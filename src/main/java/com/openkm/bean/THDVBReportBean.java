package com.openkm.bean;

import java.sql.Timestamp;

public class THDVBReportBean {
	String orgName;
	String fullname;
	String employeeCode;
	String docName;
	Long viewNum;
	Long totalTimeView;
	String author;
	Timestamp timeUpload;

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

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	public Long getTotalTimeView() {
		return totalTimeView;
	}

	public void setTotalTimeView(Long totalTimeView) {
		this.totalTimeView = totalTimeView;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getTimeUpload() {
		return timeUpload;
	}

	public void setTimeUpload(Timestamp timeUpload) {
		this.timeUpload = timeUpload;
	}
}
