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

package com.openkm.bean;

import java.text.DateFormat;

import com.openkm.core.DatabaseException;
import com.openkm.dao.OrganizationDao;
import com.openkm.frontend.client.Main;

import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author pavila
 *
 */
@XmlRootElement(name = "document")
public class Document extends Node {
	private static final long serialVersionUID = 1L;

	public static final String TYPE = "okm:document";
	public static final String CONTENT = "okm:content";
	public static final String CONTENT_TYPE = "okm:resource";
	public static final String SIZE = "okm:size";
	public static final String LANGUAGE = "okm:language";
	public static final String VERSION_COMMENT = "okm:versionComment";
	public static final String NAME = "okm:name";
	public static final String TEXT = "okm:text";
	public static final String TITLE = "okm:title";
	public static final String DESCRIPTION = "okm:description";

	private String title = "";
	private String description = "";
	private String language = "";
	private Calendar lastModified;
	private String mimeType;
	private boolean locked;
	private boolean checkedOut;
	private Version actualVersion;
	private LockInfo lockInfo;
	private boolean signed;
	private boolean convertibleToPdf;
	private boolean convertibleToSwf;
	private String cipherName;
	private String docCode;
	private String docName;
	private Calendar docEffectiveDate;
	private long docOrgId;
	private String docOrgName;
	private int docConfidentiality;
	private String docConfidentialityName;
	private Calendar docExpiredDate;
	private String docAuthor;
	private String publishCom;
	private Integer publisedYear;
	private Integer pageNumber;
	private String id;

	public Document() {
	}

	public Document(HashMap<String, Object> vtProperties) {
		setVtProperties(vtProperties);
	}

	public LockInfo getLockInfo() {
		return lockInfo;
	}

