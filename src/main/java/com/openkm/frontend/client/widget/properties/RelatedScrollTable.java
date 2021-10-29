package com.openkm.frontend.client.widget.properties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gen2.table.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.*;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.service.OKMSearchService;
import com.openkm.frontend.client.service.OKMSearchServiceAsync;
import com.openkm.frontend.client.util.CommonUI;
import com.openkm.frontend.client.util.ScrollTableHelper;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.ConfirmPopup;
import com.openkm.frontend.client.widget.properties.version.ExtendedColumnSorter;
import com.openkm.frontend.client.widget.searchresult.Status;

import java.util.*;

public class RelatedScrollTable extends Composite implements ClickHandler {
	private final OKMSearchServiceAsync searchService = (OKMSearchServiceAsync) GWT.create(OKMSearchService.class);

	// Number of columns
	public static final int NUMBER_OF_COLUMNS = 7;

	private GWTDocument doc;
	private ScrollTable table;
	private FixedWidthFlexTable headerTable;
	private FixedWidthGrid dataTable;
	private ExtendedColumnSorter columnSorter;
	private List<GWTDocument> gWTDocuments = new ArrayList<>();

	/**
	 * Version
	 */
	public RelatedScrollTable() {
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
		ScrollTableHelper.setColumnWidth(table, 2, 90, ScrollTableHelper.MEDIUM);
		ScrollTableHelper.setColumnWidth(table, 3, 90, ScrollTableHelper.MEDIUM);
		ScrollTableHelper.setColumnWidth(table, 4, 90, ScrollTableHelper.FIXED);
		ScrollTableHelper.setColumnWidth(table, 5, 90, ScrollTableHelper.FIXED);
		ScrollTableHelper.setColumnWidth(table, 6, 90, ScrollTableHelper.MEDIUM);

		table.setColumnSortable(4, false);
		table.setColumnSortable(5, false);

		// Level 1 headers
		headerTable.setHTML(0, 0, Main.i18n("document.name"));
		headerTable.setHTML(0, 1, Main.i18n("document.size"));
		headerTable.setHTML(0, 2, Main.i18n("filebrowser.date.update"));
		headerTable.setHTML(0, 3, Main.i18n("filebrowser.author"));
		headerTable.setHTML(0, 4, Main.i18n("filebrowser.version"));
		headerTable.setHTML(0, 5, Main.i18n("document.code"));
		headerTable.setHTML(0, 6, Main.i18n("document.docName"));

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
		headerTable.setHTML(0, 0, Main.i18n("document.name"));
		headerTable.setHTML(0, 1, Main.i18n("document.size"));
		headerTable.setHTML(0, 2, Main.i18n("filebrowser.date.update"));
		headerTable.setHTML(0, 3, Main.i18n("filebrowser.author"));
		headerTable.setHTML(0, 4, Main.i18n("filebrowser.version"));
		headerTable.setHTML(0, 5, Main.i18n("document.code"));
		headerTable.setHTML(0, 6, Main.i18n("document.docName"));
	}

	/**
	 * Sets the document
	 *
	 * @param GWTDocument The document
	 */
	public void set(GWTDocument doc) {
		this.doc = doc;
	}

	/**
	 * Removes all rows except the first
	 */
	public void reset() {
		// Purge all rows except first
		while (dataTable.getRowCount() > 0) {
			dataTable.removeRow(0);
		}
		dataTable.resize(0, NUMBER_OF_COLUMNS);
	}

	public void addRow(GWTDocument doc) {
		Anchor anchor = new Anchor();
		anchor.setHTML(doc.getName());
		anchor.setStyleName("okm-Hyperlink");
		anchor.setTitle(doc.getName());

		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CommonUI.openPath(Util.getParent(doc.getPath()), doc.getPath());
			}
		});

		final int rows = dataTable.getRowCount();
		dataTable.insertRow(rows);
		dataTable.setWidget(rows, 0, anchor);
		dataTable.setHTML(rows, 1, Util.formatSize(doc.getActualVersion().getSize()));
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		dataTable.setHTML(rows, 2, dtf.format(doc.getLastModified()));
		dataTable.setHTML(rows, 3, doc.getAuthor());
		dataTable.setHTML(rows, 4, doc.getActualVersion().getName());
		dataTable.setHTML(rows, 5, doc.getDocCode());
		dataTable.setHTML(rows, 6, doc.getDocName());

	}

	public void getRelatedDoc() {
		if (doc != null) {
			if (doc.getDocCode() != null && !doc.getDocCode().isEmpty()) {
				GWTQueryParams params = new GWTQueryParams();
				params.setDocCode(doc.getDocCode());
				params.setKeywords("");
				params.setPath("/okm:root");
				params.setMimeType("");
				params.setContent("");
				params.setDomain(1);
				findPaginated(params, 0, 100);
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

	/*
	 * (non-Javadoc)
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent event) {
		Main.get().confirmPopup.setConfirm(ConfirmPopup.CONFIRM_PURGE_VERSION_HISTORY_DOCUMENT);
		Main.get().confirmPopup.show();
	}

	/**
	 * fillWidth
	 */
	public void fillWidth() {
		table.fillWidth();
	}

	public void findPaginated(GWTQueryParams params, int offset, int limit) {
		searchService.findPaginated(params, offset, limit, callbackFindPaginated);
	}

	/**
	 * Call Back find paginated
	 */
	final AsyncCallback<GWTResultSet> callbackFindPaginated = new AsyncCallback<GWTResultSet>() {
		public void onSuccess(GWTResultSet result) {
			gWTDocuments = new ArrayList<>();
			Collection<GWTQueryResult> results = result.getResults();
			for(GWTQueryResult gWTQueryResult : results) {
				if (gWTQueryResult.getDocument() != null &&
						!gWTQueryResult.getDocument().getUuid().equals(doc.getUuid()) &&
						gWTQueryResult.getDocument().getDocCode().equals(doc.getDocCode())) {
					gWTDocuments.add(gWTQueryResult.getDocument());
				}
			}
			drawResults();
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("FindPaginated", caught);
		}
	};

	private void drawResults() {
		reset();
		for (GWTDocument gWTDocument : gWTDocuments) {
			addRow(gWTDocument);
		}
	}
}
