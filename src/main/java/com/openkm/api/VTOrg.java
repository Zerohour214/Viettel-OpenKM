package com.openkm.api;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.frontend.client.bean.GWTOrganization;
import com.openkm.module.AuthModule;
import com.openkm.module.ModuleManager;
import com.openkm.module.OrgModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VTOrg implements OrgModule {
	private static Logger log = LoggerFactory.getLogger(VTOrg.class);
	private static VTOrg instance = new VTOrg();
	private VTOrg() {
	}

	public static VTOrg getInstance() {
		return instance;
	}

	@Override
	public List<Organization> getAllOrg() throws DatabaseException {
		log.debug("getAllOrg()");
		OrgModule vt = ModuleManager.getOrgModule();
		List<Organization> orgs = vt.getAllOrg();
		log.debug("getAllOrg(): ", orgs);
		return orgs;
	}
}
