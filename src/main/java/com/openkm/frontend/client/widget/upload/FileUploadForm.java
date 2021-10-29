/**
 * Copyright (c) 2006-2017  Paco Avila & Josep Llort
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.openkm.frontend.client.widget.upload;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.constants.service.RPCService;

import java.util.Date;

/**
 * FileUploadForm
 *
 * @author jllort
 *
 */
public class FileUploadForm extends Composite {

	private FormPanel uploadForm;
	private VerticalPanel vPanel;
	private TextBox inputPath;
	private TextBox inputAction;
	private TextBox inputRenameDocument;
	private CheckBox notifyToUser;
	private CheckBox importZip;
	private TextBox docCode;
	private TextBox docName;
	private DateBox docEffectiveDate;
	private TextBox docOrg;
	private TextBox docConfidentiality;
	private DateBox docExpiredDate;
	private TextBox publishCom;
	private TextBox docAuthor;
	private TextBox pageNumber;
	private TextBox publishedYear;

	private TextArea versionComment;
	private TextBox mails;
	private TextBox users;
	private TextBox roles;
	private FileUpload fileUpload;
	private TextArea message;
	private TextBox increaseVersion;

	/**
	 * FileUploadForm
	 */
	public FileUploadForm(FileUpload fileUpload, String size) {
		this.fileUpload = fileUpload;
		fileUpload.setStyleName("okm-Input");
		fileUpload.getElement().setAttribute("size", size);
		// Set the name of the upload file form element
		fileUpload.setName("uploadFormElement");

		uploadForm = new FormPanel();
		vPanel = new VerticalPanel();
		inputPath = new TextBox();
		inputAction = new TextBox();
		inputRenameDocument = new TextBox();
		notifyToUser = new CheckBox();
		importZip = new CheckBox();
		docCode = new TextBox();
		docName = new TextBox();
		docEffectiveDate = new DateBox();
		docOrg = new TextBox();
		docConfidentiality = new TextBox();
		versionComment = new TextArea();
		docExpiredDate = new DateBox();
		publishCom = new TextBox();
		publishedYear = new TextBox();
		docAuthor = new TextBox();
		pageNumber = new TextBox();
		versionComment = new TextArea();
		mails = new TextBox();
		users = new TextBox();
		roles = new TextBox();
		message = new TextArea();
		increaseVersion = new TextBox();

		// Set Form details
		// Set the action to call on submit
		uploadForm.setAction(RPCService.FileUploadService);
		// Set the form encoding to multipart to indicate a file upload
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		// Set the method to Post
		uploadForm.setMethod(FormPanel.METHOD_POST);

		inputPath.setName("path");
		inputPath.setVisible(false);
		vPanel.add(inputPath);

		inputAction.setName("action");
		inputAction.setVisible(false);
		vPanel.add(inputAction);

		inputRenameDocument.setName("rename");
		inputRenameDocument.setVisible(false);
		vPanel.add(inputRenameDocument);

		notifyToUser.setName("notify");
		notifyToUser.setVisible(false);
		vPanel.add(notifyToUser);

		importZip.setName("importZip");
		importZip.setVisible(false);
		vPanel.add(importZip);

		docCode.setName("docCode");
		docCode.setVisible(false);
		vPanel.add(docCode);

		docName.setName("docName");
		docName.setVisible(false);
		vPanel.add(docName);

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));
		docEffectiveDate.getTextBox().setName("docEffectiveDate");
		docEffectiveDate.setVisible(false);
		docEffectiveDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		vPanel.add(docEffectiveDate);

		docOrg.setName("docOrgId");
		docOrg.setVisible(false);
		vPanel.add(docOrg);

		docConfidentiality.setName("docConfidentiality");
		docConfidentiality.setVisible(false);
		vPanel.add(docConfidentiality);

		docExpiredDate.getTextBox().setName("docExpiredDate");
		docExpiredDate.setVisible(false);
		docExpiredDate.setFormat(new DateBox.DefaultFormat(dateFormat));
		vPanel.add(docExpiredDate);

		docAuthor.setName("docAuthor");
		docAuthor.setVisible(false);
		vPanel.add(docAuthor);

		publishCom.setName("publishCom");
		publishCom.setVisible(false);
		vPanel.add(publishCom);

		publishedYear.setName("publishedYear");
		publishedYear.setVisible(false);
		vPanel.add(publishedYear);

		pageNumber.setName("pageNumber");
		pageNumber.setVisible(false);
		vPanel.add(pageNumber);

		versionComment.setName("comment");
		versionComment.setVisible(false);
		vPanel.add(versionComment);

		mails.setName("mails");
		mails.setVisible(false);
		vPanel.add(mails);

		users.setName("users");
		users.setVisible(false);
		vPanel.add(users);

		roles.setName("roles");
		roles.setVisible(false);
		vPanel.add(roles);

		message.setName("message");
		message.setVisible(false);
		vPanel.add(message);

		increaseVersion.setName("increaseVersion");
		increaseVersion.setText("0");
		increaseVersion.setVisible(false);
		vPanel.add(increaseVersion);

		vPanel.add(fileUpload);

		uploadForm.setWidget(vPanel);

		initWidget(uploadForm);
	}

	/**
	 * addSubmitCompleteHandler
	 *
	 * @param submitCompleHandler
	 */
	public void addSubmitCompleteHandler(SubmitCompleteHandler submitCompleHandler) {
		uploadForm.addSubmitCompleteHandler(submitCompleHandler);
	}

	/**
	 * setEncoding
	 *
	 * @param encodingType
	 */
	public void setEncoding(String encodingType) {
		uploadForm.setEncoding(encodingType);
	}

	/**
	 * Set the path
	 * @param path String path
	 */
	public void setPath(String path) {
		inputPath.setText(path);
	}

	/**
	 * setAction
	 *
	 * @param action
	 */
	public void setAction(String action) {
		inputAction.setText(action);
	}

	/**
	 * setRename
	 *
	 * @param rename
	 */
	public void setRename(String rename) {
		if (rename != null && !rename.equals("")) {
			inputRenameDocument.setText(rename);
		}
	}

	/**
	 * setNotifyToUser
	 *
	 * @param value
	 */
	public void setNotifyToUser(boolean value) {
		notifyToUser.setValue(value);
	}

	/**
	 * isNotifyToUser
	 *
	 * @return
	 */
	public boolean isNotifyToUser() {
		return notifyToUser.getValue();
	}

	/**
	 * setImportZip
	 *
	 * @param value
	 */
	public void setImportZip(boolean value) {
		importZip.setValue(value);
	}

	/**
	 * isImportZip
	 *
	 * @return
	 */
	public boolean isImportZip() {
		return importZip.getValue();
	}

	/**
	 * setVersionCommnent
	 *
	 * @param comment
	 */
	public void setVersionCommnent(String comment) {
		versionComment.setText(comment);
	}

	/**
	 * setMails
	 *
	 * @param mails
	 */
	public void setMails(String mails) {
		this.mails.setText(mails);
	}

	/**
	 * setUsers
	 *
	 * @param users
	 */
	public void setUsers(String users) {
		this.users.setText(users);
	}

	/**
	 * setRoles
	 *
	 * @param roles
	 */
	public void setRoles(String roles) {
		this.roles.setText(roles);
	}

	/**
	 * setMessage
	 *
	 * @param message
	 */
	public void setMessage(String message) {
		this.message.setText(message);
	}

	/**
	 * setIncreaseMajorVersion
	 */
	public void setIncreaseVersion(int increaseVersion) {
		this.increaseVersion.setText(String.valueOf(increaseVersion));
	}

	/**
	 * setDocCode
	 *
	 * @return
	 * @param docCode
	 */
	public void setDocCode(String docCode) { this.docCode.setText(docCode); }

	/**
	 * getDocCode
	 *
	 * @return
	 */
	public String getDocCode() { return docCode.getText(); }

	/**
	 * setDocName
	 *
	 * @return
	 * @param docName
	 */
	public void setDocName(String docName) { this.docName.setText(docName); }

	/**
	 * getDocName
	 *
	 * @return
	 */
	public String getDocName() { return docName.getText(); }

	public Date getDocEffectiveDate() {
		return docEffectiveDate.getValue();
	}

	public void setDocEffectiveDate(Date docEffectiveDate) {
		this.docEffectiveDate.setValue(docEffectiveDate);
	}

	public void setDocOrg(String docOrg) { this.docOrg.setText(docOrg); }

	public String getDocOrg() { return docOrg.getText(); }

	public void setDocConfidentiality(String docConfidentiality) { this.docConfidentiality.setText(docConfidentiality); }

	public String getDocConfidentiality() { return docConfidentiality.getText(); }

	public Date getDocExpiredDate() {
		return docExpiredDate.getValue();
	}

	public void setDocExpiredDate(Date docExpiredDate) {
		this.docExpiredDate.setValue(docExpiredDate);;
	}

	public String getPublishCom() {
		return publishCom.getText();
	}

	public void setPublishCom(String publishCom) {
		this.publishCom.setText(publishCom);
	}

	public String getDocAuthor() {
		return docAuthor.getText();
	}

	public void setDocAuthor(String docAuthor) {
		this.docAuthor.setText(docAuthor);
	}

	public String getPageNumber() {
		return pageNumber.getText();
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber.setText(pageNumber);
	}

	public String getPublishedYear() {
		return publishedYear.getText();
	}

	public void setPublishedYear(String publishedYear) {
		this.publishedYear.setText(publishedYear);
	}

	/**
	 * getFileName
	 *
	 * @return
	 */
	public String getFileName() {
		return fileUpload.getFilename();
	}

	public void submit() {
		uploadForm.submit();
	}
}

