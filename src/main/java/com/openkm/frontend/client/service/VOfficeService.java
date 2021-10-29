package com.openkm.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTVOfficeDocument;

import java.util.List;

@RemoteServiceRelativePath("VOffice")
public interface VOfficeService extends RemoteService {
	public List<GWTVOfficeDocument> getVOfficeDocuments(String code, String password) throws OKMException;
	public void getFileFromVOffice(String path, List<GWTVOfficeDocument> gwtvOfficeDocuments, String password) throws Exception;
	public void loginSSO() throws Exception;
}
