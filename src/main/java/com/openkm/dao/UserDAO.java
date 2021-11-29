package com.openkm.dao;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.dao.bean.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private static Logger log = LoggerFactory.getLogger(UserDAO.class);
	private static UserDAO single = new UserDAO();
	private UserDAO() {
	}

	public static UserDAO getInstance() {
		return single;
	}

	public List<User> getAllUser(String search, int isNotInOrg) throws DatabaseException {
		log.debug("getAllUser({})");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String nativeQuery = "select u.USR_NAME as name, u.USR_ID as id, u.USR_EMAIL as email " +
					" from OKM_USER u " +

					" where (u.USR_NAME like concat('%', :search, '%') or u.USR_ID like concat('%', :search, '%') or u.USR_EMAIL like concat('%', :search, '%') )" +
					" and u.USR_ACTIVE = 'T' and u.USR_ID != 'okmAdmin' ";

			String userInOrgQuery = "SELECT uo.USER_ID FROM USER_ORG_VTX uo";
			Query q = session.createSQLQuery(userInOrgQuery);
			List<String> userInOrgList = q.list();
			if(isNotInOrg == 1) {
				nativeQuery += " and u.USR_ID NOT IN :userInOrgList";
			}

			q = session.createSQLQuery(nativeQuery);
			q.setString("search", search);
			if(userInOrgList.size() == 0) userInOrgList = new ArrayList<>();
			q.setParameterList("userInOrgList", userInOrgList);
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
