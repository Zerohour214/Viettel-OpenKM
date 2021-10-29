package com.openkm.dao.bean;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "AUTHOR_TICKET")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthTicket {
	@Id
	@Column(name = "EMPLOYEE_CODE")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String empCode;

	@Column(name = "TICKET",  columnDefinition="TEXT")
	@Type(type="text")
	private String ticket;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
