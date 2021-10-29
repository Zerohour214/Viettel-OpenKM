package com.openkm.dao;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.AuthTicket;
import com.openkm.dao.bean.Organization;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthTicketDao {
	private static Logger log = LoggerFactory.getLogger(OrganizationDao.class);
	private static AuthTicketDao single = new AuthTicketDao();

	private AuthTicketDao() {
	}

	public static AuthTicketDao getInstance() {
		return single;
	}

	public static String getTicket(String empCode) throws DatabaseException {
		if (empCode.equals("") || empCode == null) {
			return "";
		}
		log.debug("getTicket({})");
		String qs = "from AuthTicket au where au.empCode=:empCode";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setParameter("empCode", empCode);
			List<AuthTicket> ret = q.list();
			log.debug("getTicket: {}", ret);
			return ret.get(0).getTicket();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public static void saveOrUpdate(AuthTicket auth) throws DatabaseException {
		log.debug("saveOrUpdate({})", auth);
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(auth);
			HibernateUtil.commit(tx);
			log.debug("saveOrUpdate: void");
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
