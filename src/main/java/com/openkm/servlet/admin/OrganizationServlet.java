package com.openkm.servlet.admin;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


public class OrganizationServlet extends BaseServlet {
	private static Logger log = LoggerFactory.getLogger(OrganizationServlet.class);

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.info("");
		ServletContext sc = getServletContext();
		sc.getRequestDispatcher("/admin/organization_vtx.jsp").forward(request, response);
	}

}
