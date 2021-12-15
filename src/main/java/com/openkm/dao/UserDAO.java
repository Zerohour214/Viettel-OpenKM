package com.openkm.dao;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.dao.bean.User;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
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
			String nativeQuery =
					" select u.USR_NAME as name, u.USR_ID as id, u.USR_EMAIL as email " +
					" from OKM_USER u " +
					" where (u.USR_NAME like concat('%', :search, '%') or u.USR_ID like concat('%', :search, '%') or u.USR_EMAIL like concat('%', :search, '%') )" +
					" and u.USR_ACTIVE = 'T' ";

			String userInOrgQuery = "SELECT uo.USER_ID FROM USER_ORG_VTX uo";
			Query q = session.createSQLQuery(userInOrgQuery);
			List<String> userInOrgList = q.list();
			if(userInOrgList.size() == 0) userInOrgList = new ArrayList<>();
			if(isNotInOrg == 1 && userInOrgList.size() != 0) {
				nativeQuery += " and u.USR_ID NOT IN :userInOrgList";
			}

			q = session.createSQLQuery(nativeQuery);
			q.setString("search", search);

			if(isNotInOrg == 1 && userInOrgList.size() != 0)
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

	public OrganizationVTX getOrgByUserId(String userId) throws DatabaseException {
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String nativeQuery = "SELECT o.CODE code, o.NAME name, o.ORG_PARENT parent, o.PATH path FROM OKM_USER u\n" +
					"JOIN USER_ORG_VTX uo ON u.USR_ID  = uo.USER_ID\n" +
					"JOIN ORGANIZATION_VTX o ON o.ID = uo.ORG_ID\n" +
					"WHERE u.USR_ID = :userId ";
			SQLQuery q = session.createSQLQuery(nativeQuery);
			q.setResultTransformer(Transformers.aliasToBean(OrganizationVTX.class));
			q.addScalar("code");
			q.addScalar("name");
			q.addScalar("parent", Hibernate.LONG);
			q.addScalar("path");


			q.setParameter("userId", userId);

			OrganizationVTX result = (OrganizationVTX) q.uniqueResult();
			return result;

		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
