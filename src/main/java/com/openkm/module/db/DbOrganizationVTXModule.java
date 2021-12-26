package com.openkm.module.db;

import com.openkm.bean.OrganizationVTXBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.OrganizationVTXDAO;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.dao.bean.User;
import com.openkm.module.OrgVTXModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Component
public class DbOrganizationVTXModule implements OrgVTXModule, ApplicationContextAware {
	private static Logger log = LoggerFactory.getLogger(DbOrganizationVTXModule.class);
	private static ApplicationContext appCtx;


	@Override
	public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
		DbOrganizationVTXModule.appCtx = appCtx;
	}

	@Override
	public Response createOrg(OrganizationVTXBean organizationVTXBean) throws DatabaseException {
		return OrganizationVTXDAO.getInstance().createOrg(organizationVTXBean);
	}

	@Override
	public void updateOrg(OrganizationVTXBean organizationVTXBean) throws DatabaseException {
		OrganizationVTXDAO.getInstance().updateOrg(organizationVTXBean);
	}

	@Override
	public List<OrganizationVTX> search(String name, String code) throws DatabaseException {
		return OrganizationVTXDAO.getInstance().search(name, code);
	}

	@Override
	public List<OrganizationVTX>  getAllOrgLevelRoot() throws DatabaseException {
		return OrganizationVTXDAO.getInstance().getAllOrgLevelRoot();
	}

	@Override
	public void addUserToOrg(String userId, Long orgId) throws DatabaseException {
		OrganizationVTXDAO.getInstance().addUserToOrg(userId, orgId);
	}

	@Override
	public String importUserToOrg(InputStream fileContent) {
		return OrganizationVTXDAO.getInstance().importUserToOrg(fileContent);
	}

	@Override
	public List<User> findUsersbyOrg(String orgId) throws DatabaseException {
		return OrganizationVTXDAO.getInstance().findUsersbyOrg(orgId);
	}

	@Override
	public void removeUserOrg(Long orgId, String userId) throws DatabaseException {
		 OrganizationVTXDAO.getInstance().removeUserOrg(orgId, userId);
	}

	@Override
	public void deleteOrg(Long orgId) throws DatabaseException {
		OrganizationVTXDAO.getInstance().deleteOrg(orgId);
	}

	@Override
	public List<OrganizationVTX> getAllChild(long parent) throws DatabaseException {
		return OrganizationVTXDAO.getInstance().getAllChild(parent);
	}

	@Override
	public void importOrg(InputStream is) {
		OrganizationVTXDAO.getInstance().importOrg(is);
	}


}
