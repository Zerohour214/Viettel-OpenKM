package com.openkm.frontend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GWTVOfficeDocument implements IsSerializable {
	private String code;
	private String title;
	private String area;
	private Long documentId;
	private String attachment;
	private String name;
	private Long fileAttachmentId;
	private String storage;
	private Integer filePage;
	private Long fileSize;
	private Boolean decrypt;
	private Integer type;
	private Boolean isCopy;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFileAttachmentId() {
		return fileAttachmentId;
	}

	public void setFileAttachmentId(Long fileAttachmentId) {
		this.fileAttachmentId = fileAttachmentId;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public Integer getFilePage() {
		return filePage;
	}

	public void setFilePage(Integer filePage) {
		this.filePage = filePage;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getDecrypt() {
		return decrypt;
	}

	public void setDecrypt(Boolean decrypt) {
		this.decrypt = decrypt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getCopy() {
		return isCopy;
	}

	public void setCopy(Boolean copy) {
		isCopy = copy;
	}
}
