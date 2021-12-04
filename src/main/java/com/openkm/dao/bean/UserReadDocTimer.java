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
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name = "USER_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String userId;

	@Column(name = "DOC_ID")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String docId;

	@Column(name = "LAST_PREVIEW")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	protected Calendar created;

	@Column(name = "CONFIRM_DATE")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	protected Calendar confirmDate;

	@Column(name = "START_CONFIRM")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	protected Calendar startConfirm;

	@Column(name = "END_CONFIRM")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	protected Calendar endConfirm;

	@Column(name = "READING", nullable = false)
	@Type(type = "true_false")
	protected boolean reading;

	@Column(name = "CONFIRM", nullable = false)
	@Type(type = "true_false")
	protected boolean confirm;

	@Column(name = "TOTAL_TIME", nullable = false)
	protected long totalTime;

	@Column(name = "TIME_LAST_PREVIEW", nullable = false)
	protected long timeLastPreview;

	@Column(name = "COUNT_VIEW", nullable = false)
	protected long countView;

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

	public long getCountView() {
		return countView;
	}

	public void setCountView(long countView) {
		this.countView = countView;
	}

	public Calendar getStartConfirm() {
		return startConfirm;
	}

	public void setStartConfirm(Calendar startConfirm) {
		this.startConfirm = startConfirm;
	}

	public Calendar getEndConfirm() {
		return endConfirm;
	}

	public void setEndConfirm(Calendar endConfirm) {
		this.endConfirm = endConfirm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Calendar confirmDate) {
		this.confirmDate = confirmDate;
	}

	public long getTimeLastPreview() {
		return timeLastPreview;
	}

	public void setTimeLastPreview(long timeLastPreview) {
		this.timeLastPreview = timeLastPreview;
	}
}
