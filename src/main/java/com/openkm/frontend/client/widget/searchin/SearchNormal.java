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

package com.openkm.frontend.client.widget.searchin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTOrganization;
import com.openkm.frontend.client.bean.GWTUser;
import com.openkm.frontend.client.constants.ui.UIDesktopConstants;
import com.openkm.frontend.client.service.OKMAuthService;
import com.openkm.frontend.client.service.OKMAuthServiceAsync;
import com.openkm.frontend.client.service.VTOrgService;
import com.openkm.frontend.client.service.VTOrgServiceAsync;
import com.openkm.frontend.client.util.OKMBundleResources;

import java.util.Date;
import java.util.List;

/**
 * SearchNormal
 *
 * @author jllort
 */
public class SearchNormal extends Composite {
	private final OKMAuthServiceAsync authService = GWT.create(OKMAuthService.class);
	private final VTOrgServiceAsync orgService = GWT.create(VTOrgService.class);

	private static final int CALENDAR_FIRED_NONE = -1;
	private static final int CALENDAR_FIRED_START = 0;
	private static final int CALENDAR_FIRED_END = 1;

	private ScrollPanel scrollPanel;
	private FlexTable table;
	public ListBox context;
	public TextBox content;
	public TextBox name;
	public TextBox docCode;
	public TextBox docName;
	public DateBox docEffectDate;
	public ListBox orgListBox;
	public ListBox docConfidentialityListBox;
	public DateBox docExpiredDate;
	public TextBox docAuthor;
	public TextBox publishCom;
	public TextBox publishedYear;
	public TextBox pageNumber;
	public TextBox keywords;
	public ListBox userListBox;
	public TextBox startDate;
	public TextBox endDate;
	public HorizontalPanel dateRange;
	public PopupPanel calendarPopup;
	public CalendarWidget calendar;
	public HTML startCalendarIcon;
	public HTML endCalendarIcon;
	public HTML cleanIcon;
	public HTML dateBetween;
	public int calendarFired = CALENDAR_FIRED_NONE;
	public Date modifyDateFrom;
	public Date modifyDateTo;
	public int posTaxonomy = 0;
	public int posTemplates = 0;
	public int posPersonal = 0;
	public int posMail = 0;
	public int posTrash = 0;
	private boolean templatesVisible = false;
	private boolean personalVisible = false;
	private boolean mailVisible = false;
	private boolean trashVisible = false;
	private String trashContextValue = "";
	private String personalContextValue = "";
	private String mailContextValue = "";
	private String templatesContextValue = "";

