package com.openkm.bean;

import java.sql.Timestamp;

public class KQTTReportBean {
	String orgName;
	String fullname;
	String employeeCode;
	String docName;

	Timestamp assignDoc;
	Timestamp confirmDate;
	Timestamp startConfirm;
	Timestamp endConfirm;
	Long timeRead;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Timestamp getAssignDoc() {
		return assignDoc;
	}

	public void setAssignDoc(Timestamp assignDoc) {
		this.assignDoc = assignDoc;
	}

	public Timestamp getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Timestamp confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Timestamp getStartConfirm() {
		return startConfirm;
	}

	public void setStartConfirm(Timestamp startConfirm) {
		this.startConfirm = startConfirm;
	}

	public Timestamp getEndConfirm() {
		return endConfirm;
	}

	public void setEndConfirm(Timestamp endConfirm) {
		this.endConfirm = endConfirm;
	}

	public Long getTimerRead() {
		return timeRead;
	}

	public void setTimerRead(Long timeRead) {
		this.timeRead = timeRead;
	}
}
