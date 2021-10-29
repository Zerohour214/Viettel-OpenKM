package com.openkm.module.db;

import com.openkm.core.DatabaseException;
import com.openkm.dao.OrganizationDao;
import com.openkm.dao.bean.Organization;
import com.openkm.module.OrgModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbOrgModule implements OrgModule, ApplicationContextAware {
	private static Logger log = LoggerFactory.getLogger(DbOrgModule.class);
	private static ApplicationContext appCtx;

	@Override
	public List<Organization> getAllOrg() throws DatabaseException {
		List<Organization> organizationList = null;

		try {
			organizationList = OrganizationDao.getInstance().getAllOrg();
		} catch (DatabaseException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage(), e);
		}
		return organizationList;


	}

	@Override
	public void setApplicationContext(ApplicationContext appCtx) throws BeansException {
		DbOrgModule.appCtx = appCtx;
	}
}