	/**
	 * SearchNormal
	 */
	public SearchNormal() {
		table = new FlexTable();
		scrollPanel = new ScrollPanel(table);

		context = new ListBox();
		context.setStyleName("okm-Select");
		int count = 0;
		posTaxonomy = count++;
		context.addItem(Main.i18n("leftpanel.label.taxonomy"), "");

		if (templatesVisible) {
			posTemplates = count++;
			context.addItem(Main.i18n("leftpanel.label.templates"), "");
		}

		if (personalVisible) {
			posPersonal = count++;
			context.addItem(Main.i18n("leftpanel.label.my.documents"), "");
		}

		if (mailVisible) {
			posMail = count++;
			context.addItem(Main.i18n("leftpanel.label.mail"), "");
		}

		if (trashVisible) {
			posTrash = count++;
			context.addItem(Main.i18n("leftpanel.label.trash"), "");
		}

		context.setSelectedIndex(posTaxonomy);

		context.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// each time list is changed must clean folder
				Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced.path.setText("");

				// Always enable mail search in mail view
				if (mailVisible && context.getSelectedIndex() == posMail) {
					Main.get().mainPanel.search.searchBrowser.searchIn.searchAdvanced.enableMailSearch();
				}
			}
		});

		content = new TextBox();
		name = new TextBox();
		docCode = new TextBox();
		docName = new TextBox();
		docEffectDate = new DateBox();
		orgListBox = new ListBox();
		docConfidentialityListBox = new ListBox();
		docExpiredDate = new DateBox();
		docAuthor = new TextBox();
		publishCom = new TextBox();
		publishedYear = new TextBox();
		pageNumber = new TextBox();
		keywords = new TextBox();
		userListBox = new ListBox();
		startDate = new TextBox();
		endDate = new TextBox();
		dateRange = new HorizontalPanel();
		calendar = new CalendarWidget();
		calendarPopup = new PopupPanel(true);
		startCalendarIcon = new HTML("<span class=\"glyphicons glyphicons-calendar icon-green-renew\" style=\"padding-left: 7px;\"></span>");
		endCalendarIcon = new HTML("<span class=\"glyphicons glyphicons-calendar icon-green-renew\" style=\"padding-left: 7px;\"></span>");
		cleanIcon = new HTML("<span class=\"glyphicons glyphicons-remove-sign icon-red-renew\" style=\"padding-left: 7px;\"></span>");
		dateBetween = new HTML("<span class=\"glyphicons glyphicons-arrow-right icon-black-renew\" style=\"padding: 6px 3px 0 6px;\"></span>");

		userListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.evaluateSearchButtonVisible();
			}
		});

		orgListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.evaluateSearchButtonVisible();
			}
		});
		docConfidentialityListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.evaluateSearchButtonVisible();
			}
		});

		// Calendar widget
		calendarPopup.setWidget(calendar);

		calendar.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				calendarPopup.hide();
				DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.day.pattern"));

				switch (calendarFired) {
					case CALENDAR_FIRED_START:
						startDate.setText(dtf.format(calendar.getDate()));
						modifyDateFrom = (Date) calendar.getDate().clone();
						break;

					case CALENDAR_FIRED_END:
						endDate.setText(dtf.format(calendar.getDate()));
						modifyDateTo = (Date) calendar.getDate().clone();
						break;
				}

				calendarFired = CALENDAR_FIRED_NONE;
				Main.get().mainPanel.search.searchBrowser.searchIn.searchControl.evaluateSearchButtonVisible();
			}
		});

		startCalendarIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				calendarFired = CALENDAR_FIRED_START;
				if (modifyDateFrom != null) {
					calendar.setNow((Date) modifyDateFrom.clone());
				} else {
					calendar.setNow(null);
				}
				calendarPopup.setPopupPosition(startCalendarIcon.getAbsoluteLeft(), startCalendarIcon.getAbsoluteTop() - 2);
				calendarPopup.show();
			}
		});
		startCalendarIcon.setStyleName("okm-Hyperlink");

		endCalendarIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				calendarFired = CALENDAR_FIRED_END;
				if (modifyDateTo != null) {
					calendar.setNow((Date) modifyDateTo.clone());
				} else {
					calendar.setNow(null);
				}
				calendarPopup.setPopupPosition(endCalendarIcon.getAbsoluteLeft(), endCalendarIcon.getAbsoluteTop() - 2);
				calendarPopup.show();
			}
		});
		endCalendarIcon.setStyleName("okm-Hyperlink");

		cleanIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				startDate.setText("");
				modifyDateFrom = null;
				endDate.setText("");
				modifyDateTo = null;
			}
		});
		cleanIcon.setStyleName("okm-Hyperlink");

		// Date range panel
		dateRange.add(startDate);
		dateRange.add(startCalendarIcon);
		dateRange.add(dateBetween);
		dateRange.add(endDate);
		dateRange.add(endCalendarIcon);
		dateRange.add(cleanIcon);
		startDate.setWidth("127px");
		endDate.setWidth("127px");
		startDate.setMaxLength(10);
		endDate.setMaxLength(10);
		startDate.setReadOnly(true);
		endDate.setReadOnly(true);
		dateRange.setCellVerticalAlignment(startCalendarIcon, HasAlignment.ALIGN_MIDDLE);
		dateRange.setCellVerticalAlignment(endCalendarIcon, HasAlignment.ALIGN_MIDDLE);
		dateRange.setCellVerticalAlignment(cleanIcon, HasAlignment.ALIGN_MIDDLE);
		dateRange.setCellWidth(cleanIcon, "25px");
		dateRange.setCellHorizontalAlignment(cleanIcon, HasAlignment.ALIGN_RIGHT);
		dateBetween.addStyleName("okm-NoWrap");

		table.setHTML(0, 0, Main.i18n("search.context"));
		table.setWidget(0, 1, context);
		table.setHTML(1, 0, Main.i18n("search.content"));
		table.setWidget(1, 1, content);
		table.setHTML(2, 0, Main.i18n("search.name"));
		table.setWidget(2, 1, name);
		table.setHTML(3, 0, Main.i18n("document.code"));
		table.setWidget(3, 1, docCode);
		table.setHTML(4, 0, Main.i18n("document.docName"));
		table.setWidget(4, 1, docName);
		table.setHTML(5, 0, Main.i18n("document.effectiveDate"));
		table.setWidget(5, 1, docEffectDate);
		table.setHTML(6, 0, Main.i18n("document.org"));
		table.setWidget(6, 1, orgListBox);
		table.setHTML(7, 0, Main.i18n("document.confidentiality.level"));
		table.setWidget(7, 1, docConfidentialityListBox);
		table.setHTML(8, 0, Main.i18n("document.docExpiredDate"));
		table.setWidget(8, 1, docExpiredDate);
		table.setHTML(9, 0, Main.i18n("document.docAuthor"));
		table.setWidget(9, 1, docAuthor);
		table.setHTML(10, 0, Main.i18n("document.publishCom"));
		table.setWidget(10, 1, publishCom);
		table.setHTML(11, 0, Main.i18n("document.publishedYear"));
		table.setWidget(11, 1, publishedYear);
		table.setHTML(12, 0, Main.i18n("document.pageNumber"));
		table.setWidget(12, 1, pageNumber);
		table.setHTML(13, 0, Main.i18n("search.keywords"));
		table.setWidget(13, 1, keywords);
		table.setHTML(14, 0, Main.i18n("search.user"));
		table.setWidget(14, 1, userListBox);
		table.setHTML(15, 0, Main.i18n("search.date.range"));
		table.setWidget(15, 1, dateRange);

		CellFormatter cellFormatter = table.getCellFormatter();
		cellFormatter.setStyleName(0, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(1, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(2, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(3, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(4, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(5, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(6, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(7, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(8, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(9, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(10, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(11, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(12, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(13, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(14, 0, "okm-DisableSelect");
		cellFormatter.setStyleName(15, 0, "okm-DisableSelect");

		// Sets wordWrap for al rows
		setRowWordWarp(table, 0, 1, false);
		setRowWordWarp(table, 1, 1, false);
		setRowWordWarp(table, 2, 1, false);
		setRowWordWarp(table, 3, 1, false);
		setRowWordWarp(table, 4, 1, false);
		setRowWordWarp(table, 5, 1, false);
		setRowWordWarp(table, 6, 1, false);
		setRowWordWarp(table, 7, 1, false);
		setRowWordWarp(table, 8, 1, false);
		setRowWordWarp(table, 9, 1, false);
		setRowWordWarp(table, 10, 1, false);
		setRowWordWarp(table, 11, 1, false);
		setRowWordWarp(table, 12, 1, false);
		setRowWordWarp(table, 13, 1, false);
		setRowWordWarp(table, 14, 1, false);
		setRowWordWarp(table, 15, 1, false);

		table.setStyleName("okm-searchNormal-table-renew");
		context.addStyleName("okm-Input");
		context.addStyleName("okm-searchNormal-select-renew");
		content.setStyleName("okm-Input");
		content.addStyleName("okm-searchNormal-input-renew");
		name.setStyleName("okm-Input");
		docCode.setStyleName("okm-Input");
		docName.setStyleName("okm-Input");
		docEffectDate.setStyleName("okm-Input");
		orgListBox.setStyleName("okm-Input");
		docConfidentialityListBox.setStyleName("okm-Input");
		docExpiredDate.setStyleName("okm-Input");
		docAuthor.setStyleName("okm-Input");
		publishCom.setStyleName("okm-Input");
		publishedYear.setStyleName("okm-Input");
		pageNumber.setStyleName("okm-Input");
		keywords.setStyleName("okm-Input");
		keywords.addStyleName("okm-searchNormal-input-renew");
		userListBox.setStyleName("okm-Input");
		userListBox.addStyleName("okm-searchNormal-select-renew");
		startDate.setStyleName("okm-Input");
		endDate.setStyleName("okm-Input");

		initWidget(scrollPanel);
	}

	/**
	 * Set the WordWarp for all the row cells
	 *
	 * @param row     The row cell
	 * @param columns Number of row columns
	 */
	private void setRowWordWarp(FlexTable table, int row, int columns, boolean wrap) {
		CellFormatter cellFormatter = table.getCellFormatter();

		for (int i = 0; i < columns; i++) {
			cellFormatter.setWordWrap(row, i, wrap);
		}
	}

	/**
	 * Gets all users
	 */
	public void getAllUsers() {
		authService.getAllUsers(new AsyncCallback<List<GWTUser>>() {
			public void onSuccess(List<GWTUser> result) {
				userListBox.addItem("", ""); // Add first value empty
				for (GWTUser user : result) {
					userListBox.addItem(user.getUsername(), user.getId());
				}
			}

			public void onFailure(Throwable caught) {
				Main.get().showError("GetAllUsers", caught);
			}
		});
	}

	public void getAllOrg() {
		orgService.getAllOrg(new AsyncCallback<List<GWTOrganization>>() {
			@Override
			public void onSuccess(List<GWTOrganization> result) {
				orgListBox.addItem("", ""); // Add first value empty
				for (GWTOrganization org : result) {
					orgListBox.addItem(org.getName(), String.valueOf(org.getId()));
				}
			}

			public void onFailure(Throwable caught) {
				Main.get().showError("getAllOrg", caught);
			}
		});
	}

	public void getConfidentialityLevels() {
		docConfidentialityListBox.addItem("", "");
		docConfidentialityListBox.addItem(Main.i18n("document.confidentiality.normal"), "0");
		docConfidentialityListBox.addItem(Main.i18n("document.confidentiality.high"), "1");
	}

	/**
	 * langRefresh
	 */
	public void langRefresh() {
		table.setHTML(0, 0, Main.i18n("search.context"));
		table.setHTML(1, 0, Main.i18n("search.content"));
		table.setHTML(2, 0, Main.i18n("search.name"));
		table.setHTML(3, 0, Main.i18n("document.code"));
		table.setHTML(4, 0, Main.i18n("document.docName"));
		table.setHTML(5, 0, Main.i18n("document.effectiveDate"));
		table.setHTML(6, 0, Main.i18n("document.org"));
		table.setHTML(7, 0, Main.i18n("document.confidentiality.level"));
		table.setHTML(8, 0, Main.i18n("document.docExpiredDate"));
		table.setHTML(9, 0, Main.i18n("document.docAuthor"));
		table.setHTML(10, 0, Main.i18n("document.publishCom"));
		table.setHTML(11, 0, Main.i18n("document.publishedYear"));
		table.setHTML(12, 0, Main.i18n("document.pageNumber"));
		table.setHTML(13, 0, Main.i18n("search.keywords"));
		table.setHTML(14, 0, Main.i18n("search.user"));
		table.setHTML(15, 0, Main.i18n("search.date.range"));

		context.setItemText(posTaxonomy, Main.i18n("leftpanel.label.taxonomy"));

		if (templatesVisible) {
			context.setItemText(posTemplates, Main.i18n("leftpanel.label.templates"));
		}

		if (personalVisible) {
			context.setItemText(posPersonal, Main.i18n("leftpanel.label.my.documents"));
		}

		if (mailVisible) {
			context.setItemText(posMail, Main.i18n("leftpanel.label.mail"));
		}

		if (trashVisible) {
			context.setItemText(posTrash, Main.i18n("leftpanel.label.trash"));
		}

		calendar.langRefresh();
	}

	/**
	 * Sets the context values
	 *
	 * @param contextValue The context value
	 * @param stackView    The stack view
	 */
	public void setContextValue(String contextValue, int stackView) {
		switch (stackView) {
			case UIDesktopConstants.NAVIGATOR_TAXONOMY:
				context.setValue(posTaxonomy, contextValue);
				break;

			case UIDesktopConstants.NAVIGATOR_TEMPLATES:
				templatesContextValue = contextValue;

				if (templatesVisible) {
					posTemplates = context.getItemCount(); // Item count by default is good id, 0 is first item, etc...
					context.addItem(Main.i18n("leftpanel.label.templates"), templatesContextValue);
				}
				break;

			case UIDesktopConstants.NAVIGATOR_PERSONAL:
				personalContextValue = contextValue;

				if (personalVisible) {
					posPersonal = context.getItemCount();
					context.addItem(Main.i18n("leftpanel.label.my.documents"), personalContextValue);
				}
				break;

			case UIDesktopConstants.NAVIGATOR_MAIL:
				mailContextValue = contextValue;

				if (mailVisible) {
					posMail = context.getItemCount();
					context.addItem(Main.i18n("leftpanel.label.mail"), mailContextValue);
				}
				break;

			case UIDesktopConstants.NAVIGATOR_TRASH:
				trashContextValue = contextValue;

				if (trashVisible) {
					posTrash = context.getItemCount();
					context.addItem(Main.i18n("leftpanel.label.trash"), trashContextValue);
				}
				break;
		}
	}

	/**
	 * showTemplates
	 */
	public void showTemplates() {
		templatesVisible = true;
	}

	/**
	 * showPersonal
	 */
	public void showPersonal() {
		personalVisible = true;
	}

	/**
	 * showMail
	 */
	public void showMail() {
		mailVisible = true;
	}

	/**
	 * showTrash
	 */
	public void showTrash() {
		trashVisible = true;
	}
}
