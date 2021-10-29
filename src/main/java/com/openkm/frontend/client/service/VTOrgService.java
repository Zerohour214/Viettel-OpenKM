package com.openkm.frontend.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.openkm.frontend.client.OKMException;
import com.openkm.frontend.client.bean.GWTOrganization;

import java.util.List;

@RemoteServiceRelativePath("Org")
public interface VTOrgService extends RemoteService {
	public List<GWTOrganization> getAllOrg() throws OKMException;
}
