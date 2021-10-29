/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2017 Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.dao.bean;

import com.openkm.module.db.stuff.IntegerFieldBridge;
import com.openkm.module.db.stuff.LongFieldBridge;
import com.openkm.module.db.stuff.LowerCaseFieldBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Indexed
@Table(name = "OKM_NODE_DOCUMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NodeDocument extends NodeBase {
	private static final long serialVersionUID = 1L;
	public static final String TEXT_FIELD = "text";

	@Column(name = "NDC_LAST_MODIFIED")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	private Calendar lastModified;

	@Column(name = "NDC_LANGUAGE", length = 8)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String language;

	@Column(name = "NDC_TITLE", length = 256)
	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String title;

	@Column(name = "NDC_DESCRIPTION", length = 2048)
	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String description;

	@Column(name = "NDC_MIME_TYPE", length = 128)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private String mimeType;

	@Column(name = "NDC_TEXT")
	@Lob
	@Type(type = "org.hibernate.type.StringClobType")
	@Field(index = Index.TOKENIZED, store = Store.NO)
	private String text;

	@Column(name = "NDC_CHECKED_OUT", nullable = false)
	@Type(type = "true_false")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private boolean checkedOut;

	@Column(name = "NDC_ENCRYPTION", nullable = false)
	@Type(type = "true_false")
	private boolean encryption;

	@Column(name = "NDC_CIPHER_NAME")
	private String cipherName;

	@Column(name = "NDC_SIGNED", nullable = false)
	@Type(type = "true_false")
	private boolean signed;

	@Column(name = "NDC_TEXT_EXTRACTED", nullable = false)
	@Type(type = "true_false")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private boolean textExtracted;

	@Column(name = "NDC_LOCKED", nullable = false)
	@Type(type = "true_false")
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	private boolean locked;

	@Column(name = "NDC_DOC_CODE", length = 256, nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String docCode;

	@Column(name = "NDC_DOC_NAME", length = 256, nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String docName;

	@Column(name = "NDC_DOC_EFFECTIVE_DATE", nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	private Calendar docEffectiveDate;

	@Column(name = "NDC_DOC_ORG_ID", nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LongFieldBridge.class)
	private Long docOrgId;

	@Column(name = "NDC_DOC_CONFIDENTIALITY", nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = IntegerFieldBridge.class)
	private Integer docConfidentiality;

	@Column(name = "NDC_DOC_EXPIRED_DATE", nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@CalendarBridge(resolution = Resolution.DAY)
	private Calendar docExpiredDate;

	@Column(name = "NDC_DOC_AUTHOR", nullable =  true, length = 128)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String docAuthor;

	@Column(name = "NDC_PUBLISH_COM", nullable = true, length = 256)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = LowerCaseFieldBridge.class)
	private String publishCom;

	@Column(name = "NDC_PAGE_NUMBERS", nullable = true)
	private Integer pageNumber;

	@Column(name = "NDC_PUBLISHED_YEAR", nullable = true)
	@Field(index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = IntegerFieldBridge.class)
	private Integer publishedYear;

	@Embedded
	private NodeLock lock;

	public Integer getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public Calendar getDocExpiredDate() {
		return docExpiredDate;
	}

	public void setDocExpiredDate(Calendar docExpiredDate) {
		this.docExpiredDate = docExpiredDate;
	}

	public String getDocAuthor() {
		return docAuthor;
	}

	public void setDocAuthor(String ndcAuthor) {
		this.docAuthor = ndcAuthor;
	}

	public String getPublishCom() {
		return publishCom;
	}

	public void setPublishCom(String publishCom) {
		this.publishCom = publishCom;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isEncryption() {
		return encryption;
	}

	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}

	public String getCipherName() {
		return cipherName;
	}

	public void setCipherName(String cipherName) {
		this.cipherName = cipherName;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public boolean isTextExtracted() {
		return textExtracted;
	}

	public void setTextExtracted(boolean textExtracted) {
		this.textExtracted = textExtracted;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public NodeLock getLock() {
		return lock;
	}

	public void setLock(NodeLock lock) {
		this.lock = lock;
	}

	public String getDocCode() { return docCode; }

	public void setDocCode(String docCode) { this.docCode = docCode; }

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Calendar getDocEffectiveDate() {
		return docEffectiveDate;
	}

	public void setDocEffectiveDate(Calendar docEffectiveDate) {
		this.docEffectiveDate = docEffectiveDate;
	}

	public Long getDocOrgId() {
		return docOrgId;
	}

	public void setDocOrgId(Long docOrgId) {
		this.docOrgId = docOrgId;
	}

	public Integer getDocConfidentiality() {
		return docConfidentiality;
	}

	public void setDocConfidentiality(Integer docConfidentiality) {
		this.docConfidentiality = docConfidentiality;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("uuid=").append(uuid);
		sb.append(", context=").append(context);
		sb.append(", path=").append(path);
		sb.append(", parent=").append(parent);
		sb.append(", author=").append(author);
		sb.append(", name=").append(name);
		sb.append(", created=").append(created == null ? null : created.getTime());
		sb.append(", lastModified=").append(lastModified == null ? null : lastModified.getTime());
		sb.append(", language=").append(language);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", mimeType=").append(mimeType);
		sb.append(", checkedOut=").append(checkedOut);
		sb.append(", encryption=").append(encryption);
		sb.append(", cipherName=").append(cipherName);
		sb.append(", subscriptors=").append(subscriptors);
		sb.append(", keywords=").append(keywords);
		sb.append(", categories=").append(categories);
		// sb.append(", properties=").append(properties); Prevents Lazy Exception
		sb.append(", userPermissions=").append(userPermissions);
		sb.append(", rolePermissions=").append(rolePermissions);
		sb.append(", textExtracted=").append(textExtracted);
		sb.append(", locked=").append(locked);
		sb.append(", lock=").append(lock);
		sb.append(", docCode=").append(docCode);
		sb.append(", docName=").append(docName);
		sb.append(", docEffectiveDate=").append(docEffectiveDate);
		sb.append(", docOrgId=").append(docOrgId);
		sb.append(", docConfidentiality=").append(docConfidentiality);
		sb.append(", docExpiredDate=").append(docExpiredDate);
		sb.append(", docAuthor=").append(docAuthor);
		sb.append(", PageNumber=").append(pageNumber);
		sb.append(", publishCom=").append(publishCom);
		sb.append(", publishedYear=").append(publishedYear);
		sb.append("}");
		return sb.toString();
	}
}