	public void setLockInfo(LockInfo lockInfo) {
		this.lockInfo = lockInfo;
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

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
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

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Version getActualVersion() {
		return actualVersion;
	}

	public void setActualVersion(Version actualVersion) {
		this.actualVersion = actualVersion;
	}

	public boolean isConvertibleToPdf() {
		return convertibleToPdf;
	}

	public void setConvertibleToPdf(boolean convertibleToPdf) {
		this.convertibleToPdf = convertibleToPdf;
	}

	public boolean isConvertibleToSwf() {
		return convertibleToSwf;
	}

	public void setConvertibleToSwf(boolean convertibleToSwf) {
		this.convertibleToSwf = convertibleToSwf;
	}

	public void setCipherName(String cipherName) {
		this.cipherName = cipherName;
	}

	public String getCipherName() {
		return cipherName;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public String getDocCode() { return docCode; }

	public void setDocCode(String docCode) { this.docCode = docCode; }

	public String getDocName() { return docName; }

	public void setDocName(String docName) { this.docName = docName; }

	public Calendar getDocEffectiveDate() {
		return docEffectiveDate;
	}

	public void setDocEffectiveDate(Calendar docEffectiveDate) {
		this.docEffectiveDate = docEffectiveDate;
	}

	public long getDocOrgId() {
		return docOrgId;
	}

	public void setDocOrgId(long docOrgId) {
		this.docOrgId = docOrgId;
		try {
			setDocOrgName(OrganizationDao.getInstance().getOrgNameById(docOrgId));
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public String getDocOrgName() {
		return docOrgName;
	}

	public void setDocOrgName(String docOrgName) {
		this.docOrgName = docOrgName;
	}

	public int getDocConfidentiality() {
		return docConfidentiality;
	}

	public void setDocConfidentiality(int docConfidentiality) {
		this.docConfidentiality = docConfidentiality;
		String normal = "document.confidentiality.normal";
		String high = "document.confidentiality.high";
		setDocConfidentialityName(docConfidentiality == 0 ? normal : high);
	}

	public String getDocConfidentialityName() {
		return docConfidentialityName;
	}

	public void setDocConfidentialityName(String docConfidentialityName) {
		this.docConfidentialityName = docConfidentialityName;
	}

	public Calendar getDocExpiredDate() { return docExpiredDate; }

	public void setDocExpiredDate(Calendar docExpiredDate) { this.docExpiredDate = docExpiredDate; }

	public String getDocAuthor() { return docAuthor; }

	public void setDocAuthor(String docAuthor) { this.docAuthor = docAuthor; }

	public String getPublishCom() { return publishCom; }

	public void setPublishCom(String publishCom) { this.publishCom = publishCom; }

	public Integer getPublisedYear() { return publisedYear; }

	public void setPublisedYear(Integer publisedYear) { this.publisedYear = publisedYear; }

	public Integer getPageNumber() { return pageNumber; }

	public void setPageNumber(Integer pageNumber) { this.pageNumber = pageNumber; }

	public void setVtProperties(HashMap<String, Object> vtProperties) {
		if(vtProperties != null) {

			if (vtProperties.get("docCode") != null && !vtProperties.get("docCode").toString().trim().isEmpty()){
				setDocCode(vtProperties.get("docCode").toString());
			}
			if (vtProperties.get("docName") != null && !vtProperties.get("docName").toString().trim().isEmpty()){
				setDocName(vtProperties.get("docName").toString());
			}

			if (vtProperties.get("docOrgId") != null && !vtProperties.get("docOrgId").toString().trim().isEmpty()){
				setDocOrgId(Long.parseLong(vtProperties.get("docOrgId").toString()));
			}
			if (vtProperties.get("docConfidentiality") != null && !vtProperties.get("docConfidentiality").toString().trim().isEmpty()){
				setDocConfidentiality(Integer.parseInt(vtProperties.get("docConfidentiality").toString()));
			}
			if(vtProperties.get("docEffectiveDate") != null){
				setDocEffectiveDate((Calendar) vtProperties.get("docEffectiveDate"));
			}
			if(vtProperties.get("docExpiredDate") != null){
				setDocExpiredDate((Calendar) vtProperties.get("docExpiredDate"));
			}
			if (vtProperties.get("publishCom") != null && !vtProperties.get("publishCom").toString().trim().isEmpty()){
				setPublishCom(vtProperties.get("publishCom").toString());
			}
			if(vtProperties.get("publishedYear") != null && !vtProperties.get("publishedYear").toString().trim().isEmpty()){
				setPublisedYear((Integer) vtProperties.get("publishedYear"));
			}
			if(vtProperties.get("publishedYear") != null && !vtProperties.get("pageNumber").toString().trim().isEmpty()){
				setPageNumber((Integer) vtProperties.get("pageNumber"));
			}
			if (vtProperties.get("docAuthor") != null && !vtProperties.get("docAuthor").toString().trim().isEmpty()){
				setDocAuthor(vtProperties.get("docAuthor").toString());
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path=").append(path);
		sb.append(", title=").append(title);
		sb.append(", description=").append(description);
		sb.append(", mimeType=").append(mimeType);
		sb.append(", author=").append(author);
		sb.append(", permissions=").append(permissions);
		sb.append(", created=").append(created == null ? null : created.getTime());
		sb.append(", lastModified=").append(lastModified == null ? null : lastModified.getTime());
		sb.append(", keywords=").append(keywords);
		sb.append(", categories=").append(categories);
		sb.append(", locked=").append(locked);
		sb.append(", lockInfo=").append(lockInfo);
		sb.append(", actualVersion=").append(actualVersion);
		sb.append(", subscribed=").append(subscribed);
		sb.append(", subscriptors=").append(subscriptors);
		sb.append(", uuid=").append(uuid);
		sb.append(", convertibleToPdf=").append(convertibleToPdf);
		sb.append(", convertibleToSwf=").append(convertibleToSwf);
		sb.append(", cipherName=").append(cipherName);
		sb.append(", notes=").append(notes);
		sb.append(", language=").append(language);
		sb.append(", docCode=").append(docCode);
		sb.append(", docName=").append(docName);
		sb.append(", docEffectiveDate=").append(docEffectiveDate);
		sb.append(", docExpiredDate=").append(docExpiredDate);
		sb.append(", docAuthor=").append(docAuthor);
		sb.append(", publishCom=").append(publishCom);
		sb.append(", publishedYear=").append(publisedYear);
		sb.append(", pageNumber=").append(pageNumber);
		sb.append("}");
		return sb.toString();
	}
}
