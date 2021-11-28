package com.openkm.module;

import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.User;

import java.util.List;

public interface UserModule {
	public List<User> getAllUser(String search, int isNotInOrg) throws DatabaseException;
}
