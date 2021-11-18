package com.openkm.bean;

import java.io.Serializable;

public class OrganizationVTXBean implements Serializable {
	private Long id;
	private String orgName;
	private String orgCode;
	private Long orgParent;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getOrgParent() {
		return orgParent;
	}

	public void setOrgParent(Long orgParent) {
		this.orgParent = orgParent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
