package com.openkm.dao;

import com.openkm.bean.OrganizationVTXBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.dao.bean.User;
import com.openkm.dao.bean.UserOrganizationVTX;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrganizationVTXDAO {
	private static Logger log = LoggerFactory.getLogger(OrganizationVTXDAO.class);
	private static OrganizationVTXDAO single = new OrganizationVTXDAO();

	private OrganizationVTXDAO() {
	}

	public static OrganizationVTXDAO getInstance() {
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

	public void createOrg (OrganizationVTXBean newOrg) throws DatabaseException {
		log.debug("createOrg({})");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			OrganizationVTX organizationVTX = new OrganizationVTX();
			organizationVTX.setCode(newOrg.getOrgCode());
			organizationVTX.setName(newOrg.getOrgName());
			organizationVTX.setParent(newOrg.getOrgParent());
			organizationVTX.setPath("");

			session.beginTransaction();
			Long id = (Long) session.save(organizationVTX);
			session.getTransaction().commit();

			if(newOrg.getOrgParent() == null) {
				organizationVTX.setPath('/' + String.valueOf(id) + '/');
			} else {
				organizationVTX.setPath(getOrganizationbyId(newOrg.getOrgParent()).getPath() + id + '/');
			}

			session.beginTransaction();
			session.save(organizationVTX);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public OrganizationVTX getOrganizationbyId(Long id) throws DatabaseException {
		String qs = "from OrganizationVTX o where o.id= :id";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setLong("id", id);
			OrganizationVTX ret = (OrganizationVTX) q.setMaxResults(1).uniqueResult();
			log.debug("getOrg: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public List<OrganizationVTX> search(String name, String code) throws DatabaseException {
		String qs = "from OrganizationVTX o where o.name like concat('%', :name, '%' )  and o.code like concat('%', :code, '%' ) ";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("name", name);
			q.setString("code", code);
			List<OrganizationVTX> ret = (List<OrganizationVTX>) q.list();
			log.debug("getAllOrg: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public List<OrganizationVTX>  getAllOrgLevelRoot() throws DatabaseException {
		String qs = "from OrganizationVTX o where o.parent is null or o.parent = :param ";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("param", "");
			List<OrganizationVTX> ret = (List<OrganizationVTX>) q.list();
			log.debug("getAllOrg: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public void addUserToOrg(String userId, Long orgId) throws DatabaseException {
		log.debug("addUserToOrg({})");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			UserOrganizationVTX userOrganizationVtx = new UserOrganizationVTX();
			userOrganizationVtx.setUserId(userId);
			userOrganizationVtx.setOrgId(orgId);

			session.beginTransaction();
			session.save(userOrganizationVtx);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public List<User> findUsersbyOrg(String orgId) throws DatabaseException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createSQLQuery("SELECT u.USR_ID as id, u.USR_NAME as name, u.USR_EMAIL as email FROM OKM_USER u JOIN USER_ORG_VTX uo ON u.USR_ID = uo.USER_ID where uo.ORG_ID = :orgId");
			q.setString("orgId", orgId);
			List<User> ret = (List<User>) q.list();
			log.debug("getAllOrg: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public void removeUserOrg(Long orgId, String userId) throws DatabaseException {
		log.debug("removeUserOrg({})");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			UserOrganizationVTX userOrganizationVTX = new UserOrganizationVTX();
			userOrganizationVTX.setOrgId(orgId);
			userOrganizationVTX.setUserId(userId);

			session.beginTransaction();
			session.delete(userOrganizationVTX);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public void deleteOrg(Long orgId) throws DatabaseException {
		log.debug("removeUserOrg({})");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			OrganizationVTX organizationVTX = new OrganizationVTX();
			organizationVTX.setId(orgId);

			session.beginTransaction();
			session.delete(organizationVTX);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
