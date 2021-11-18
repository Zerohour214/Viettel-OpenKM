package viettel.filter;

import com.openkm.core.AccessDeniedException;
import com.openkm.core.DatabaseException;
import com.openkm.core.HttpSessionManager;
import com.openkm.dao.AuthDAO;
import com.openkm.dao.bean.Role;
import com.openkm.dao.bean.User;
import com.openkm.module.db.stuff.DbSessionManager;
import com.openkm.spring.PrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;
import viettel.passport.client.UserToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SecContextFilter extends SecurityContextPersistenceFilter {

	@Autowired
	@Qualifier
	private static AuthenticationManager authenticationManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = null;
		HttpServletResponse res = null;
		if (request instanceof HttpServletRequest) {
			req = (HttpServletRequest)request;
		}

		if (response instanceof HttpServletResponse) {
			res = (HttpServletResponse)response;
		}

		KmConnector cnn = new KmConnector(req, res);
		if (this.alowURL(req.getRequestURI(), KmConnector.getAllowedUrls())) {
			chain.doFilter(req, res);
		} else if (!cnn.isAuthenticate()) {
			if (cnn.hadTicket()) {
				if (!cnn.getAuthenticate()) {
					res.sendRedirect(KmConnector.getErrorUrl());
				} else {
					chain.doFilter(request, response);
				}
			} else {
				res.sendRedirect(cnn.getPassportLoginURL() + "?appCode=" + cnn.getDomainCode() + "&service=" + URLEncoder.encode(cnn.getServiceURL(), "UTF-8"));
			}
		} else {


			chain.doFilter(req, response);
		}

		//			UserToken userInfo = (UserToken) req.getSession().getAttribute("vsaUserToken");

//			try {
//
//				User usr = AuthDAO.findUserByPk(userInfo.getStaffCode());
//				SecurityContextHolder.getContext();
//
//				UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken("okmAdmin", "Poiu098@");
//				userAuth.getDetails();
//				userAuth.getCredentials();
//
//				FilterConfig filterConfig = this.getFilterConfig();
//				WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("authenticationManager");
//				ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
//				authenticationManager = (AuthenticationManager) ctx.getBean("authenticationManager");
//				Authentication authentication = authenticationManager.authenticate(userAuth);
//				authentication.isAuthenticated();
//				authentication.getCredentials();
//				authentication.getDetails();
//
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				HttpSessionManager.getInstance().getSessions();
//
//				SecurityContext sc = SecurityContextHolder.getContext();
//				HttpSession session = req.getSession();
//				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
//
//				Authentication auth1 = PrincipalUtils.getAuthentication();
//				String token = UUID.randomUUID().toString();
//				DbSessionManager.getInstance().add(token, authentication);
//			} catch (DatabaseException e) {
//				e.printStackTrace();
//			}

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
}
