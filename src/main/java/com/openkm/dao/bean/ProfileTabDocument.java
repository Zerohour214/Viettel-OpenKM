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

package com.openkm.dao.bean;

import java.io.Serializable;

public class ProfileTabDocument implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean propertiesVisible;
	private boolean securityVisible;
	private boolean notesVisible;
	private boolean versionsVisible;
	private boolean versionDownloadVisible;
	private boolean previewVisible;
	private boolean relatedVisible;
	private boolean activityVisible;
	private boolean propertyGroupsVisible;
	private boolean transmitVisible;

	public boolean isPropertiesVisible() {
		return propertiesVisible;
	}

	public void setPropertiesVisible(boolean propertiesVisible) {
		this.propertiesVisible = propertiesVisible;
	}

	public boolean isSecurityVisible() {
		return securityVisible;
	}

	public void setSecurityVisible(boolean securityVisible) {
		this.securityVisible = securityVisible;
	}

	public boolean isNotesVisible() {
		return notesVisible;
	}

	public void setNotesVisible(boolean notesVisible) {
		this.notesVisible = notesVisible;
	}

	public boolean isVersionsVisible() {
		return versionsVisible;
	}

	public void setVersionsVisible(boolean versionsVisible) {
		this.versionsVisible = versionsVisible;
	}

	public boolean isVersionDownloadVisible() {
		return versionDownloadVisible;
	}

	public void setVersionDownloadVisible(boolean versionDownloadVisible) {
		this.versionDownloadVisible = versionDownloadVisible;
	}

	public boolean isPreviewVisible() {
		return previewVisible;
	}

	public void setPreviewVisible(boolean previewVisible) {
		this.previewVisible = previewVisible;
	}

	public boolean isRelatedVisible() {
		return relatedVisible;
	}

	public void setRelatedVisible(boolean relatedVisible) {
		this.relatedVisible = relatedVisible;
	}

	public boolean isPropertyGroupsVisible() {
		return propertyGroupsVisible;
	}

	public void setPropertyGroupsVisible(boolean propertyGroupsVisible) {
		this.propertyGroupsVisible = propertyGroupsVisible;
	}

	public boolean isActivityVisible() {
		return activityVisible;
	}

	public void setActivityVisible(boolean activityVisible) {
		this.activityVisible = activityVisible;
	}

	public boolean isTransmitVisible() {
		return transmitVisible;
	}

	public void setTransmitVisible(boolean transmitVisible) {
		this.transmitVisible = transmitVisible;
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("propertiesVisible=").append(propertiesVisible);
		sb.append(", securityVisible=").append(securityVisible);
		sb.append(", notesVisible=").append(notesVisible);
		sb.append(", versionsVisible=").append(versionsVisible);
		sb.append(", versionDownloadVisible=").append(versionDownloadVisible);
		sb.append(", previewVisible=").append(previewVisible);
		sb.append(", relatedVisible=").append(relatedVisible);
		sb.append(", activityVisible").append(activityVisible);
		sb.append(", propertyGroupsVisible=").append(propertyGroupsVisible);
		sb.append("}");
		return sb.toString();
	}
}
