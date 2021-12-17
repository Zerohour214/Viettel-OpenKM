package com.openkm.rest.endpoint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openkm.bean.OrganizationVTXBean;
import com.openkm.bean.UserOrganizationVTXBean;
import com.openkm.core.DatabaseException;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.module.ModuleManager;
import com.openkm.module.OrgVTXModule;
import com.openkm.servlet.admin.BaseServlet;
import io.swagger.annotations.Api;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Api(description="org-service", value="org-service")
@Path("/organization")
public class OrganizationService extends BaseServlet {
	private static Logger log = LoggerFactory.getLogger(OrganizationService.class);

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void createOrg(@FormParam("orgName") String orgName,
						  @FormParam("orgCode") String orgCode,
						  @FormParam("orgParentId") Long orgParentId,
						  @FormParam("orgParent") String orgParent) throws DatabaseException {
		OrganizationVTXBean organizationVTXBean = new OrganizationVTXBean();
		organizationVTXBean.setOrgCode(orgCode);
		organizationVTXBean.setOrgName(orgName);

		if(orgParentId != null)
			organizationVTXBean.setOrgParent(orgParentId);
		else {
			organizationVTXBean.setOrgParent(-1L);
		}

		OrgVTXModule om = ModuleManager.getOrgVTXModule();

		om.createOrg(organizationVTXBean);
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void updateOrg(@FormParam("orgName") String orgName,
						  @FormParam("orgCode") String orgCode,
						  @FormParam("orgParentId") String orgParentId,
						  @QueryParam("orgId") Long orgId
						  ) throws DatabaseException {
		OrganizationVTXBean organizationVTXBean = new OrganizationVTXBean();
		organizationVTXBean.setOrgCode(orgCode);
		organizationVTXBean.setOrgName(orgName);
		organizationVTXBean.setId(orgId);


		if(!orgParentId.equals("#"))
			organizationVTXBean.setOrgParent(Long.parseLong(orgParentId));
		else {
			organizationVTXBean.setOrgParent(-1L);
		}

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		om.updateOrg(organizationVTXBean);
	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String searchByForm(@FormParam("orgName") String orgName,
						 @FormParam("orgCode") String orgCode) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		if(orgCode == null) orgCode = "";
		if(orgName == null) orgName = "";
		String json = new Gson().toJson(om.search(orgName, orgCode));
		return json;
	}

	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllOrg(@QueryParam("orgName") String orgName,
						 @QueryParam("orgCode") String orgCode) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		if(orgCode == null) orgCode = "";
		if(orgName == null) orgName = "";
		String json = new Gson().toJson(om.search(orgName, orgCode));
		return json;
	}

	@GET
	@Path("/getOrganizationsRoot")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllOrgLevelRoot() throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();

		String json = new Gson().toJson(om.getAllOrgLevelRoot());
		return json;
	}


	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/addUserToOrg")
	public void addUserToOrg(@FormParam("bodyData") String bodyData) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		List<UserOrganizationVTXBean> userOrganizationVTXBeans = new Gson().fromJson(bodyData, new TypeToken<List<UserOrganizationVTXBean>>(){}.getType());

		userOrganizationVTXBeans.forEach(uo -> {
			try {
				om.addUserToOrg(uo.getUserId(), uo.getOrgId());
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		});
	}

	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/importUserToOrg")
	public String importUserToOrg(
			List<Attachment> atts
	) throws DatabaseException, IOException {
		InputStream is = null;
		for (Attachment att : atts) {
			is = att.getDataHandler().getInputStream();
		}
		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		return om.importUserToOrg(is);
	}

	@GET
	@Path("/findUsersbyOrg")
	@Consumes(MediaType.APPLICATION_JSON)
	public String findUsersbyOrg(@QueryParam("orgId") String orgId
							) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		String json = new Gson().toJson(om.findUsersbyOrg(orgId));
		return json;
	}

	@DELETE
	@Path("/removeUserOrg")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUserOrg(@QueryParam("orgId") Long orgId,
								@QueryParam("userId") String userId
	) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		om.removeUserOrg(orgId, userId);
	}

	@DELETE
	@Path("/deleteOrg")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUserOrg(@QueryParam("orgId") Long orgId

	) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		om.deleteOrg(orgId);
	}

	@GET
	@Path("/getAllOrgChild")
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAllOrg(@QueryParam("parent") Long parent) throws DatabaseException {

		OrgVTXModule om = ModuleManager.getOrgVTXModule();

		String json = new Gson().toJson(om.getAllChild(parent));
		return json;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/importOrg")
	public void importOrg(
			List<Attachment> atts
	) throws DatabaseException, IOException {
		InputStream is = null;
		for (Attachment att : atts) {
			is = att.getDataHandler().getInputStream();
		}
		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		om.importOrg(is);
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/downloadTemplateImportOrg")
	public Response downloadTemplateImportOrg(@QueryParam("type") String type) throws DatabaseException, IOException, URISyntaxException {

		String resourceName = "";

		switch (type) {
			case "ORG":
				resourceName = "template/IMPORT_ORG.xls";
				break;
			case "USER_ORG":
				resourceName = "template/IMPORT_USER_ORG.xls";
				break;
			case "USER_CREATE":
				resourceName = "template/IMPORT_USER_CREATE.xls";
				break;
		}



		URL res = getClass().getClassLoader().getResource(resourceName);
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();

		return Response
				.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()))
				.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/downloadOrgList")
	public Response downloadOrgList() throws DatabaseException, IOException, URISyntaxException {

		String resourceName = "template/EXPORT_ORG.xls";
		URL res = getClass().getClassLoader().getResource(resourceName);
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
		InputStream is = new BufferedInputStream(new FileInputStream(absolutePath));

		OrgVTXModule om = ModuleManager.getOrgVTXModule();
		List<OrganizationVTX> organizationVTXList = om.search("", "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beans", organizationVTXList);
		XLSTransformer transformer = new XLSTransformer();
		Workbook resultWorkbook = null;
		try {
			resultWorkbook = transformer.transformXLS(is, map);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		resultWorkbook.write(byteArrayOutputStream);

		return Response
				.ok(byteArrayOutputStream.toByteArray(), MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()))
				.build();
	}
}
