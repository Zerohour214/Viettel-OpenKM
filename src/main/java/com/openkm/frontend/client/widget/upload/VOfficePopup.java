package com.openkm.frontend.client.widget.upload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.openkm.extension.frontend.client.util.OkmConstants;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTVOfficeDocument;
import com.openkm.frontend.client.constants.service.ErrorCode;
import com.openkm.frontend.client.service.VOfficeService;
import com.openkm.frontend.client.service.VOfficeServiceAsync;

import java.util.ArrayList;
import java.util.List;

public class VOfficePopup extends DialogBox {
	private final VOfficeServiceAsync vOfficeService = GWT.create(VOfficeService.class);

	private TextBox vOfficeDocumentCode;
	private PasswordTextBox passVoffice;
	private Label passLabel;
	private Label docCodeLabel;
	private Button searchButton;
	private Button closeButton;
	private Button addButton;
	FlexTable vtTable;
	FlexTable vtHeader;
	List<GWTVOfficeDocument> gwtvOfficeDocuments;
	private VerticalPanel vPanel;
	private HorizontalPanel hTopPanel;
	private HorizontalPanel hPassPanel;
	private HorizontalPanel vButtonPanel;
	private VerticalPanel vDoccodePanel;
	private FlexTable propertiesTable;
	private int popupWidth = 615;
	private int popupHeight = 125;

	/**
	 * File upload
	 */
	public VOfficePopup() {
		super(false, false); // Modal = true indicates popup is centered
		propertiesTable = new FlexTable();
		vPanel = new VerticalPanel();
		hPassPanel = new HorizontalPanel();
		vDoccodePanel = new VerticalPanel();
		hTopPanel = new HorizontalPanel();
		vButtonPanel = new HorizontalPanel();
		vtHeader = new FlexTable();
		gwtvOfficeDocuments = new ArrayList<>();

		closeButton = new Button(OkmConstants.ICON_NO_BUTTON + Main.i18n("button.close"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				executeClose();
			}
		}
		);
		addButton = new Button(OkmConstants.ICON_ADD_BUTTON + Main.i18n("button.add"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addButton.setVisible(false); // Add new file button must be unvisible after clicking
				searchButton.setVisible(true);
				executeUploadFromVOffice();
			}
		}
		);
		addButton.setVisible(true);

		searchButton = new Button(OkmConstants.ICON_SEARCH_BUTTON + Main.i18n("button.search"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				executeSearch();
			}
		});

		// Set up a click listener on the proceed check box
//		searchButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				executeSearch();
//			}
//		});

		vOfficeDocumentCode = new TextBox();
		docCodeLabel = new Label(Main.i18n("document.code"));
		passLabel = new Label("Password Voffice");
		passVoffice = new PasswordTextBox();
		vtTable = new FlexTable();
		vtTable.addStyleName("okm-tableProperties-renew");
		vtHeader = new FlexTable();
		setTableHeader();

		vPanel.setWidth("615px");
		vPanel.setHeight("100px");

		propertiesTable.setWidget(0,0, docCodeLabel);
		propertiesTable.setWidget(0,1, vOfficeDocumentCode);
		propertiesTable.setWidget(0,2,searchButton);
		propertiesTable.setWidget(1,0, passLabel);
		propertiesTable.setWidget(1,1, passVoffice);
		propertiesTable.getColumnFormatter().setWidth(1, "250px");
//		vDoccodePanel.add(hTopPanel);
//		vDoccodePanel.add(hPassPanel);
//		vtHeader.add(vOfficeDocumentCode);
//		vtHeader.add(searchButton);
//		vtHeader.add(passLabel);
//		vtHeader.add(passVoffice);
//		vTopPanel.add(vtHeader);

