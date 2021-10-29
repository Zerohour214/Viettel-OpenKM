package com.openkm.frontend.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.openkm.frontend.client.util.Util;

/**
 * Clipboard
 *
 * @author sochoa
 */
public class ClipboardIcon extends Composite implements ClickHandler {
	private HTML imgCopyDav;
	private String text;

	public ClipboardIcon() {
		// Show clipboard icon
		imgCopyDav = new HTML("<span class=\"glyphicons glyphicons-copy\"></span>");
		imgCopyDav.setStyleName("okm-Hyperlink");
		imgCopyDav.addClickHandler(this);

		// All composites must call initWidget() in their constructors.
		initWidget(imgCopyDav);
	}

	public ClipboardIcon(String text) {
		this.text = text;

		// Show clipboard icon
		imgCopyDav = new HTML("<span class=\"glyphicons glyphicons-copy\" style = \"font-size: 15px; color: #27B45F;\"></span>");
		imgCopyDav.setStyleName("okm-Hyperlink");
		imgCopyDav.setTitle(text);
		imgCopyDav.addClickHandler(this);

		// All composites must call initWidget() in their constructors.
		initWidget(imgCopyDav);
	}

	public void setText(String text) {
		this.text = text;
		imgCopyDav.setTitle(text);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (text != null && !text.isEmpty()) {
			Util.copyToClipboard(text);
		} else {
			Util.consoleLog("URL is nul or empty");
		}
	}
}
