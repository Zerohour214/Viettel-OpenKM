package com.openkm.dao.bean;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Indexed
@Table(name = "USER_DOC_TRANSMIT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDocumentTransmitVTX implements Serializable {

	@Id
	@Column(name = "DOC_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String docId;

	@Id
	@Column(name = "USER_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String userId;

	@Column(name="CREATED_AT", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date created_at;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
}
