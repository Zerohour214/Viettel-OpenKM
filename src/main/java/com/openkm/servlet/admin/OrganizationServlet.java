package com.openkm.servlet.admin;

import com.openkm.core.DatabaseException;
import com.openkm.dao.OrganizationVTXDAO;
import com.openkm.util.WebUtils;
import io.swagger.annotations.Api;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class OrganizationServlet extends BaseServlet {
	private static Logger log = LoggerFactory.getLogger(OrganizationServlet.class);
	private OrganizationVTXDAO organizationVTXDAO = new OrganizationVTXDAO();



}
