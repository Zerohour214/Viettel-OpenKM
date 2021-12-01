/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
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

package com.openkm.frontend.client.widget.properties;

import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.openkm.extension.frontend.client.widget.messaging.MessagingToolBarBox;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.bean.GWTPermission;
import com.openkm.frontend.client.bean.GWTUser;
import com.openkm.frontend.client.constants.ui.UIDesktopConstants;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.util.OKMBundleResources;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.ClipboardIcon;
import com.openkm.frontend.client.widget.ConfirmPopup;
import com.openkm.frontend.client.widget.properties.CategoryManager.CategoryToRemove;
import com.openkm.frontend.client.widget.properties.KeywordManager.KeywordToRemove;
import com.openkm.frontend.client.widget.thesaurus.ThesaurusSelectPopup;

/**
 * Document
 *
 * @author jllort
 */
public class Document extends Composite {
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);

	private FlexTable tableProperties;
	private FlexTable tableSubscribedUsers;
	private FlexTable table;
	private FlexTable vtTable;
	private GWTDocument document;
	private HTML subcribedUsersText;
	private Image proposeSubscribeImage;
	private HorizontalPanel hPanelSubscribedUsers;
	private CategoryManager categoryManager;

	private TransmitManager transmitManager;

	private KeywordManager keywordManager;
	private ScrollPanel scrollPanel;
	private boolean remove = true;

	/**
	 * Document
	 */
	public Document() {
		categoryManager = new CategoryManager(CategoryManager.ORIGIN_DOCUMENT);
		transmitManager = new TransmitManager(TransmitManager.ORIGIN_DOCUMENT);
		keywordManager = new KeywordManager(ThesaurusSelectPopup.DOCUMENT_PROPERTIES);
		document = new GWTDocument();
		table = new FlexTable();
		tableProperties = new FlexTable();
		vtTable = new FlexTable();
		tableSubscribedUsers = new FlexTable();
		scrollPanel = new ScrollPanel(table);


		tableProperties.setHTML(0, 0, "<b>" + Main.i18n("document.uuid") + "</b>");
		tableProperties.setHTML(0, 1, "");
		tableProperties.setHTML(1, 0, "<b>" + Main.i18n("document.name") + "</b>");
		tableProperties.setHTML(1, 1, "");
		tableProperties.setHTML(2, 0, "<b>" + Main.i18n("document.folder") + "</b>");
		tableProperties.setHTML(3, 1, "");
		tableProperties.setHTML(3, 0, "<b>" + Main.i18n("document.size") + "</b>");
		tableProperties.setHTML(4, 1, "");
		tableProperties.setHTML(4, 0, "<b>" + Main.i18n("document.created") + "</b>");
		tableProperties.setHTML(5, 1, "");
		tableProperties.setHTML(5, 0, "<b>" + Main.i18n("document.lastmodified") + "</b>");
		tableProperties.setHTML(5, 1, "");
		tableProperties.setHTML(6, 0, "<b>" + Main.i18n("document.mimetype") + "</b>");
		tableProperties.setHTML(6, 1, "");
		tableProperties.setHTML(7, 0, "<b>" + Main.i18n("document.keywords") + "</b>");
		tableProperties.setHTML(7, 1, "");
		tableProperties.setHTML(8, 0, "<b>" + Main.i18n("document.status") + "</b>");
		tableProperties.setHTML(8, 1, "");
		tableProperties.setHTML(9, 0, "<b>" + Main.i18n("document.subscribed") + "</b>");
		tableProperties.setHTML(9, 1, "");
		tableProperties.setHTML(10, 0, "<b>" + Main.i18n("document.history.size") + "</b>");
		tableProperties.setHTML(10, 1, "");
//		tableProperties.setHTML(11, 0, "<b>" + Main.i18n("document.url") + "</b>");
//		tableProperties.setWidget(11, 1, new HTML(""));
//		tableProperties.setHTML(12, 0, "<b>" + Main.i18n("document.webdav") + "</b>");
//		tableProperties.setWidget(12, 1, new HTML(""));


		vtTable.setHTML(0, 0, "<b>" + Main.i18n("document.code") + "</b>");
		vtTable.setHTML(0, 1, "");
		vtTable.setHTML(1, 0, "<b>" + Main.i18n("document.docName") + "</b>");
		vtTable.setHTML(1, 1, "");
		vtTable.setHTML(2, 0, "<b>" + Main.i18n("document.effectiveDate") + "</b>");
		vtTable.setHTML(2, 1, "");
		vtTable.setHTML(3, 0, "<b>" + Main.i18n("document.docExpiredDate") + "</b>");
		vtTable.setHTML(3, 1, "");
		vtTable.setHTML(4, 0, "<b>" + Main.i18n("document.confidentiality.level") + "</b>");
		vtTable.setHTML(4, 1, "");
		vtTable.setHTML(5, 0, "<b>" + Main.i18n("document.docAuthor") + "</b>");
		vtTable.setHTML(5, 1, "");
		vtTable.setHTML(6, 0, "<b>" + Main.i18n("document.publishCom") + "</b>");
		vtTable.setHTML(6, 1, "");
		vtTable.setHTML(7, 0, "<b>" + Main.i18n("document.publishedYear") + "</b>");
		vtTable.setHTML(7, 1, "");
		vtTable.setHTML(8, 0, "<b>" + Main.i18n("document.pageNumber") + "</b>");
		vtTable.setHTML(8, 1, "");
		vtTable.setHTML(9, 0, "<b>" + Main.i18n("document.org") + "</b>");
		vtTable.setHTML(9, 1, "");

		tableProperties.getCellFormatter().setVerticalAlignment(7, 0, HasAlignment.ALIGN_TOP);

		// Sets the tagcloud
		keywordManager.getKeywordCloud().setWidth("350px");

		VerticalPanel vPanel2 = new VerticalPanel();

		hPanelSubscribedUsers = new HorizontalPanel();
		subcribedUsersText = new HTML("<b>" + Main.i18n("document.subscribed.users") + "<b>");
		proposeSubscribeImage = new Image(OKMBundleResources.INSTANCE.proposeSubscription());
		proposeSubscribeImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MessagingToolBarBox.get().executeProposeSubscription(document.getUuid(), GWTDocument.TYPE);
			}
		});
		hPanelSubscribedUsers.add(subcribedUsersText);
		hPanelSubscribedUsers.add(new HTML("&nbsp;"));
		hPanelSubscribedUsers.setCellVerticalAlignment(subcribedUsersText, HasAlignment.ALIGN_MIDDLE);

		vPanel2.add(hPanelSubscribedUsers);
		vPanel2.add(tableSubscribedUsers);
		HTML space2 = new HTML("");
		vPanel2.add(space2);
		vPanel2.add(keywordManager.getKeywordCloudText());
		vPanel2.add(keywordManager.getKeywordCloud());

		HTML space3 = new HTML("");
		vPanel2.add(space3);


		vPanel2.add(categoryManager.getPanelCategories());
		vPanel2.add(categoryManager.getSubscribedCategoriesTable());

		HTML space4 = new HTML("");
		vPanel2.add(space4);
		vPanel2.add(transmitManager.getPanelCategories());
		vPanel2.add(transmitManager.getSubscribedCategoriesTable());

		vPanel2.setCellHeight(space2, "10px");
		vPanel2.setCellHeight(space3, "10px");
		vPanel2.setCellHeight(space4, "10px");

		table.setWidget(0, 0, tableProperties);
		table.setHTML(0, 1, "");
		table.setWidget(0, 2, vtTable );
		table.setHTML(0, 3, "");
		table.setWidget(0, 4, vPanel2);

		// The hidden column extends table to 100% width
		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setWidth(0, 1, "25px");
		cellFormatter.setWidth(0, 3, "25px");
		cellFormatter.setVerticalAlignment(0, 0, HasAlignment.ALIGN_TOP);
		cellFormatter.setVerticalAlignment(0, 2, HasAlignment.ALIGN_TOP);
		cellFormatter.setVerticalAlignment(0, 4, HasAlignment.ALIGN_TOP);

		// Sets wordWrap for al rows
		for (int i = 0; i < 11; i++) {
			setRowWordWarp(i, 0, true, tableProperties);
		}

		for (int i = 0; i < 11; i++) {
			setRowWordWarp(i, 0, true, vtTable);
		}

		setRowWordWarp(0, 0, true, tableSubscribedUsers);

		tableProperties.setStyleName("okm-DisableSelect");
