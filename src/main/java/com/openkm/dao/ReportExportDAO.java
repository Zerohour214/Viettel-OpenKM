package com.openkm.dao;

import com.openkm.bean.ActivityLogExportBean;
import com.openkm.bean.KQTTReportBean;
import com.openkm.bean.THDVBReportBean;
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

	public static List<THDVBReportBean> exportTHDVBByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT DISTINCT o.NAME orgName, u.USR_NAME fullname, u.USR_ID employeeCode, d.NBS_NAME docName, ud.COUNT_VIEW viewNum, " +
				"ud.TOTAL_TIME totalTimeView, u_.USR_EMAIL author, d.NBS_CREATED timeUpload " +
				"FROM ORGANIZATION_VTX o\n" +
				"JOIN USER_ORG_VTX uo ON o.ID = uo.ORG_ID\n" +
				"JOIN OKM_USER u ON u.USR_ID = uo.USER_ID\n" +
				"JOIN USER_READ_DOC_TIMER ud ON u.USR_ID = ud.USER_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ud.DOC_ID\n" +
				"JOIN OKM_USER u_ ON u_.USR_ID = d.NBS_AUTHOR\n" ;

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			/*q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());*/

			q.setResultTransformer(Transformers.aliasToBean(THDVBReportBean.class));
			q.addScalar("orgName");
			q.addScalar("fullname");
			q.addScalar("employeeCode");
			q.addScalar("docName");
			q.addScalar("totalTimeView", Hibernate.LONG);
			q.addScalar("author");
			q.addScalar("timeUpload");
			q.addScalar("viewNum", Hibernate.LONG);
			List<THDVBReportBean> ret = q.list();
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
				"FROM org_doc od JOIN user_org_vtx uov ON od.ORG_ID = uov.ORG_ID\n" +
				"JOIN OKM_USER ou ON ou.USR_ID = uov.USER_ID\n" +
				"JOIN ORGANIZATION_VTX ov ON ov.ID = od.ORG_ID\n" +
				"JOIN OKM_NODE_BASE onb ON onb.NBS_UUID = od.DOC_ID) a\n" +
				"LEFT JOIN (\n" +
				"SELECT x.LAST_PREVIEW,x.CONFIRM_DATE,x.START_CONFIRM, x.END_CONFIRM ,x.TIME_LAST_PREVIEW, x.USER_ID,x.DOC_ID FROM (\n" +
				"SELECT * FROM user_read_doc_timer urdt WHERE urdt.CONFIRM = 'T' GROUP BY urdt.DOC_ID,urdt.USER_ID\n" +
				"UNION\n" +
				"SELECT urdt.* FROM user_read_doc_timer urdt LEFT JOIN user_read_doc_timer urdt2 ON (urdt.USER_ID = urdt2.USER_ID AND urdt.DOC_ID = urdt2.DOC_ID AND urdt.ID < urdt2.ID) \n" +
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
}
