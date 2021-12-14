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

package com.openkm.frontend.client.widget.organizationVTX;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.openkm.extension.frontend.client.util.OkmConstants;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTFolder;
import com.openkm.frontend.client.widget.categories.FolderSelectTree;
import com.openkm.frontend.client.widget.categories.Status;

public class OrganizationVtxSelectPopup extends DialogBox {

	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	public ScrollPanel scrollDirectoryPanel;
	private VerticalPanel verticalDirectoryPanel;
	private FolderSelectTree folderSelectTree;
	private Button cancelButton;
	private Button actionButton;
	public Status status;


	JsArrayNumber orgCheckeds = (JsArrayNumber) JsArrayNumber.createArray();
	JsArrayString orgPathTrace = (JsArrayString) JsArrayString.createArray();


	public OrganizationVtxSelectPopup() {
		// Establishes auto-close when click outside
		super(false, true);

		status = new Status();
		status.setStyleName("okm-StatusPopup");

		vPanel = new VerticalPanel();
		vPanel.setWidth("600px");
		vPanel.setHeight("400px");
		hPanel = new HorizontalPanel();

		scrollDirectoryPanel = new ScrollPanel();
		scrollDirectoryPanel.setSize("600px", "400px");
		scrollDirectoryPanel.setStyleName("okm-Popup-text");
		verticalDirectoryPanel = new VerticalPanel();
		verticalDirectoryPanel.setSize("100%", "100%");
		folderSelectTree = new FolderSelectTree();
		folderSelectTree.setSize("100%", "100%");

		verticalDirectoryPanel.add(folderSelectTree);
		scrollDirectoryPanel.add(verticalDirectoryPanel);

		cancelButton = new Button(OkmConstants.ICON_NO_BUTTON + Main.i18n("button.close"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		actionButton = new Button(OkmConstants.ICON_ADD_BUTTON + Main.i18n("button.add"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				executeAction();
			}
		});

		vPanel.add(new HTML("<br>"));
		vPanel.add(scrollDirectoryPanel);
		vPanel.add(new HTML("<br>"));
		hPanel.add(cancelButton);
		HTML space = new HTML();
		space.setWidth("50px");
		hPanel.add(space);
		hPanel.add(actionButton);
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));

		vPanel.setCellHorizontalAlignment(scrollDirectoryPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHeight(scrollDirectoryPanel, "250px");

		cancelButton.setStyleName("okm-NoButton");
		cancelButton.addStyleName("btn");
		cancelButton.addStyleName("btn-warning");
		actionButton.setStyleName("okm-AddButton");
		actionButton.addStyleName("btn");
		actionButton.addStyleName("btn-success");


		super.hide();
		setWidget(vPanel);
	}

	public TreeItem generateTreeOrg(Long parentId, TreeItem root, Boolean auto, Boolean autoCheck) {

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Main.CONTEXT + "/services/rest/organization/getAllOrgChild?parent=" + parentId);

