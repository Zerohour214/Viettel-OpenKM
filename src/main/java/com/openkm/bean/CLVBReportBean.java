package com.openkm.bean;

public class CLVBReportBean {
	String docId;
	String docName;
	Long totalAccess;
	Long totalView;
	Long totalLessOneMin;
	Long index;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Long getTotalAccess() {
		return totalAccess;
	}

	public void setTotalAccess(Long totalAccess) {
		this.totalAccess = totalAccess;
	}

	public Long getTotalView() {
		return totalView;
	}

	public void setTotalView(Long totalView) {
		this.totalView = totalView;
	}

	public Long getTotalLessOneMin() {
		return totalLessOneMin;
	}

	public void setTotalLessOneMin(Long totalLessOneMin) {
		this.totalLessOneMin = totalLessOneMin;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public Long getIndex() {
		return index;
	}
}
