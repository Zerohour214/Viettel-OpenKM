package com.openkm.dao.bean;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;


@Entity
@Indexed
@Table(name = "USER_READ_DOC_TIMER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserReadDocTimer implements Serializable {

	@Id
	@Column(name = "USER_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String userId;

	@Id
	@Column(name = "DOC_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String docId;

	@Column(name = "LAST_PREVIEW")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	protected Calendar created;

	@Column(name = "READING", nullable = false)
	@Type(type = "true_false")
	protected boolean reading;

	@Column(name = "CONFIRM", nullable = false)
	@Type(type = "true_false")
	protected boolean confirm;

	@Column(name = "TOTAL_TIME", nullable = false)
	protected long totalTime;

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public boolean isReading() {
		return reading;
	}

	public void setReading(boolean reading) {
		this.reading = reading;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
}
