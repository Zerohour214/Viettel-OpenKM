package com.openkm.module;

import com.openkm.bean.OrganizationVTXBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.Organization;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.dao.bean.User;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

public interface OrgVTXModule {

	public Response createOrg(OrganizationVTXBean organizationVTXBean) throws DatabaseException;
	public void updateOrg(OrganizationVTXBean organizationVTXBean) throws DatabaseException;
	public List<OrganizationVTX> search(String name, String code) throws DatabaseException;
	public List<OrganizationVTX> getAllOrgLevelRoot() throws DatabaseException;
	public void addUserToOrg(String userId, Long orgId) throws DatabaseException;

	public String importUserToOrg(InputStream fileContent);

	public List<User>  findUsersbyOrg(String orgId) throws DatabaseException;

    public void removeUserOrg(Long orgId, String userId) throws DatabaseException;

	public void deleteOrg(Long orgId) throws DatabaseException;
	public List<OrganizationVTX> getAllChild(long parent) throws DatabaseException;
	public void importOrg(InputStream is);
}
