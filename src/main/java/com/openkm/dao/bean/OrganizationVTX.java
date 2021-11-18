package com.openkm.dao.bean;

import com.openkm.module.db.stuff.LowerCaseFieldBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;

@Entity
@Indexed
@Table(name = "ORGANIZATION_VTX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganizationVTX {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private Long id;


	@Column(name = "CODE", length = 256, nullable = false)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String code;

	@Column(name = "NAME", length = 256, nullable = false)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String name;

	@Column(name = "PATH", length = 1000, nullable = false)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String path;

	@Column(name = "ORG_ORDER")
	private Integer order;

	@Column(name = "ORG_PARENT")
	private Long parent;

	@Column(name = "GENERATE_ORG_ORDER", length = 256)
	private String genOrgId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "ORG_LEVEL_MANAGE")
	private Integer orgLevelMangage;

	public String getGenOrgId() {
		return genOrgId;
	}

	public void setGenOrgId(String genOrgId) {
		this.genOrgId = genOrgId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrgLevelMangage() {
		return orgLevelMangage;
	}

	public void setOrgLevelMangage(Integer orgLevelMangage) {
		this.orgLevelMangage = orgLevelMangage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}
}
