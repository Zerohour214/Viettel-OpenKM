package viettel.filter;

import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.AuthTicketDao;
import com.openkm.dao.bean.AuthTicket;
import com.openkm.dao.bean.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.support.WebApplicationContextUtils;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.UserToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class LoginFilter implements Filter {
	private static Logger log = Logger.getLogger(LoginFilter.class);
	private static HashSet<String> casAllowedURL = new HashSet();

	@Autowired
	@Qualifier

	private static AuthenticationManager authenticationManager;


	public static HashSet<String> getCasAllowedURL() {
		return casAllowedURL;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			filterConfig.getServletContext();
			WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("authenticationManager");
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
			authenticationManager = (AuthenticationManager) ctx.getBean("authenticationManager");
			log.debug("lay danh sach AllowUrl tu file config 'cas_en_US.properties'");
			if (KmConnector.getAllowedUrls() != null) {
				String[] var5;
				int var4 = (var5 = KmConnector.getAllowedUrls()).length;

				for (int var3 = 0; var3 < var4; ++var3) {

					String temp = var5[var3];
					getCasAllowedURL().add(temp);
				}
			}

		} catch (Exception var6) {
			log.error("Loi lay danh sach AllowUrl tu file config:'cas_en_US.properties'");
			throw new ExceptionInInitializerError(var6);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = null;
		HttpServletResponse res = null;

		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest) request;
		}

		if (response instanceof HttpServletResponse) {
			res = (HttpServletResponse) response;
		}

		KmConnector cnn = new KmConnector(req, res);
		if (!cnn.isAuthenticate()) {
			if (cnn.hadTicket()) {
				if (!cnn.getAuthenticate()) {
					res.sendRedirect(KmConnector.getErrorUrl());
				}
			}
		}

		UserToken userInfo = (UserToken) req.getSession().getAttribute("vsaUserToken");
		String tmpTicket = (String) req.getSession().getAttribute("AuthTicket");

		AuthTicket authTicket = new AuthTicket();
		authTicket.setTicket(tmpTicket);
		authTicket.setEmpCode(userInfo.getUserName());
//		authTicket.setEmpCode("132415");
		try {
			AuthTicketDao.saveOrUpdate(authTicket);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		if (userInfo != null) {

			if (req.getSession(true).getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) == null) {
				try {

					log.info("----------------------------USERNAME: " + userInfo.getUserName());
					log.info("----------------------------STAFFCODE: " + userInfo.getStaffCode());

					List<Role> roles = AuthDAO.findRolesByUser(userInfo.getUserName(), true);
					List<String> listGranted = new ArrayList<>();
					if (roles.isEmpty()) {
						String service = cnn.getServiceURL();
						log.info("--------------------------UNAUTHORIZE");
						res.sendRedirect(service + "/unauthorized.jsp");
					} else {
						for (Role r : roles) {
							listGranted.add(r.getId());
						}
						SecurityContextHolder.getContext();

						AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
						UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), null, AuthorityUtils.createAuthorityList(listGranted.toArray(new String[listGranted.size()])));
						userAuth.setDetails(authenticationDetailsSource.buildDetails(req));

//							Authentication authentication = authenticationManager.authenticate(userAuth);

						SecurityContextHolder.getContext().setAuthentication(userAuth);
						SecurityContext sc = SecurityContextHolder.getContext();
						HttpSession session = req.getSession(true);
						session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

						log.info("-------------------------------------KMS 1");
						res.sendRedirect( "/kms");
					}

				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} else {
				log.info("-------------------------------------KMS 2");
				res.sendRedirect( "/kms");
			}
		} else {
			log.info("-------------------------------------KMS 3");
			res.sendRedirect( "/kms");
		}
	}

	@Override
	public void destroy() {

	}

	private Boolean alowURL(String url, String[] listAlowUrl) {
		String[] var6 = listAlowUrl;
		int var5 = listAlowUrl.length;

		for (int var4 = 0; var4 < var5; ++var4) {

			String str = var6[var4];
			if (url.equalsIgnoreCase(str)) {
				return true;
			}
		}

		return false;
	}

	private HashSet<String> getVsaAllowedServletPath(HttpServletRequest request) {

		UserToken vsaUserToken = (UserToken) request.getSession().getAttribute("vsaUserToken");
		HashSet<String> vsaAllowedURL = new HashSet();
		Iterator var5 = vsaUserToken.getObjectTokens().iterator();

		while (var5.hasNext()) {
			ObjectToken ot = (ObjectToken) var5.next();

			String servletPath = ot.getObjectUrl();
			if (!"#".equals(servletPath)) {
				vsaAllowedURL.add(servletPath.split("\\?")[0]);
			}
		}

		return vsaAllowedURL;
	}
}