//		vTopPanel.setCellHorizontalAlignment(searchButton, HorizontalPanel.ALIGN_CENTER);
//		vTopPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		vPanel.add(propertiesTable);
		vPanel.setCellHorizontalAlignment(propertiesTable, HorizontalPanel.ALIGN_LEFT);
		vPanel.add(new HTML("<br/>"));

		vPanel.add(vtTable);
		vPanel.add(new HTML("<br/>"));

		vButtonPanel.add(closeButton);
		vButtonPanel.add(new HTML("&nbsp;&nbsp;"));
		vButtonPanel.add(addButton);

		vPanel.add(vButtonPanel);
		vPanel.add(new HTML("<br/>"));

		vPanel.setCellHorizontalAlignment(vButtonPanel, VerticalPanel.ALIGN_CENTER);

		closeButton.setStyleName("okm-NoButton");
		closeButton.addStyleName("btn");
		closeButton.addStyleName("btn-warning");
		addButton.setStyleName("okm-AddButton");
		addButton.addStyleName("btn");
		addButton.addStyleName("btn-success");
		searchButton.setStyleName("okm-NoButton");
		searchButton.addStyleName("btn");
		searchButton.addStyleName("btn-success");
		vOfficeDocumentCode.setStyleName("okm-Input");
		vOfficeDocumentCode.setWidth("240px");
		passVoffice.setStyleName("okm-Input");
		passVoffice.setWidth("240px");
		loginSSO();
		setWidget(vPanel);
	}

	protected void loginSSO(){
		vOfficeService.loginSSO(new AsyncCallback() {
			@Override
			public void onFailure(Throwable throwable) {

			}

			@Override
			public void onSuccess(Object o) {

			}
		});
	}

	/**
	 * executeClose
	 */
	protected void executeClose() {
		setPopupPosition(-850, 0);
		setModal(false);

//		addButton.setVisible(false);
//		searchButton.setVisible(false);
	}

	protected void executeUploadFromVOffice() {
		if (gwtvOfficeDocuments.isEmpty()) {
			return;
		}
		int row = vtTable.getRowCount();
		List<GWTVOfficeDocument> newVOfficeDocuments = new ArrayList<>();
		for (int i = 1; i < row; i++) {
			CheckBox checkBox = (CheckBox) vtTable.getWidget(i, 0);
			if (checkBox.isChecked()) {
				newVOfficeDocuments.add(gwtvOfficeDocuments.get(i - 1));
			}
		}
		uploadFromVOffice(newVOfficeDocuments, passVoffice.getValue());
	}

	/**
	 * executeSearch
	 */
	protected void executeSearch() {
		if (Main.get().mainPanel.bottomPanel.userInfo.isQuotaExceed()) {
			Main.get().showError("UserQuotaExceed", new OKMException("OKM-" + ErrorCode.ORIGIN_OKMBrowser + ErrorCode.CAUSE_QuotaExceed, ""));
		} else if ( passVoffice.getValue() == null || passVoffice.getValue().isEmpty()) {
			Window.alert("Vui lòng nhập mật khẩu Voffice");
		} else {
			getVOfficeDocuments(vOfficeDocumentCode.getText(), passVoffice.getValue());
		}
	}

	/**
	 * Language refresh
	 */
	public void langRefresh() {
		closeButton.setHTML(OkmConstants.ICON_NO_BUTTON + Main.i18n("button.close"));
		addButton.setHTML(OkmConstants.ICON_ADD_BUTTON + Main.i18n("button.add"));
		searchButton.setHTML(OkmConstants.ICON_SEARCH_BUTTON + Main.i18n("button.search"));
		setTableHeader();
	}

	/**
	 * Show file upload popup
	 */
	public void showPopup() {
		setWidth("" + popupWidth);
		setHeight("" + popupHeight);
		center();

		vOfficeDocumentCode.setText("");
		resetTable();

		// Allways must initilize htmlForm for tree path initialization
		langRefresh();
		setModal(true);
	}

	/**
	 * Hide file upload
	 */
	@Override
	public void hide() {
		super.hide();
	}

	/**
	 * Adds a document to the panel
	 *
	 * @param doc The doc to add
	 */
	private void addRow(GWTVOfficeDocument doc) {
		int row = vtTable.getRowCount();
		vtTable.setWidget(row, 0, new CheckBox());
		vtTable.setHTML(row, 1, doc.getTitle());
		vtTable.setHTML(row, 2, doc.getCode());
		vtTable.setHTML(row, 3, doc.getName());
	}

	private void resetTable() {
		vtTable.removeAllRows();
		setTableHeader();
	}

	private void setTableHeader() {
		if (!gwtvOfficeDocuments.isEmpty()) {
			vtTable.setHTML(0, 1, Main.i18n("document.docName"));
			vtTable.setHTML(0, 2, Main.i18n("document.code"));
			vtTable.setHTML(0, 3, Main.i18n("merge.pdf.filename"));
		}
	}

	public void getVOfficeDocuments(String code, String password) {
		vOfficeService.getVOfficeDocuments(code, password, new AsyncCallback<List<GWTVOfficeDocument>>() {
			@Override
			public void onSuccess(List<GWTVOfficeDocument> result) {
				gwtvOfficeDocuments = result;
				resetTable();
				for (GWTVOfficeDocument doc : gwtvOfficeDocuments) {
					addRow(doc);
				}
			}

			public void onFailure(Throwable caught) {
				Main.get().showError("getVOfficeDocuments", caught);
			}
		});
	}

	public void uploadFromVOffice(List<GWTVOfficeDocument> newVOfficeDocuments, String password) {
		if (password == null || password.isEmpty()){
			Window.alert("Vui lòng nhập mật khẩu Voffice");
		} else {
			vOfficeService.getFileFromVOffice(Main.get().activeFolderTree.getActualPath(), newVOfficeDocuments, password, new AsyncCallback() {
				@Override
				public void onSuccess(Object o) {
					addButton.setVisible(true);
					Main.get().mainPanel.desktop.browser.fileBrowser.refresh(Main.get().activeFolderTree.getActualPath());
				}

				public void onFailure(Throwable caught) {
					Main.get().showError("getFileFromVOffice", caught);
					addButton.setVisible(true);
				}
			});
		}
	}

}
