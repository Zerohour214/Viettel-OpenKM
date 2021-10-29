package com.openkm.module.db;

import com.openkm.core.DatabaseException;
import com.openkm.dao.OrganizationVTXDAO;
import com.openkm.dao.UserDAO;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.dao.bean.User;
import com.openkm.module.OrgVTXModule;
import com.openkm.module.UserModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbUserModule implements UserModule, ApplicationContextAware {
	private static Logger log = LoggerFactory.getLogger(DbUserModule.class);
	private static ApplicationContext appCtx;
	@Override
	public List<User> getAllUser(String search) throws DatabaseException {
		return UserDAO.getInstance().getAllUser(search);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		DbUserModule.appCtx = appCtx;
	}
}
