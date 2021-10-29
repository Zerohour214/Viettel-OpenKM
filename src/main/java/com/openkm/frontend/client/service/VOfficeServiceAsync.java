package com.openkm.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.openkm.frontend.client.bean.GWTVOfficeDocument;

import java.util.List;

public interface VOfficeServiceAsync {
	public void getVOfficeDocuments(String code, String password, AsyncCallback<List<GWTVOfficeDocument>> async);
	public void getFileFromVOffice(String path, List<GWTVOfficeDocument> gwtvOfficeDocuments, String password, AsyncCallback async);
	public void loginSSO(AsyncCallback asyncCallback);
}