		try {
			if(auto) root.removeItems();
			builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request,
													   Response response) {

							JSONValue jsonValue = JSONParser.parseStrict(response.getText());
							JSONArray jsonArray = jsonValue.isArray();

							for (int i = 0; i < jsonArray.size(); ++i) {
								String orgName = jsonArray.get(i).isObject().get("name").isString().stringValue();
								Double orgParent = jsonArray.get(i).isObject().get("parent").isNumber().doubleValue();
								Double orgId = jsonArray.get(i).isObject().get("id").isNumber().doubleValue();
								String orgPath = jsonArray.get(i).isObject().get("path").isString().stringValue();

								HTML icon = new HTML("<i class='glyphicons glyphicons-folder-new'><i/>");
								HorizontalPanel hc = new HorizontalPanel();
								hc.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
								CheckBox checkbox = new CheckBox();

								if(auto) {
									if (autoCheck) {
										checkbox.setChecked(true);
										orgCheckeds.push(orgId);
									} else {
										checkbox.setChecked(false);
										for (int j = 0; j < orgCheckeds.length(); j++) {
											if (orgCheckeds.get(j) == orgId) {
												orgCheckeds.set(j, -1);
											}
										}
									}
								}

								for (int j = 0; j < orgCheckeds.length(); ++j) {
									if (orgCheckeds.get(j) == orgId) {
										checkbox.setValue(true);
										break;
									}
								}

								for (int j = 0; j < orgPathTrace.length(); ++j) {
									if (orgPathTrace.get(j).contains(orgPath)) {
										icon.setHTML("<i class='glyphicons glyphicons-folder-new' style='color: #27B45F'><i/>");
										break;
									}
								}

								hc.add(icon);
								hc.add(checkbox);
								hc.add(new Label(orgName));

								TreeItem orgNode = new TreeItem(hc);


								checkbox.addClickHandler(
										new ClickHandler() {
											@Override
											public void onClick(ClickEvent event) {
												actionButton.setEnabled(true);
												boolean checked = ((CheckBox) event.getSource()).getValue();
												if (checked) {
													orgCheckeds.push(orgId);
													generateTreeOrg(orgId.longValue(), orgNode, true, true);

												} else {
													for (int j = 0; j < orgCheckeds.length(); j++) {
														if (orgCheckeds.get(j) == orgId) {
															orgCheckeds.set(j, -1);
														}
													}
													generateTreeOrg(orgId.longValue(), orgNode, true, false);
												}
											}
										});

								root.setState(true);


								icon.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent clickEvent) {

										if (orgNode.getState()) {
											orgNode.setState(false);
											icon.setHTML("<i class='glyphicons glyphicons-folder-new'><i/>");
											for (int j = 0; j < orgPathTrace.length(); ++j) {
												if (orgPathTrace.get(j).contains(orgPath)) {
													icon.setHTML("<i class='glyphicons glyphicons-folder-new' style='color: #27B45F'><i/>");
													break;
												}
											}
										} else {
											orgNode.setState(true);
											icon.setHTML("<i class='glyphicons glyphicons-folder-open'><i/>");
											for (int j = 0; j < orgPathTrace.length(); ++j) {
												if (orgPathTrace.get(j).contains(orgPath)) {
													icon.setHTML("<i class='glyphicons glyphicons-folder-open' style='color: #27B45F'><i/>");
													break;
												}
											}
										}

										if (orgNode.getChildCount() == 0) {
											generateTreeOrg(orgId.longValue(), orgNode, false, false);
										}
									}
								});


								root.addItem(orgNode);


								if (auto) {
									if (autoCheck) {
										generateTreeOrg(orgId.longValue(), orgNode, true, true);
									} else {
										generateTreeOrg(orgId.longValue(), orgNode, true, false);
									}
								}
							}
							root.setState(true);
						}

						@Override
						public void onError(Request request, Throwable throwable) {

						}
					}
			);
		} catch (RequestException e) {
			e.printStackTrace();
		}
		return root;
	}

	public void drawOrgTree() {
		verticalDirectoryPanel.clear();
		Tree tree = new Tree();
		TreeItem root = new TreeItem(new Label("root"));
		root = generateTreeOrg(-1L, root, false, false);
		tree.addItem(root);
		verticalDirectoryPanel.add(tree);
	}

	public void checkAllChild(TreeItem root, Long orgId) {

	}


	/**
	 * Executes the action
	 */
	public void executeAction() {
		StringBuilder orgs = new StringBuilder();
		for (int i = 0; i < orgCheckeds.length(); ++i) {
			if (orgCheckeds.get(i) != -1)
				orgs.append(",").append(orgCheckeds.get(i));
		}
		orgs.deleteCharAt(0);
		Main.get().mainPanel.desktop.browser.tabMultiple.tabDocument.document.transmit(orgs.toString());
	}

	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("categories.folder.select.label"));
		cancelButton.setHTML(OkmConstants.ICON_NO_BUTTON + Main.i18n("button.close"));
		actionButton.setHTML(OkmConstants.ICON_ADD_BUTTON + Main.i18n("button.add"));
	}

	/**
	 * Shows the popup
	 */
	public void show() {
		initButtons();
		int left = (Window.getClientWidth() - 500) / 2;
		int top = (Window.getClientHeight() - 300) / 2;
		setPopupPosition(left, top);
		setText("Add organization");

		// Resets to initial tree value
		folderSelectTree.reset();
		super.show();
	}

	/**
	 * Enables or disables move button
	 *
	 * @param enable
	 */
	public void enable(boolean enable) {
		actionButton.setEnabled(enable);
	}

	/**
	 * Enables all button
	 */
	private void initButtons() {
		cancelButton.setEnabled(true);
		actionButton.setEnabled(false);
	}

	public void setOrgCheckeds(JsArrayNumber orgCheckeds, JsArrayString orgPathTrace) {
		this.orgCheckeds = orgCheckeds;
		this.orgPathTrace = orgPathTrace;
		drawOrgTree();
	}
}
