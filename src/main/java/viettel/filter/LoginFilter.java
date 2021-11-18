package viettel.filter;

import clients.ConnectService;
import com.openkm.SecurityConfig;
import com.openkm.api.OKMAuth;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.HttpSessionManager;
import com.openkm.core.RepositoryException;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.AuthTicketDao;
import com.openkm.dao.bean.AuthTicket;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.module.AuthModule;
import com.openkm.module.ModuleManager;
import com.openkm.module.db.stuff.DbSessionManager;
import com.openkm.spring.PrincipalUtils;
import net.shibboleth.utilities.java.support.httpclient.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.support.WebApplicationContextUtils;
import viettel.passport.client.ObjectToken;
import viettel.passport.client.UserToken;
import viettel.passport.util.Connector;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;


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
		authTicket.setEmpCode(userInfo.getStaffCode());
		try {
			AuthTicketDao.saveOrUpdate(authTicket);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		if (userInfo != null) {
			userInfo.setUserID(132415L);
			userInfo.setStaffCode("132415");
			if (req.getSession(true).getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) == null) {
				try {
					List<Role> roles = AuthDAO.findRolesByUser(userInfo.getStaffCode(), true);
					List<String> listGranted = new ArrayList<>();
					if (roles.isEmpty()) {
						String service = cnn.getServiceURL();
						res.sendRedirect(service + "/unauthorized.jsp");
					} else {
						for (Role r : roles) {
							listGranted.add(r.getId());
						}
						SecurityContextHolder.getContext();

						AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
						UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userInfo.getStaffCode(), null, AuthorityUtils.createAuthorityList(listGranted.toArray(new String[listGranted.size()])));
						userAuth.setDetails(authenticationDetailsSource.buildDetails(req));

//							Authentication authentication = authenticationManager.authenticate(userAuth);

						SecurityContextHolder.getContext().setAuthentication(userAuth);
						SecurityContext sc = SecurityContextHolder.getContext();
						HttpSession session = req.getSession(true);
						session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

						res.sendRedirect( "/kms");
					}

				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} else {
				res.sendRedirect( "/kms");
			}
		} else {
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
