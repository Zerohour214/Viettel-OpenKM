package com.openkm.dao.bean;

import com.openkm.module.db.stuff.LowerCaseFieldBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
@Table(name = "ORG_DOC")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrgDocumentVTX implements Serializable {

	@Id
	@Column(name = "DOC_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String docId;


	@Id
	@Column(name = "ORG_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private Long orgId;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}


}
