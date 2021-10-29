package com.openkm.frontend.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTOrganization;

import java.util.List;

public interface VTOrgServiceAsync {
	public void getAllOrg(AsyncCallback<List<GWTOrganization>> async);
}
