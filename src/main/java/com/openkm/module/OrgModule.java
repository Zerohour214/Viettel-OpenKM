package com.openkm.module;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;

import java.util.List;

public interface OrgModule {

	public List<Organization> getAllOrg() throws DatabaseException;
}
