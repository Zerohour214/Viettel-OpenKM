package com.openkm.bean;

import java.sql.Timestamp;

public class THDVBReportBeanGeneral {
	String orgName;
	String docName;
	Long totalUser;
	Long viewedUser;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Long getTotalUser() {
		return totalUser;
	}

	public void setTotalUser(Long totalUser) {
		this.totalUser = totalUser;
	}

	public Long getViewedUser() {
		return viewedUser;
	}

	public void setViewedUser(Long viewedUser) {
		this.viewedUser = viewedUser;
	}
}
