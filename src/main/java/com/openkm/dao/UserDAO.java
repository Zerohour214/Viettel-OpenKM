package com.openkm.dao;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.dao.bean.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDAO {
	private static Logger log = LoggerFactory.getLogger(UserDAO.class);
	private static UserDAO single = new UserDAO();
	private UserDAO() {
	}

	public static UserDAO getInstance() {
		return single;
	}

	public List<User> getAllUser(String search) throws DatabaseException {
		log.debug("getAllUser({})");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createSQLQuery(
					"select u.USR_NAME as name, u.USR_ID as id, u.USR_EMAIL as email " +
							" from OKM_USER u " +

							" where (u.USR_NAME like concat('%', :search, '%') or u.USR_ID like concat('%', :search, '%') or u.USR_EMAIL like concat('%', :search, '%') )" +
							" and u.USR_ACTIVE = 'T' and u.USR_ID != 'okmAdmin'");

			q.setString("search", search);
			List<User> ret = q.list();
			log.debug("getAllUser: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
