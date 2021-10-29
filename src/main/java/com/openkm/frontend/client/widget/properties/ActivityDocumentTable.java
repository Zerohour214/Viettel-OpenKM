package com.openkm.frontend.client.widget.properties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.openkm.extension.frontend.client.service.OKMActivityLogService;
import com.openkm.extension.frontend.client.service.OKMActivityLogServiceAsync;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTQueryParams;
import com.openkm.frontend.client.bean.GWTQueryResult;
import com.openkm.frontend.client.bean.GWTResultSet;
import com.openkm.frontend.client.bean.extension.GWTActivity;
import com.openkm.frontend.client.service.OKMSearchService;
import com.openkm.frontend.client.service.OKMSearchServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.ScrollTableHelper;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.properties.version.ExtendedColumnSorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ActivityDocumentTable extends Composite {
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);
	private final OKMActivityLogServiceAsync activityService = (OKMActivityLogServiceAsync) GWT.create(OKMActivityLogService.class);

	// Number of columns
	public static final int NUMBER_OF_COLUMNS = 4;

	private GWTDocument doc;
	private ScrollTable table;
	private FixedWidthFlexTable headerTable;
	private FixedWidthGrid dataTable;
	private ExtendedColumnSorter columnSorter;
	private List<GWTActivity> gwtActivities = new ArrayList<>();

	public ActivityDocumentTable(){
		AbstractScrollTable.ScrollTableImages scrollTableImages = new AbstractScrollTable.ScrollTableImages() {
			public AbstractImagePrototype scrollTableAscending() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/sort_asc.gif");
					}

					public Image createImage() {
						return new Image("img/sort_asc.gif");
					}

					public String getHTML() {
						return "<img border=\"0\" src=\"img/sort_asc.gif\"/>";
					}
				};
			}

			public AbstractImagePrototype scrollTableDescending() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/sort_desc.gif");
					}

					public Image createImage() {
						return new Image("img/sort_desc.gif");
					}

					public String getHTML() {
						return "<img border=\"0\" src=\"img/sort_desc.gif\"/>";
					}
				};
			}

			public AbstractImagePrototype scrollTableFillWidth() {
				return new AbstractImagePrototype() {
					public void applyTo(Image image) {
						image.setUrl("img/fill_width.gif");
					}

					public Image createImage() {
						return new Image("img/fill_width.gif");
					}

					public String getHTML() {
						return "<img border=\"0\" src=\"img/fill_width.gif\"/>";
					}
				};
			}
		};

		headerTable = new FixedWidthFlexTable();
		dataTable = new FixedWidthGrid();
		columnSorter = new ExtendedColumnSorter();
		table = new ScrollTable(dataTable, headerTable, scrollTableImages);
		dataTable.setColumnSorter(columnSorter);
		table.setCellSpacing(0);
		table.setCellPadding(2);
		table.setSize("540px", "140px");
		ScrollTableHelper.setColumnWidth(table, 0, 90, ScrollTableHelper.FIXED);
		ScrollTableHelper.setColumnWidth(table, 1, 90, ScrollTableHelper.MEDIUM);
		ScrollTableHelper.setColumnWidth(table, 2, 120, ScrollTableHelper.MEDIUM);
		ScrollTableHelper.setColumnWidth(table, 3, 120, ScrollTableHelper.MEDIUM);


		table.setColumnSortable(2, false);

		// Level 1 headers
		headerTable.setHTML(0, 0, Main.i18n("document.activity.user"));
		headerTable.setHTML(0, 1, Main.i18n("document.activity.action"));
		headerTable.setHTML(0, 2, Main.i18n("document.activity.item"));
		headerTable.setHTML(0, 3, Main.i18n("document.activity.date"));


		// Table data
		dataTable.setSelectionPolicy(SelectionGrid.SelectionPolicy.ONE_ROW);
		table.setResizePolicy(AbstractScrollTable.ResizePolicy.FILL_WIDTH);
		table.setScrollPolicy(AbstractScrollTable.ScrollPolicy.BOTH);

		headerTable.addStyleName("okm-DisableSelect");
		dataTable.addStyleName("okm-DisableSelect");

		initWidget(table);
	}

	/**
	 * Language refresh
	 */
	public void langRefresh() {
		headerTable.setHTML(0, 0, Main.i18n("document.activity.user"));
		headerTable.setHTML(0, 1, Main.i18n("document.activity.action"));
		headerTable.setHTML(0, 2, Main.i18n("document.activity.item"));
		headerTable.setHTML(0, 3, Main.i18n("document.activity.date"));
	}

	/**
	 * Sets the document
	 *
	 * @param GWTDocument The document
	 */
	public void set(GWTDocument doc) {
		this.doc = doc;
	}

	public void reset() {
		// Purge all rows except first
		while (dataTable.getRowCount() > 0) {
			dataTable.removeRow(0);
		}
		dataTable.resize(0, NUMBER_OF_COLUMNS);
	}

	public void addRow(GWTActivity act) {

		final int rows = dataTable.getRowCount();
		dataTable.insertRow(rows);
		dataTable.setHTML(rows, 0, act.getUser());
		dataTable.setHTML(rows, 1, getActionName(act.getAction()));
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		dataTable.setHTML(rows, 2, act.getItem());
		dataTable.setHTML(rows, 3, dtf.format(act.getDate()));
	}

	public void fillWidth() {
		table.fillWidth();
	}

	public void getActivity() {
		if (doc != null) {
			if (doc.getUuid() != null && !doc.getUuid().isEmpty()) {
				findByFilterByItem(doc.getUuid());
			}
			else {
				reset();
			}
		}
	}

	/**
	 * @return the data table
	 */
	public FixedWidthGrid getDataTable() {
		return dataTable;
	}

	public void findByFilterByItem(String item) {
		activityService.findByFilterByItem(item, "ALL_ACTIONS", false, callbackFindPaginated);
	}

	/**
	 * Call Back find paginated
	 */
	final AsyncCallback<List<GWTActivity>> callbackFindPaginated = new AsyncCallback<List<GWTActivity>>() {
		public void onSuccess(List<GWTActivity> result) {
			gwtActivities = new ArrayList<>();
			gwtActivities = result;
//			for(GWTActivity gwtActivity : result) {
//				gwtActivities.add(gwtActivity.getDocument());
//			}
			drawResults();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("FindPaginated", caught);
		}
	};

	private void drawResults() {
		reset();
		for (GWTActivity gwtActivity : gwtActivities) {
			addRow(gwtActivity);
		}
	}

	private String getActionName(String action){
		String result = action;
		switch (action){
			case "CANCEL_DOCUMENT_CHECKOUT":
				result = "Hủy checkout";
				break;
			case "CHECKIN_DOCUMENT":
				result = "Checkin";
				break;
			case "CHECKOUT_DOCUMENT":
				result = "Checkout";
				break;
			case "CREATE_DOCUMENT":
				result = "Tạo tài liệu";
				break;
			case "DELETE_DOCUMENT":
				result = "Xóa tài liệu";
				break;
			case "GET_CHILDREN_DOCUMENTS":
				result = "Lấy tài liệu con";
				break;
			case "GET_DOCUMENT_CONTENT":
				result = "Tải xuống";
				break;
			case "GET_DOCUMENT_CONTENT_BY_VERSION":
				result = "Tải xuống với version";
				break;
			case "GET_DOCUMENT_PROPERTIES":
				result = "Lấy thuộc tính tài liệu";
				break;
			case "GET_DOCUMENT_VERSION_HISTORY":
				result = "Lấy lịch sử version";
				break;
			case "GET_PROPERTY_GROUP_PROPERTIES":
				result = "Lấy nhóm thuộc tính";
				break;
			case "LOCK_DOCUMENT":
				result = "Khóa tài liệu";
				break;
			case "MOVE_DOCUMENT":
				result = "Chuyển tài liệu";
				break;
			case "PURGE_DOCUMENT":
				result = "Xóa sạch tài liệu";
				break;
			case "RENAME_DOCUMENT":
				result = "Đổi tên tài liệu";
				break;
			case "SET_DOCUMENT_PROPERTIES":
				result = "Set thuộc tính tài liệu";
				break;
			case "UNLOCK_DOCUMENT":
				result = "Mở khóa tài liệu";
				break;
			default:
				result = action;
		}

		return result;
	}
}
