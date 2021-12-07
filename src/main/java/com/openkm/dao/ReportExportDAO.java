package com.openkm.dao;


import com.openkm.bean.*;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.ActivityFilter;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReportExportDAO {
	private static Logger log = LoggerFactory.getLogger(ActivityDAO.class);

	private ReportExportDAO() {
	}

	public static List<THDVBReportBeanDetail> exportTHDVBByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT DISTINCT o.NAME orgName, u.USR_NAME fullname, u.USR_ID employeeCode, d.NBS_NAME docName, ud.COUNT_VIEW viewNum, " +
				"ud.TOTAL_TIME totalTimeView, u_.USR_EMAIL author, d.NBS_CREATED timeUpload " +
				"FROM ORGANIZATION_VTX o\n" +
				"JOIN USER_ORG_VTX uo ON o.ID = uo.ORG_ID\n" +
				"JOIN OKM_USER u ON u.USR_ID = uo.USER_ID\n" +
				"JOIN USER_READ_DOC_TIMER ud ON u.USR_ID = ud.USER_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ud.DOC_ID\n" +
				"JOIN OKM_USER u_ ON u_.USR_ID = d.NBS_AUTHOR\n" +
				"WHERE ud.LAST_PREVIEW between :begin AND :end ";
		if (filter.getUser() != null && !filter.getUser().equals(""))
			qs += "AND u.USR_ID=:user ";

		if(filter.getOrgIdTHDVB() != null && !filter.getOrgIdTHDVB().trim().equals("")) {
			qs += "AND o.ID = :orgId\n";
		}

		if(filter.getDocIdTHDVB() != null && !filter.getDocIdTHDVB().trim().equals("")) {
			qs += "AND d.NBS_UUID = :docId\n";
		}
		qs += "ORDER BY o.ID ";
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			if(filter.getOrgIdTHDVB() != null && !filter.getOrgIdTHDVB().trim().equals("")) {
				q.setString("orgId", filter.getOrgIdTHDVB());
			}
			if(filter.getDocIdTHDVB() != null && !filter.getDocIdTHDVB().trim().equals("")) {
				q.setString("docId", filter.getDocIdTHDVB());
			}
			if (filter.getUser() != null && !filter.getUser().equals(""))
				q.setString("user", filter.getUser());

			q.setResultTransformer(Transformers.aliasToBean(THDVBReportBeanDetail.class));
			q.addScalar("orgName");
			q.addScalar("fullname");
			q.addScalar("employeeCode");
			q.addScalar("docName");
			q.addScalar("totalTimeView", Hibernate.LONG);
			q.addScalar("author");
			q.addScalar("timeUpload");
			q.addScalar("viewNum", Hibernate.LONG);
			List<THDVBReportBeanDetail> ret = q.list();
			for(int i=1; i <= ret.size(); ++i) {
				ret.get(i-1).setIndex((long) i);
			}
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}

	public static List<THDVBReportBeanGeneral> exportTHDVBGeneralByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT t2.orgName, t2.docName, t1.totalUser, t2.viewedUser, t2.userId FROM \n" +
				"(\n" +
				"SELECT uo.ORG_ID orgId, COUNT(*) totalUser\n" +
				"FROM USER_ORG_VTX uo\n" +
				"GROUP BY uo.ORG_ID\n" +
				") t1\n" +
				"JOIN \n" +
				"(\n" +
				"SELECT o.ID orgId, o.NAME orgName, d.NBS_NAME docName, u.USR_ID userId, COUNT(DISTINCT u.USR_ID) viewedUser\n" +
				"FROM USER_ORG_VTX uo_\n" +
				"JOIN OKM_USER u ON uo_.USER_ID = u.USR_ID\n" +
				"JOIN USER_READ_DOC_TIMER ut ON uo_.USER_ID = ut.USER_ID\n" +
				"JOIN ORGANIZATION_VTX o ON o.ID = uo_.ORG_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ut.DOC_ID\n" +
				"WHERE ut.LAST_PREVIEW BETWEEN :begin and :end\n";

		if(filter.getOrgIdTHDVB() != null && !filter.getOrgIdTHDVB().trim().equals("")) {
			qs += "AND o.ID = :orgId\n";
		}

		if(filter.getDocIdTHDVB() != null && !filter.getDocIdTHDVB().trim().equals("")) {
			qs += "AND d.NBS_UUID = :docId\n";
		}

		if (filter.getUser() != null && !filter.getUser().equals(""))
			qs += "AND u.USR_ID=:user\n ";

			qs +=	"GROUP BY o.ID, d.NBS_UUID\n" +
				") t2\n" +
				"\n" +
				"ON t1.orgId = t2.orgId\n";


		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			if(filter.getOrgIdTHDVB() != null && !filter.getOrgIdTHDVB().trim().equals("")) {
				q.setString("orgId", filter.getOrgIdTHDVB());
			}
			if(filter.getDocIdTHDVB() != null && !filter.getDocIdTHDVB().trim().equals("")) {
				q.setString("docId", filter.getDocIdTHDVB());
			}

			if (filter.getUser() != null && !filter.getUser().equals(""))
				q.setString("user", filter.getUser());
			q.setResultTransformer(Transformers.aliasToBean(THDVBReportBeanGeneral.class));
			q.addScalar("orgName");
			q.addScalar("docName");
			q.addScalar("totalUser", Hibernate.LONG);
			q.addScalar("viewedUser", Hibernate.LONG);
			q.addScalar("userId");
			List<THDVBReportBeanGeneral> ret = q.list();
			for(int i=1; i <= ret.size(); ++i) {
				ret.get(i-1).setIndex((long) i);
			}
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}

	public static List<KQTTReportBean> exportKQTTByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT a.orgName, a.fullname, a.employeeCode, a.docName, a.assignDoc,\n" +
				"ud.CONFIRM_DATE confirmDate, ud.START_CONFIRM startConfirm, ud.END_CONFIRM endConfirm, ud.TIME_LAST_PREVIEW timeRead\n" +
				" FROM (SELECT ov.NAME orgName, ou.USR_NAME fullname, ou.USR_ID employeeCode, onb.NBS_NAME docName,\n" +
				"od.CREATED_AT assignDoc, onb.NBS_UUID docId\n" +
				"FROM ORG_DOC od JOIN USER_ORG_VTX uov ON od.ORG_ID = uov.ORG_ID\n" +
				"JOIN OKM_USER ou ON ou.USR_ID = uov.USER_ID\n" +
				"JOIN ORGANIZATION_VTX ov ON ov.ID = od.ORG_ID\n" +
				"JOIN OKM_NODE_BASE onb ON onb.NBS_UUID = od.DOC_ID) a\n" +
				"LEFT JOIN (\n" +
				"SELECT x.LAST_PREVIEW,x.CONFIRM_DATE,x.START_CONFIRM, x.END_CONFIRM ,x.TIME_LAST_PREVIEW, x.USER_ID,x.DOC_ID FROM (\n" +
				"SELECT * FROM USER_READ_DOC_TIMER urdt WHERE urdt.CONFIRM = 'T' GROUP BY urdt.DOC_ID,urdt.USER_ID\n" +
				"UNION\n" +
				"SELECT urdt.* FROM USER_READ_DOC_TIMER urdt LEFT JOIN USER_READ_DOC_TIMER urdt2 ON (urdt.USER_ID = urdt2.USER_ID AND urdt.DOC_ID = urdt2.DOC_ID AND urdt.ID < urdt2.ID) \n" +
				"WHERE urdt2.ID IS NULL) AS x WHERE x.LAST_PREVIEW BETWEEN :begin and :end GROUP BY x.DOC_ID,x.USER_ID) as ud ON ud.USER_ID = a.employeeCode AND ud.DOC_ID = a.docId;" ;

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			q.setResultTransformer(Transformers.aliasToBean(KQTTReportBean.class));
			q.addScalar("orgName");
			q.addScalar("fullname");
			q.addScalar("employeeCode");
			q.addScalar("docName");
			q.addScalar("assignDoc");
			q.addScalar("confirmDate");
			q.addScalar("startConfirm");
			q.addScalar("endConfirm");
			q.addScalar("timeRead", Hibernate.LONG);

			List<KQTTReportBean> ret = q.list();
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}

	public static List<CLVBReportBean> exportCLVBByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT d.NBS_UUID docId, d.NBS_NAME docName, \n" +
				"COUNT(DISTINCT u.USER_ID) totalAccess, \n" +
				"SUM(u.COUNT_VIEW) totalView, \n" +
				"SUM(u.LESS_1MIN) totalLessOneMin\n" +
				"FROM USER_READ_DOC_TIMER u\n" +
				"JOIN OKM_NODE_BASE d ON u.DOC_ID = d.NBS_UUID\n" +
				"WHERE u.LAST_PREVIEW BETWEEN :begin AND :end\n";


		if(filter.getDocIdCLVB() != null && !filter.getDocIdCLVB().trim().equals("")) {
			qs += "AND d.NBS_UUID = :docId\n";
		}
		qs += "GROUP BY u.DOC_ID\n";

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());
			if(filter.getDocIdCLVB() != null && !filter.getDocIdCLVB().trim().equals("")) {
				q.setString("docId", filter.getDocIdCLVB());
			}
			q.setResultTransformer(Transformers.aliasToBean(CLVBReportBean.class));
			q.addScalar("docId");
			q.addScalar("docName");
			q.addScalar("totalAccess", Hibernate.LONG);
			q.addScalar("totalView", Hibernate.LONG);
			q.addScalar("totalLessOneMin", Hibernate.LONG);

			List<CLVBReportBean> ret = q.list();

			for(int i=1; i <= ret.size(); ++i) {
				ret.get(i-1).setIndex((long) i);
			}

			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}
}
