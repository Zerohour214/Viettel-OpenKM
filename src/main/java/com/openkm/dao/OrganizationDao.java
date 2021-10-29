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

public class OrganizationDao {
	private static Logger log = LoggerFactory.getLogger(OrganizationDao.class);
	private static OrganizationDao single = new OrganizationDao();

	private OrganizationDao() {
	}

	public static OrganizationDao getInstance() {
		return single;
	}

	public List<Organization> getAllOrg() throws DatabaseException {
		log.debug("getAllOrg({})");
		String qs = "from Organization";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			List<Organization> ret = q.list();
			log.debug("getAllOrg: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public String getOrgNameById(long id) throws DatabaseException {
		if (id == 0) {
			return "";
		}
		log.debug("getOrgNameById({})");
		String qs = "from Organization o where o.id=:id";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setParameter("id", id);
			List<Organization> ret = q.list();
			log.debug("getOrgNameById: {}", ret);
			return ret.get(0).getName();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

}