/*package viettel.filter;

import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.AuthTicketDao;
import com.openkm.dao.bean.AuthTicket;
import com.openkm.dao.bean.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.support.WebApplicationContextUtils;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.UserToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class LoginFilter  implements Filter {
	private static Logger log = Logger.getLogger(LoginFilter.class);
	private static HashSet<String> casAllowedURL = new HashSet();

	@Autowired
	@Qualifier
	private static  AuthenticationManager authenticationManager;
	public static HashSet<String> getCasAllowedURL() {
		return casAllowedURL;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			filterConfig.getServletContext();
			WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("authenticationManager");
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
			authenticationManager = (AuthenticationManager) ctx.getBean("authenticationManager");
			log.debug("lay danh sach AllowUrl tu file config 'cas_en_US.properties'");
			if (KmConnector.getAllowedUrls() != null) {
				String[] var5;
				int var4 = (var5 = KmConnector.getAllowedUrls()).length;

				for(int var3 = 0; var3 < var4; ++var3) {
					String temp = var5[var3];
					getCasAllowedURL().add(temp);
				}
			}

		} catch (Exception var6) {
			log.error("Loi lay danh sach AllowUrl tu file config:'cas_en_US.properties'");
			throw new ExceptionInInitializerError(var6);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		log.error("################# VAO HAM FILTER");
		System.out.println("============ VAO HAM FILTER =-=======");
		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest)request;
		}

		if (response instanceof HttpServletResponse) {
			res = (HttpServletResponse)response;
		}

		System.out.println("================ SESSION TRA VE =======");
		System.out.println(req.getRequestURI());
		System.out.println( req.getSession().getAttribute("vsaUserToken"));

		KmConnector cnn = new KmConnector(req, res);
		if (this.alowURL(req.getRequestURI(), KmConnector.getAllowedUrls())) {
			log.error("################# Duoc Allow");
			System.out.println("============ Duoc Allow =-=======");
			chain.doFilter(req, res);
		} else if (!cnn.isAuthenticate()) {
			log.error("################# NEU CHUA AUTHENTICATE");
			System.out.println("============ EU CHUA AUTHENTICATE =-=======");
			if (cnn.hadTicket()) {
				String jwt1 = req.getHeader("Authorization");
				String ticket_tmp = req.getParameter("ticket");
				log.error("################# Da co ticket");
				System.out.println("============ DA CO TICKET =-=======");
				HttpServletRequest temp2 = (HttpServletRequest) request;
				System.out.println( temp2.getSession().getId());
				if (!cnn.getAuthenticate()) {
					log.error("################# Nhay vao loi ");
					System.out.println("============ Nhay vao loi =-=======");
					res.sendRedirect(KmConnector.getErrorUrl());
				} else {
					log.error("################# Tiep tuc filter ");
					System.out.println("============  Tiep tuc filter =-=======");

					System.out.println("================ SESSION KHI MOI AUTHENTICATE =======");
					HttpServletRequest temp1 = (HttpServletRequest) request;
					System.out.println( temp1.getSession().getAttribute("vsaUserToken"));
					System.out.println( temp1.getSession().getId());
					chain.doFilter(request, response);
					System.out.println("================ SESSION SAU AUTHENTICATE =======");
					System.out.println( temp1.getSession().getAttribute("vsaUserToken"));
					System.out.println( temp1.getSession().getId());

				}
			} else {
				System.out.println("###### SEND REDIRECT");
				log.error("NHAY VAO REDIRET");
				res.sendRedirect(cnn.getPassportLoginURL() + "?appCode=" + cnn.getDomainCode() + "&service=" + URLEncoder.encode(cnn.getServiceURL(), "UTF-8"));
			}
		} else {
			System.out.println("###### VO ESLE DA CO SESSION");
			log.error("===== NHAY VAO ELSE DA CO SESSION");
			UserToken userInfo = (UserToken) req.getSession().getAttribute("vsaUserToken");
			String tmpTicket = (String) req.getSession().getAttribute("AuthTicket");

			AuthTicket authTicket = new AuthTicket();
			authTicket.setTicket(tmpTicket);
//			authTicket.setEmpCode(userInfo.getStaffCode());
			authTicket.setEmpCode(userInfo.getUserName());
			try {
				AuthTicketDao.saveOrUpdate(authTicket);
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
			if(userInfo != null){
				if(req.getSession(true).getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) == null){
					try {
//						List<Role> roles = AuthDAO.findRolesByUser(userInfo.getStaffCode(), true);
						List<Role> roles = AuthDAO.findRolesByUser(userInfo.getUserName(), true);
						log.debug("################# Vo day " + userInfo.getUserName());
						List<String> listGranted = new ArrayList<>();
						if(roles.isEmpty()){
							System.out.println("###### KO CO ROLE");
							log.error("=====KO CO ROLE");
							String service = cnn.getServiceURL();
							res.sendRedirect(service + "/unauthorized.jsp");
						} else {
							for (Role r : roles) {
								listGranted.add(r.getId());
							}
							SecurityContextHolder.getContext();
							System.out.println("###### LAY DUOC ROLE");
							log.error("=====LAY DUOC ROLE");
							AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
//							UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userInfo.getStaffCode(), null, AuthorityUtils.createAuthorityList(listGranted.toArray(new String[listGranted.size()])));
							UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), null, AuthorityUtils.createAuthorityList(listGranted.toArray(new String[listGranted.size()])));
							userAuth.setDetails(authenticationDetailsSource.buildDetails(req));

//							Authentication authentication = authenticationManager.authenticate(userAuth);

							SecurityContextHolder.getContext().setAuthentication(userAuth);
							SecurityContext sc = SecurityContextHolder.getContext();
							HttpSession session = req.getSession(true);
							session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

							chain.doFilter(request, response);
						}

					} catch (DatabaseException e) {
						System.out.println("############ LOI ROI #############");
						log.error("VAO DAY BI LOI");
						e.printStackTrace();
					}
				} else{
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {

	}

	private Boolean alowURL(String url, String[] listAlowUrl) {
		String[] var6 = listAlowUrl;
		int var5 = listAlowUrl.length;

		for(int var4 = 0; var4 < var5; ++var4) {
			String str = var6[var4];
			if (url.equalsIgnoreCase(str)) {
				return true;
			}
		}

		return false;
	}

	private HashSet<String> getVsaAllowedServletPath(HttpServletRequest request) {
		UserToken vsaUserToken = (UserToken)request.getSession().getAttribute("vsaUserToken");
		HashSet<String> vsaAllowedURL = new HashSet();
		Iterator var5 = vsaUserToken.getObjectTokens().iterator();

		while(var5.hasNext()) {
			ObjectToken ot = (ObjectToken)var5.next();
			String servletPath = ot.getObjectUrl();
			if (!"#".equals(servletPath)) {
				vsaAllowedURL.add(servletPath.split("\\?")[0]);
			}
		}

		return vsaAllowedURL;
	}
}*/
