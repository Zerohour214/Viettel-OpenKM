/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2017  Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao;

import com.openkm.bean.ActivityLogExportBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Activity;
import com.openkm.dao.bean.ActivityFilter;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

public class ActivityDAO {
	private static Logger log = LoggerFactory.getLogger(ActivityDAO.class);

	private ActivityDAO() {
	}

	/**
	 * Create activity
	 */
	public static void create(Activity activity) throws DatabaseException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
			session.save(activity);
			HibernateUtil.commit(tx);
		} catch (HibernateException e) {
			HibernateUtil.rollback(tx);
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<Activity> findByFilter(ActivityFilter filter) throws DatabaseException {
		log.debug("findByFilter({})", filter);
		String qs = "from Activity a where a.date between :begin and :end ";

		if (filter.getUser() != null && !filter.getUser().equals(""))
			qs += "and a.user=:user ";
		if (filter.getAction() != null && !filter.getAction().equals(""))
			qs += "and a.action=:action ";
		if (filter.getItem() != null && !filter.getItem().equals("")) {
			qs += "and a.item=:item ";
		}

		qs += "order by a.date";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			if (filter.getUser() != null && !filter.getUser().equals(""))
				q.setString("user", filter.getUser());
			if (filter.getAction() != null && !filter.getAction().equals(""))
				q.setString("action", filter.getAction());
			if (filter.getItem() != null && !filter.getItem().equals(""))
				q.setString("item", filter.getItem());

			List<Activity> ret = q.list();
			log.debug("findByFilter: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Find by filter
	 */
	@SuppressWarnings("unchecked")
	public static List<Activity> findByFilterByItem(ActivityFilter filter) throws DatabaseException {
		log.debug("findByFilter({})", filter);
		String qs = "from Activity a where a.item=:item ";
		if (filter.getAction() != null && !filter.getAction().equals(""))
			qs += "and a.action=:action ";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = session.createQuery(qs);
			q.setString("item", filter.getItem());
			if (filter.getAction() != null && !filter.getAction().equals(""))
				q.setString("action", filter.getAction());

			List<Activity> ret = q.list();
			log.debug("findByFilterByItem: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	/**
	 * Get activity date
	 */
	public static Calendar getActivityDate(String user, String action, String item) throws
			DatabaseException {
		log.debug("getActivityDate({}, {}, {})", new Object[]{user, action, item});
		String qsAct = "select max(a.date) from Activity a " +
				"where a.user=:user and a.action=:action and a.item=:item";
		String qsNoAct = "select max(a.date) from Activity a " +
				"where (a.action='CREATE_DOCUMENT' or a.action='CHECKIN_DOCUMENT') and a.item=:item";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query q = null;

			if (action != null) {
				q = session.createQuery(qsAct);
				q.setString("user", user);
				q.setString("action", action);
				q.setString("item", item);
			} else {
				q = session.createQuery(qsNoAct);
				q.setString("item", item);
			}

			Calendar ret = (Calendar) q.setMaxResults(1).uniqueResult();

			if (ret == null) {
				// May be the document has been moved or renamed?
				ret = Calendar.getInstance();
			}

			log.debug("getActivityDate: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public static List<ActivityLogExportBean> exportByFilter(ActivityFilter filter) throws DatabaseException {

		String actionReports = " ( 'CREATE_DOCUMENT', 'CHECKIN_DOCUMENT', 'DELETE_DOCUMENT', 'PURGE_DOCUMENT', 'MOVE_DOCUMENT' )";

		String qs = "SELECT o.CODE as orgCode, o.NAME as orgName, ou.USR_ID as employeeCode, " +
				"ou.USR_NAME as fullName, onb.NBS_NAME as documentName, oa.ACT_ACTION as action, oa.ACT_DATE as dateTime " +
				"FROM OKM_ACTIVITY oa\n" +
				"JOIN OKM_USER ou ON oa.ACT_USER = ou.USR_ID\n" +
				"JOIN USER_ORG_VTX uov ON uov.USER_ID = ou.USR_ID\n" +
				"JOIN ORGANIZATION_VTX o ON o.ID = uov.ORG_ID\n" +
				"JOIN OKM_NODE_DOCUMENT okd ON okd.NBS_UUID = oa.ACT_ITEM\n" +
				"JOIN OKM_NODE_BASE onb ON onb.NBS_UUID = okd.NBS_UUID\n " +
				"WHERE oa.ACT_DATE between :begin and :end " +
				"AND oa.ACT_ACTION IN " + actionReports
				;

		if (filter.getUser() != null && !filter.getUser().equals(""))
			qs += "and ou.USR_ID=:user ";
		if (filter.getAction() != null && !filter.getAction().equals(""))
			qs += "and oa.ACT_ACTION=:action ";
		if (filter.getItem() != null && !filter.getItem().equals("")) {
			qs += "and oa.ACT_ITEM=:item ";
		}

		qs += "order by oa.ACT_DATE, o.CODE";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q =  session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			if (filter.getUser() != null && !filter.getUser().equals(""))
				q.setString("user", filter.getUser());
			if (filter.getAction() != null && !filter.getAction().equals(""))
				q.setString("action", filter.getAction());
			if (filter.getItem() != null && !filter.getItem().equals(""))
				q.setString("item", filter.getItem());

			q.setResultTransformer(Transformers.aliasToBean(ActivityLogExportBean.class));
			q.addScalar("orgCode");
			q.addScalar("orgName");
			q.addScalar("employeeCode");
			q.addScalar("fullName");
			q.addScalar("action");
			q.addScalar("documentName");
			q.addScalar("dateTime");


			List<ActivityLogExportBean> ret = q.list();

			for(int i=1; i<= ret.size(); ++i) {
				ret.get(i-1).setIndex((long) i);
				String actionName = "";
				switch (ret.get(i-1).getAction()) {
					case "CREATE_DOCUMENT":
						actionName = "Thêm mới";
						break;
					case "CHECKIN_DOCUMENT":
						actionName = "Sửa";
						break;
					case "DELETE_DOCUMENT":
						actionName = "Xóa (thùng rác)";
						break;
					case "PURGE_DOCUMENT":
						actionName = "Xóa";
						break;
					case "MOVE_DOCUMENT":
						actionName = "Phục hồi";
						break;
				}
				ret.get(i-1).setAction(actionName);
			}

			log.debug("findByFilter: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}

	public static List<ActivityLogExportBean> exportByFilterGeneral(ActivityFilter filter) throws DatabaseException {

		String actionReports = " ( 'CREATE_DOCUMENT', 'CHECKIN_DOCUMENT', 'DELETE_DOCUMENT', 'PURGE_DOCUMENT', 'MOVE_DOCUMENT' )";

		String qs = "SELECT o.CODE as orgCode, o.NAME as orgName,\n" +
				"onb.NBS_NAME as documentName, oa.ACT_ACTION as action, oa.ACT_DATE as dateTime " +
				"FROM OKM_ACTIVITY oa\n" +
				"JOIN OKM_USER ou ON oa.ACT_USER = ou.USR_ID\n" +
				"JOIN USER_ORG_VTX uov ON uov.USER_ID = ou.USR_ID\n" +
				"JOIN ORGANIZATION_VTX o ON o.ID = uov.ORG_ID\n" +
				"JOIN OKM_NODE_DOCUMENT okd ON okd.NBS_UUID = oa.ACT_ITEM\n" +
				"JOIN OKM_NODE_BASE onb ON onb.NBS_UUID = okd.NBS_UUID\n " +
				"WHERE oa.ACT_DATE between :begin and :end " +
				"AND oa.ACT_ACTION IN " + actionReports + "\n";


		if (filter.getUser() != null && !filter.getUser().equals(""))
			qs += "and ou.USR_ID=:user ";
		if (filter.getAction() != null && !filter.getAction().equals(""))
			qs += "and oa.ACT_ACTION=:action ";
		if (filter.getItem() != null && !filter.getItem().equals("")) {
			qs += "and oa.ACT_ITEM=:item ";
		}

		qs += "GROUP BY orgCode, documentName, action\n";
		qs += "order by oa.ACT_DATE, o.CODE";
		Session session = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q =  session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			if (filter.getUser() != null && !filter.getUser().equals(""))
				q.setString("user", filter.getUser());
			if (filter.getAction() != null && !filter.getAction().equals(""))
				q.setString("action", filter.getAction());
			if (filter.getItem() != null && !filter.getItem().equals(""))
				q.setString("item", filter.getItem());

			q.setResultTransformer(Transformers.aliasToBean(ActivityLogExportBean.class));
			q.addScalar("orgCode");
			q.addScalar("orgName");

			q.addScalar("action");
			q.addScalar("documentName");

			List<ActivityLogExportBean> ret = q.list();

			for(int i=1; i<= ret.size(); ++i) {
				ret.get(i-1).setIndex((long) i);
				String actionName = "";
				switch (ret.get(i-1).getAction()) {
					case "CREATE_DOCUMENT":
						actionName = "Thêm mới";
						break;
					case "CHECKIN_DOCUMENT":
						actionName = "Sửa";
						break;
					case "DELETE_DOCUMENT":
						actionName = "Xóa (thùng rác)";
						break;
					case "PURGE_DOCUMENT":
						actionName = "Xóa";
						break;
					case "MOVE_DOCUMENT":
						actionName = "Phục hồi";
						break;
				}
				ret.get(i-1).setAction(actionName);
			}

			log.debug("findByFilter: {}", ret);
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}
	}
}
