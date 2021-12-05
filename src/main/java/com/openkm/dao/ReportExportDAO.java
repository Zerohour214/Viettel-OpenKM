package com.openkm.dao;

import com.openkm.bean.THDVBReportBeanDetail;
import com.openkm.bean.THDVBReportBeanGeneral;
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
				"WHERE ud.LAST_PREVIEW between :begin and :end ORDER BY o.ID";

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

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
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}

	public static List<THDVBReportBeanGeneral> exportTHDVBGeneralByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT t2.orgName, t2.docName, t1.totalUser, t2.viewedUser FROM \n" +
				"(\n" +
				"SELECT uo.ORG_ID orgId, COUNT(*) totalUser\n" +
				"FROM USER_ORG_VTX uo\n" +
				"GROUP BY uo.ORG_ID\n" +
				") t1\n" +
				"JOIN \n" +
				"(\n" +
				"SELECT o.ID orgId, o.NAME orgName, d.NBS_NAME docName, COUNT(DISTINCT u.USR_ID) viewedUser\n" +
				"FROM USER_ORG_VTX uo_\n" +
				"JOIN OKM_USER u ON uo_.USER_ID = u.USR_ID\n" +
				"JOIN USER_READ_DOC_TIMER ut ON uo_.USER_ID = ut.USER_ID\n" +
				"JOIN ORGANIZATION_VTX o ON o.ID = uo_.ORG_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ut.DOC_ID\n" +
				"WHERE ut.LAST_PREVIEW BETWEEN :begin and :end\n" +
				"GROUP BY o.ID, d.NBS_UUID\n" +
				") t2\n" +
				"\n" +
				"ON t1.orgId = t2.orgId\n";

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			q.setResultTransformer(Transformers.aliasToBean(THDVBReportBeanGeneral.class));
			q.addScalar("orgName");
			q.addScalar("docName");
			q.addScalar("totalUser", Hibernate.LONG);
			q.addScalar("viewedUser", Hibernate.LONG);
			List<THDVBReportBeanGeneral> ret = q.list();
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}
}
