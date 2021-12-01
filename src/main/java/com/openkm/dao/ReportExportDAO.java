package com.openkm.dao;

import com.openkm.bean.ActivityLogExportBean;
import com.openkm.bean.THDVBReportBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.ActivityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReportExportDAO {
	private static Logger log = LoggerFactory.getLogger(ActivityDAO.class);

	private ReportExportDAO() {
	}

	public static List<THDVBReportBean> exportTHDVBByFilter(ActivityFilter filter) throws DatabaseException {
		String qs = "SELECT o.NAME, u.USR_NAME, u.USR_ID, d.NBS_NAME, ud.TOTAL_TIME, u_.USR_EMAIL, d.NBS_CREATED FROM ORGANIZATION_VTX o\n" +
				"JOIN USER_ORG_VTX uo ON o.ID = uo.ORG_ID\n" +
				"JOIN OKM_USER u ON u.USR_ID = uo.USER_ID\n" +
				"JOIN USER_READ_DOC_TIMER ud ON u.USR_ID = ud.USER_ID\n" +
				"JOIN OKM_NODE_BASE d ON d.NBS_UUID = ud.DOC_ID\n" +
				"JOIN OKM_USER u_ ON u_.USR_ID = d.NBS_AUTHOR";
		return null;
	}
}
