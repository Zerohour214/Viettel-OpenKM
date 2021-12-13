package com.openkm.rest.endpoint;

import com.google.gson.Gson;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.module.ModuleManager;
import com.openkm.module.OrgVTXModule;
import com.openkm.module.UserModule;
import io.swagger.annotations.Api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Api(description="user-service", value="user-service")
@Path("/user")
public class UserService {
	@POST
	@Path("/getAllUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getAllUser(@FormParam("userSearch") String userSearch, @QueryParam("notInOrg") int isNotInOrg) throws DatabaseException {

		UserModule um = ModuleManager.getUserModule();
		if(userSearch == null) userSearch = "";

		String json = new Gson().toJson(um.getAllUser(userSearch, isNotInOrg));
		return json;
	}

	@GET
	@Path("/getOrgByUserId")
	public OrganizationVTX getOrgByUserId(@QueryParam("userId") String userId) throws DatabaseException {
		UserModule um = ModuleManager.getUserModule();
		if(userId == null) userId = "";


		OrganizationVTX organizationVTX = um.getOrgByUserId(userId);
		return organizationVTX;
	}
}