//		vtTable.setStyleName("okm-DisableSelect");
		tableSubscribedUsers.setStyleName("okm-DisableSelect");
		proposeSubscribeImage.addStyleName("okm-Hyperlink");
		tableProperties.addStyleName("okm-tableProperties-renew");
		vtTable.addStyleName("okm-tableProperties-renew");




		initWidget(scrollPanel);
	}

	/**
	 * Set the WordWarp for all the row cells
	 *
	 * @param row     The row cell
	 * @param columns Number of row columns
	 * @param warp
	 * @param table   The table to change word wrap
	 */
	private void setRowWordWarp(int row, int columns, boolean warp, FlexTable table) {
		CellFormatter cellFormatter = table.getCellFormatter();

		for (int i = 0; i < columns; i++) {
			cellFormatter.setWordWrap(row, i, warp);
		}
	}

	/**
	 * Sets the document values
	 *
	 * @param doc The document object
	 */
	public void set(GWTDocument doc) {
		this.document = doc;

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(new HTML(doc.getUuid()));
		hPanel.add(Util.hSpace("3px"));
		hPanel.add(new ClipboardIcon(doc.getUuid()));

		tableProperties.setWidget(0, 1, hPanel);
		tableProperties.setHTML(1, 1, doc.getName());
		tableProperties.setHTML(2, 1, doc.getParentPath());
		tableProperties.setHTML(3, 1, Util.formatSize(doc.getActualVersion().getSize()));
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		tableProperties.setHTML(4, 1, dtf.format(doc.getCreated()) + " " + Main.i18n("document.by") + " " + doc.getUser().getUsername());
		tableProperties.setHTML(5, 1, dtf.format(doc.getLastModified()) + " " + Main.i18n("document.by") + " " + doc.getActualVersion().getUser().getUsername());
		tableProperties.setHTML(6, 1, doc.getMimeType());
		tableProperties.setWidget(7, 1, keywordManager.getKeywordPanel());

		// Enable select
		tableProperties.getFlexCellFormatter().setStyleName(0, 1, "okm-EnableSelect");
		tableProperties.getFlexCellFormatter().setStyleName(1, 1, "okm-EnableSelect");
		tableProperties.getFlexCellFormatter().setStyleName(2, 1, "okm-EnableSelect");

		remove = ((doc.getPermissions() & GWTPermission.WRITE) == GWTPermission.WRITE && !doc.isCheckedOut() &&
				!(doc.isLocked() && !doc.getLockInfo().getOwner().equals(Main.get().workspaceUserProperties.getUser())));

		if (doc.isCheckedOut()) {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.checkout") + " " + doc.getLockInfo().getUser().getUsername());
		} else if (doc.isLocked()) {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.locked") + " " + doc.getLockInfo().getUser().getUsername());
		} else {
			tableProperties.setHTML(8, 1, Main.i18n("document.status.normal"));
		}

		if (doc.isSubscribed()) {
			tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.yes"));
		} else {
			tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.no"));
		}

//		// URL clipboard button
//		String url = Main.get().workspaceUserProperties.getApplicationURL();
//		url += "?uuid=" + URL.encodeQueryString(URL.encodeQueryString(document.getUuid()));
//		tableProperties.setWidget(11, 1, new ClipboardIcon(url));

		// Webdav button
//		String webdavUrl = Main.get().workspaceUserProperties.getApplicationURL();
//		String webdavPath = document.getPath();
//
//		// Replace only in case webdav fix is enabled
//		if (Main.get().workspaceUserProperties.getWorkspace() != null && Main.get().workspaceUserProperties.getWorkspace().isWebdavFix()) {
//			webdavPath = webdavPath.replace("okm:", "okm_");
//		}

		// Login case write empty folder
//		if (!webdavUrl.isEmpty()) {
//			webdavPath = Util.encodePathElements(webdavPath);
//			webdavUrl = webdavUrl.substring(0, webdavUrl.lastIndexOf('/')) + "/webdav" + webdavPath;
//		}
//
//		tableProperties.setWidget(12, 1, new ClipboardIcon(webdavUrl));

		vtTable.setHTML(0, 1, SafeHtmlUtils.htmlEscape(document.getDocCode()));
		vtTable.setHTML(1, 1, SafeHtmlUtils.htmlEscape(document.getDocName()));
		if (doc.getDocEffectiveDate() == null) {
			vtTable.setHTML(2, 1, "");
		} else {
			vtTable.setHTML(2, 1, dtf.format(doc.getDocEffectiveDate()));
		}
		if (doc.getDocExpiredDate() == null) {
			vtTable.setHTML(3, 1, "");
		}
		else {
			vtTable.setHTML(3, 1, dtf.format(doc.getDocExpiredDate()));
		}

		vtTable.setHTML(4, 1, Main.i18n(document.getDocConfidentialityName()));
		vtTable.setHTML(5, 1, SafeHtmlUtils.htmlEscape(document.getDocAuthor()));
		vtTable.setHTML(6, 1, SafeHtmlUtils.htmlEscape(document.getPublishCom()));
		vtTable.setHTML(7, 1, String.valueOf(document.getPublishYear()));
		vtTable.setHTML(8, 1, String.valueOf(doc.getPageNumber()));
		vtTable.setHTML(9, 1, document.getDocOrgName());
		// Enables or disables change keywords with user permissions and document is not check-out or locked
		if (remove) {
			keywordManager.setVisible(true);
			categoryManager.setVisible(true);
		} else {
			keywordManager.setVisible(false);
			categoryManager.setVisible(false);
		}

		// Propose subscription must be disabled on trash
		if (Main.get().mainPanel.desktop.navigator.getStackIndex() == UIDesktopConstants.NAVIGATOR_TRASH) {
			proposeSubscribeImage.setVisible(false);
		} else {
			proposeSubscribeImage.setVisible(true);
		}

		getVersionHistorySize();

		getOrgsByDocId();

		// Sets wordWrap for al rows
		for (int i = 0; i < 11; i++) {
			setRowWordWarp(i, 1, true, tableProperties);
		}
		for (int i = 0; i < 11; i++) {
			setRowWordWarp(i, 1, true, vtTable);
		}

		// Remove all table rows
		tableSubscribedUsers.removeAllRows();

		// Sets the document subscribers
		for (GWTUser subscriptor : doc.getSubscriptors()) {
			tableSubscribedUsers.setHTML(tableSubscribedUsers.getRowCount(), 0, subscriptor.getUsername());
			setRowWordWarp(tableSubscribedUsers.getRowCount() - 1, 0, true, tableSubscribedUsers);
		}

		int actualView = Main.get().mainPanel.desktop.navigator.getStackIndex();
		// Some data must not be visible on personal view
		if (actualView == UIDesktopConstants.NAVIGATOR_PERSONAL) {
			subcribedUsersText.setVisible(false);
			tableSubscribedUsers.setVisible(false);
		} else {
			subcribedUsersText.setVisible(true);
			tableSubscribedUsers.setVisible(true);
		}

		// keywords
		keywordManager.reset();
		keywordManager.setObject(doc, remove);
		keywordManager.drawAll();

		// Categories
		categoryManager.removeAllRows();
		categoryManager.setObject(doc, remove);
		categoryManager.drawAll();

		//getUserReadDoc
		Main.get().mainPanel.dashboard.userDashboard.isUserReadDoc(Main.get().workspaceUserProperties.getUser().getId(), document.getUuid());
	}

	/**
	 * Lang refresh
	 */
	public void langRefresh() {
		tableProperties.setHTML(0, 0, "<b>" + Main.i18n("document.uuid") + "</b>");
		tableProperties.setHTML(1, 0, "<b>" + Main.i18n("document.name") + "</b>");
		tableProperties.setHTML(2, 0, "<b>" + Main.i18n("document.folder") + "</b>");
		tableProperties.setHTML(3, 0, "<b>" + Main.i18n("document.size") + "</b>");
		tableProperties.setHTML(4, 0, "<b>" + Main.i18n("document.created") + "</b>");
		tableProperties.setHTML(5, 0, "<b>" + Main.i18n("document.lastmodified") + "</b>");
		tableProperties.setHTML(6, 0, "<b>" + Main.i18n("document.mimetype") + "</b>");
		tableProperties.setHTML(7, 0, "<b>" + Main.i18n("document.keywords") + "</b>");
		tableProperties.setHTML(8, 0, "<b>" + Main.i18n("document.status") + "</b>");
		tableProperties.setHTML(9, 0, "<b>" + Main.i18n("document.subscribed") + "</b>");
		tableProperties.setHTML(10, 0, "<b>" + Main.i18n("document.history.size") + "</b>");
//		tableProperties.setHTML(11, 0, "<b>" + Main.i18n("document.url") + "</b>");
//		tableProperties.setHTML(12, 0, "<b>" + Main.i18n("document.webdav") + "</b>");

		vtTable.setHTML(0, 0, "<b>" + Main.i18n("document.code") + "</b>");
		vtTable.setHTML(1, 0, "<b>" + Main.i18n("document.docName") + "</b>");
		vtTable.setHTML(2, 0, "<b>" + Main.i18n("document.effectiveDate") + "</b>");
		vtTable.setHTML(3, 0, "<b>" + Main.i18n("document.org") + "</b>");
		vtTable.setHTML(4, 0, "<b>" + Main.i18n("document.confidentiality.level") + "</b>");
		vtTable.setHTML(5, 0, "<b>" + Main.i18n("document.docAuthor") + "</b>");
		vtTable.setHTML(6, 0, "<b>" + Main.i18n("document.publishCom") + "</b>");
		vtTable.setHTML(7, 0, "<b>" + Main.i18n("document.publishedYear") + "</b>");
		vtTable.setHTML(8, 0, "<b>" + Main.i18n("document.pageNumber") + "</b>");
		vtTable.setHTML(9, 0, "<b>" + Main.i18n("document.docExpiredDate") + "</b>");

		subcribedUsersText.setHTML("<b>" + Main.i18n("document.subscribed.users") + "<b>");
		keywordManager.langRefresh();
		categoryManager.langRefresh();

		if (document != null) {
			DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));

			if (document.getCreated() != null) {
				tableProperties.setHTML(4, 1, dtf.format(document.getCreated()) + " " + Main.i18n("document.by") + " " + document.getUser().getUsername());
			}

			if (document.getLastModified() != null) {
				tableProperties.setHTML(5, 1, dtf.format(document.getLastModified()) + " " + Main.i18n("document.by") + " " + document.getActualVersion().getUser().getUsername());
			}

			if (document.isCheckedOut()) {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.checkout") + " " + document.getLockInfo().getUser().getUsername());
			} else if (document.isLocked()) {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.locked") + " " + document.getLockInfo().getUser().getUsername());
			} else {
				tableProperties.setHTML(8, 1, Main.i18n("document.status.normal"));
			}

			if (document.isSubscribed()) {
				tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.yes"));
			} else {
				tableProperties.setHTML(9, 1, Main.i18n("document.subscribed.no"));
			}
		}
	}

	/**
	 * Callback GetVersionHistorySize document
	 */
	final AsyncCallback<Long> callbackGetVersionHistorySize = new AsyncCallback<Long>() {
		public void onSuccess(Long result) {
			tableProperties.setHTML(10, 1, Util.formatSize(result.longValue()));
			Main.get().mainPanel.desktop.browser.tabMultiple.status.unsetGetVersionHistorySize();
		}

		public void onFailure(Throwable caught) {
			Main.get().mainPanel.desktop.browser.tabMultiple.status.unsetGetVersionHistorySize();
			Main.get().showError("GetVersionHistorySize", caught);
		}
	};

	/**
	 * getVersionHistorySize document
	 */
	public void getVersionHistorySize() {
		Main.get().mainPanel.desktop.browser.tabMultiple.status.setGetVersionHistorySize();
		documentService.getVersionHistorySize(document.getUuid(), callbackGetVersionHistorySize);
	}

	/**
	 * addKeyword document
	 */
	public void addKeyword(String keyword) {
		keywordManager.addKeyword(keyword);
	}

	public void addListKeywords(List<String> listKeywords) { keywordManager.addListKeywords(listKeywords); }

	public void updateKeyword(){
		keywordManager.updateKeyword();
	}
	/**
	 * removeKeyword document
	 */
	public void removeKeyword(String keyword) {
		keywordManager.removeKeyword(keyword);
	}

	/**
	 * addCategory document
	 */
	public void addCategory(GWTFolder category) {
		categoryManager.addCategory(category);
	}

	/**
	 * removeCategory document
	 */
	public void removeCategory(String UUID) {
		categoryManager.removeCategory(UUID);
	}

	/**
	 * removeCategory
	 *
	 * @param category
	 */
	public void removeCategory(CategoryToRemove obj) {
		categoryManager.removeCategory(obj);
	}

	/**
	 * Sets visibility to buttons ( true / false )
	 *
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible) {
		keywordManager.setVisible(visible);
		categoryManager.setVisible(visible);
		transmitManager.setVisible(visible);
	}

	/**
	 * showProposedSusbcription
	 */
	public void showProposedSusbcription() {
		// Adds to panel
		hPanelSubscribedUsers.add(proposeSubscribeImage);
	}

	/**
	 * Removes a key
	 *
	 * @param keyword The key to be removed
	 */
	public void removeKey(String keyword) {
		keywordManager.removeKey(keyword);
	}

	/**
	 * removeKeyword
	 *
	 * @param ktr
	 */
	public void removeKeyword(KeywordToRemove ktr) {
		keywordManager.removeKeyword(ktr);
	}

	/**
	 * addKeywordToPendinList
	 *
	 * @param key
	 */
	public void addKeywordToPendinList(String key) {
		keywordManager.addKeywordToPendinList(key);
	}

	/**
	 * Adds keywords sequentially
	 */
	public void addPendingKeyWordsList() {
		keywordManager.addPendingKeyWordsList();
	}

	/**
	 * getKeywords
	 *
	 * @return
	 */
	public Collection<String> getKeywords() {
		return document.getKeywords();
	}

	/**
	 * @param enabled
	 */
	public void setKeywordEnabled(boolean enabled) {
		keywordManager.setKeywordEnabled(enabled);
	}

	/**
	 * showAddCategory
	 */
	public void showAddCategory() {
		categoryManager.showAddCategory();
	}

	/**
	 * showRemoveCategory
	 */
	public void showRemoveCategory() {
		categoryManager.showRemoveCategory();
	}

	/**
	 * showAddKeyword
	 */
	public void showAddKeyword() {
		keywordManager.showAddKeyword();
	}

	/**
	 * showRemoveKeyword
	 */
	public void showRemoveKeyword() {
		keywordManager.showRemoveKeyword();
	}

	public void transmit(String orgs) {
		transmitManager.transmit(orgs, document.getUuid());
	}

	public void getOrgsByDocId() {
		RequestBuilder builder = new RequestBuilder(
				RequestBuilder.GET, Main.CONTEXT + "/services/rest/document/getOrgsByDocId?docId=" + document.getUuid());
		builder.setHeader("Accept", "application/json");

		try {
			builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request,
													   Response response) {
							transmitManager.setOrgs(response.getText());

						}

						@Override
						public void onError(Request request, Throwable throwable) {

						}
					}
			);
		} catch (RequestException e) {
			e.printStackTrace();
		}

	}
	public void setReadDoc(){
		try {
			Main.get().mainPanel.dashboard.userDashboard.setUserReadDoc(Main.get().workspaceUserProperties.getUser().getId(), document.getUuid());
			Main.get().mainPanel.dashboard.userDashboard.getMustReadDocuments();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
