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

package com.openkm.frontend.client.constants.ui;

/**
 * Defines contants values for the desktop panels
 *
 * @author jllort
 */
public class UIDesktopConstants {

	// Navigator stack panel definition
	public static final int NAVIGATOR_TAXONOMY = 0; // Number indicates ordering
	public static final int NAVIGATOR_CATEGORIES = 1;
	public static final int NAVIGATOR_METADATA = 2;
	public static final int NAVIGATOR_THESAURUS = 3;
	public static final int NAVIGATOR_TEMPLATES = 4;
	public static final int NAVIGATOR_PERSONAL = 5;
	public static final int NAVIGATOR_MAIL = 6;
	public static final int NAVIGATOR_TRASH = 7;
	public static final int NAVIGATOR_ALL_CONTEXT = 8; // Used to indicate
	// operation to all
	// context ( search )
	// really it's not a
	// panel
	public static final int NUMBER_OF_STACKS = 8;
	public static final int STACK_HEIGHT = 30;

	// File Browser columns
	public static final int FILEBROWSER_COLUMN_STATUS = 0;
	public static final int FILEBROWSER_COLUMN_MASSIVE = 1;
	public static final int FILEBROWSER_COLUMN_ICON = 2;
	public static final int FILEBROWSER_COLUMN_NAME = 3;
	public static final int FILEBROWSER_COLUMN_SIZE = 4;
	public static final int FILEBROWSER_COLUMN_LASTMODIFIED = 5;
	public static final int FILEBROWSER_COLUMN_AUTHOR = 6;
	public static final int FILEBROWSER_COLUMN_VERSION = 7;
	public static final int FILEBROWSER_COLUMN_DOC_CODE = 8;
	public static final int FILEBROWSER_COLUMN_DOC_NAME = 9;
	public static final int FILEBROWSER_COLUMN_DOC_EFFECTIVE_DATE = 10;
	public static final int FILEBROWSER_COLUMN_DOC_ORG = 11;
	public static final int FILEBROWSER_COLUMN_DOC_CONFIDENTIALITY = 12;
	public static final int FILEBROWSER_COLUMN_DOC_EXPIRED_DATE = 13;
	public static final int FILEBROWSER_COLUMN_DOC_AUTHOR = 14;
	public static final int FILEBROWSER_COLUMN_PUBLISH_COM= 15;
	public static final int FILEBROWSER_COLUMN_PUBLISHED_YEAR = 16;
	public static final int FILEBROWSER_COLUMN_PAGE_NUMBER = 17;
	public static final int FILEBROWSER_COLUMN_EXTRA0 = 18;
	public static final int FILEBROWSER_COLUMN_EXTRA1 = 19;
	public static final int FILEBROWSER_COLUMN_EXTRA2 = 20;
	public static final int FILEBROWSER_COLUMN_EXTRA3 = 21;
	public static final int FILEBROWSER_COLUMN_EXTRA4 = 22;
	public static final int FILEBROWSER_COLUMN_EXTRA5 = 23;
	public static final int FILEBROWSER_COLUMN_EXTRA6 = 24;
	public static final int FILEBROWSER_COLUMN_EXTRA7 = 25;
	public static final int FILEBROWSER_COLUMN_EXTRA8 = 26;
	public static final int FILEBROWSER_COLUMN_EXTRA9 = 27;

	// Search Browser columns
	public static final int SEARCHBROWSER_COLUMN_RELEVANCE = 0;
	public static final int SEARCHBROWSER_COLUMN_ICON = 1;
	public static final int SEARCHBROWSER_COLUMN_NAME = 2;
	public static final int SEARCHBROWSER_COLUMN_SIZE = 3;
	public static final int SEARCHBROWSER_COLUMN_LASTMODIFIED = 4;
	public static final int SEARCHBROWSER_COLUMN_AUTHOR = 5;
	public static final int SEARCHBROWSER_COLUMN_VERSION = 6;
	public static final int SEARCHBROWSER_COLUMN_DOC_CODE = 7;
	public static final int SEARCHBROWSER_COLUMN_DOC_NAME = 8;
	public static final int SEARCHBROWSER_COLUMN_DOC_EFFECTIVE_DATE = 9;
	public static final int SEARCHBROWSER_COLUMN_DOC_ORG = 10;
	public static final int SEARCHBROWSER_COLUMN_DOC_CONFIDENTIALITY = 11;
	public static final int SEARCHBROWSER_COLUMN_DOC_EXPIRED_DATE = 12;
	public static final int SEARCHBROWSER_COLUMN_DOC_AUTHOR = 13;
	public static final int SEARCHBROWSER_COLUMN_PUBLISH_COM = 14;
	public static final int SEARCHBROWSER_COLUMN_PUBLISHED_YEAR = 15;
	public static final int SEARCHBROWSER_COLUMN_PAGE_NUMBER = 16;
	public static final int SEARCHBROWSER_COLUMN_EXTRA0 = 17;
	public static final int SEARCHBROWSER_COLUMN_EXTRA1 = 18;
	public static final int SEARCHBROWSER_COLUMN_EXTRA2 = 19;
	public static final int SEARCHBROWSER_COLUMN_EXTRA3 = 20;
	public static final int SEARCHBROWSER_COLUMN_EXTRA4 = 21;
	public static final int SEARCHBROWSER_COLUMN_EXTRA5 = 22;
	public static final int SEARCHBROWSER_COLUMN_EXTRA6 = 23;
	public static final int SEARCHBROWSER_COLUMN_EXTRA7 = 24;
	public static final int SEARCHBROWSER_COLUMN_EXTRA8 = 25;
	public static final int SEARCHBROWSER_COLUMN_EXTRA9 = 26;

	// Document history
	public static final int DOCUMENT_HISTORY_COLUMN_VERSION = 0;
	public static final int DOCUMENT_HISTORY_COLUMN_DATE = 1;
	public static final int DOCUMENT_HISTORY_COLUMN_AUTHOR = 2;
	public static final int DOCUMENT_HISTORY_COLUMN_SIZE = 3;
	public static final int DOCUMENT_HISTORY_COLUMN_BUTTON_SHOW = 4;
	public static final int DOCUMENT_HISTORY_COLUMN_BUTTON_RESTORE = 5;
	public static final int DOCUMENT_HISTORY_COLUMN_COMMENT = 6;
}
