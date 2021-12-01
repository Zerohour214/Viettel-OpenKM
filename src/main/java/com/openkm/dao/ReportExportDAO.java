package com.openkm.dao;

import com.openkm.bean.ActivityLogExportBean;
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
		String qs = "SELECT DISTINCT o.NAME orgName, u.USR_NAME fullname, u.USR_ID employeeCode, d.NBS_NAME docName, " +
				"ud.TOTAL_TIME totalTimeView, u_.USR_EMAIL author, d.NBS_CREATED timeUpload, a.ACT_DATE " +
				"FROM ORGANIZATION_VTX o\n" +
				"JOIN USER_ORG_VTX uo ON o.ID = uo.ORG_ID\n" +
				"JOIN OKM_USER u ON u.USR_ID = uo.USER_ID\n" +
				"JOIN USER_READ_DOC_TIMER ud ON u.USR_ID = ud.USER_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ud.DOC_ID\n" +
				"JOIN OKM_USER u_ ON u_.USR_ID = d.NBS_AUTHOR\n" +
				"JOIN OKM_ACTIVITY a ON a.ACT_ITEM = ud.DOC_ID AND a.ACT_USER = ud.USER_ID\n" +
				"WHERE a.ACT_DATE between :begin and :end ";

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			SQLQuery q = session.createSQLQuery(qs);
			q.setCalendar("begin", filter.getBegin());
			q.setCalendar("end", filter.getEnd());

			q.setResultTransformer(Transformers.aliasToBean(THDVBReportBean.class));
			q.addScalar("orgName");
			q.addScalar("fullname");
			q.addScalar("employeeCode");
			q.addScalar("docName");
			q.addScalar("totalTimeView", Hibernate.LONG);
			q.addScalar("author");
			q.addScalar("timeUpload");
			List<THDVBReportBean> ret = q.list();
			return ret;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			HibernateUtil.close(session);
		}

	}
}
