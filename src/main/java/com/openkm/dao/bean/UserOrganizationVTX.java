package com.openkm.dao.bean;

import com.openkm.module.db.stuff.LowerCaseFieldBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
@Table(name = "USER_ORG_VTX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOrganizationVTX implements Serializable {

	@Id
	@Column(name = "USER_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String userId;


	@Id
	@Column(name = "ORG_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private Long orgId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
}
