package com.openkm.frontend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GWTOrganization implements IsSerializable {
	Long id = null;
	String name = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=").append(id);
		sb.append(", orgName=").append(name);
		sb.append("}");
		return sb.toString();
	}
}
