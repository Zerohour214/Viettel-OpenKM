// VTX
package viettel.filter;

import com.viettel.crypto.CryptoUtil;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.client.UserToken;
import viettel.passport.util.SecureURL;
import viettel.passport.util.XMLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class KmConnector {
	private HttpServletRequest request;
	private String ticket;
	public static String passportLoginURL;
	public static String serviceURL;
	public static String domainCode;
	public static String passportValidateURL;
	public static String errorUrl;
	public static String[] allowedUrls;
	public static Boolean isGetRoleAndMenu;
	public static String vsaadminv3ServiceUrl;
	public static String actorUserName;
	public static String actorPassword;
	public static String actorIpLan;
	public static String actorIpWan;
	public static String appCode;
	public static String serviceUsers;
	public static String ticketServiceUrl;
	public static final String FILE_URL = "cas";
	public static ResourceBundle rb;
	public static boolean isAuthen = false;
	private static Logger log = Logger.getLogger(KmConnector.class);

	static {
		try {
			rb = ResourceBundle.getBundle("cas");
			passportLoginURL = getStringConfig("loginUrl");
			serviceURL = getStringConfig("service");
			domainCode = getStringConfig("domainCode");
			passportValidateURL = getStringConfig("validateUrl");
			errorUrl = getStringConfig("errorUrl");
			allowedUrls = getStringConfig("AllowUrl").split(",");
			if (rb.containsKey("isGetRoleAndMenu")) {
				isGetRoleAndMenu = "true".equalsIgnoreCase(getStringConfig("isGetRoleAndMenu"));
			} else {
				isGetRoleAndMenu = false;
			}

			if (rb.containsKey("vsaadminv3ServiceUrl")) {
				vsaadminv3ServiceUrl = getStringConfig("vsaadminv3ServiceUrl");
			}

			if (rb.containsKey("actorUserName")) {
				actorUserName = getStringConfig("actorUserName");
			}

			if (rb.containsKey("actorPassword")) {
				actorPassword = getStringConfig("actorPassword");
			}

			if (rb.containsKey("appCode")) {
				appCode = getStringConfig("appCode");
			}

			if (rb.containsKey("actorIpLan")) {
				actorIpLan = getStringConfig("actorIpLan");
			}

			if (rb.containsKey("actorIpWan")) {
				actorIpWan = getStringConfig("actorIpWan");
			}

			if (rb.containsKey("sso.serviceUsers")) {
				serviceUsers = getStringConfig("sso.serviceUsers");
			}

			if (rb.containsKey("ticketServiceUrl")) {
				ticketServiceUrl = getStringConfig("ticketServiceUrl");
			}
		} catch (MissingResourceException var1) {
			log.error("Init parameter ERROR:", var1);
		}

	}

	public static String getStringConfig(String key) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + key + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public  static String getLogoutURL(){
		try {
			return rb.getString("logoutUrl");
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + "logoutUrl" + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public KmConnector(HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
	}

	public Boolean isAuthenticate() {
		return this.request.getSession().getAttribute("vsaUserToken") != null;
	}

	public Boolean hadTicket() {
		return this.request.getParameter("ticket") != null;
	}

	public Boolean 	getAuthenticate() throws IOException {

//
//		Long TIME_OUT = 1800000l;
		try {
			String ticket_tmp = this.request.getParameter("ticket");
			if (ticket_tmp != null && !"".equalsIgnoreCase(ticket_tmp)) {

				ServiceTicketValidator stValidator = new ServiceTicketValidator();
				stValidator.setCasValidateUrl(passportValidateURL);
				stValidator.setServiceTicket(ticket_tmp);
				stValidator.setService(serviceURL);
				stValidator.setDomainCode(domainCode);
//				stValidator.validate();

////////////////

				StringBuilder sb = new StringBuilder();
				sb.append(stValidator.getCasValidateUrl());
				if (stValidator.getCasValidateUrl().indexOf(63) == -1) {
					sb.append('?');
				} else {
					sb.append('&');
				}

				sb.append("service=").append(serviceURL).append("&ticket=").append(ticket_tmp);


				sb.append("&domainCode=").append(stValidator.getDomainCode());
				sb.append("&ipWan=").append(stValidator.getIpWan());
				String url = sb.toString();
				log.info("Start validate for URL: " + url);
				long start = System.currentTimeMillis();
				SecureURL secure = new SecureURL();
				String res = "";
				if (stValidator.getCookie() != null & !"".equals(stValidator.getCookie())) {
					res = secure.retrieve(url);
				} else {
					res = secure.retrieve(url);
				}

				log.info("======================SSO================" + res);

				UserToken userToken = parseXMLResponse(res, true);

				HttpSession session = this.request.getSession(true);
				String ip = this.request.getHeader("VTS-IP");
				String mac = this.request.getHeader("VTS-MAC");
				session.setAttribute("VTS-IP", ip);
				session.setAttribute("VTS-MAC", mac);
//				if (!stValidator.isAuthenticationSuccesful()) {
				log.info("================ GET USERNAME ");
				log.info(userToken.getUserName());


				log.info("================= getUserToken ");
				log.info( userToken.toString());

				if (!isAuthen || userToken == null || userToken.getUserName() == null) {
					session.setAttribute("vsaUserToken", (Object)null);
					session.setAttribute("netID", (Object)null);
					System.out.println("############## KO AUTHEN DUOC ");
					return false;
				} else {
					try {
						if (ip != null && ip.length() > 0) {
							ip = CryptoUtil.parseIP(ip);
						} else {
							ip = "Unknown";
						}

						if (mac != null && mac.length() > 0) {
							mac = CryptoUtil.parseMAC(mac);
						} else {
							mac = "Unknown";
						}
					} catch (Exception var7) {
						log.error("@@@@@@@@@@@@@@@@@ getAuthenticate ERROR:", var7);
					}

					session.setAttribute("vsaUserToken", userToken);
					assert userToken != null;
					session.setAttribute("netID", userToken.getUserName());
					session.setAttribute("VTS-IP", ip);
					session.setAttribute("VTS-MAC", mac);
					session.setAttribute("AuthTicket", ticket_tmp);
					System.out.println("=============TOKEN LAY TU SESSION ");
					UserToken temp = (UserToken) session.getAttribute("vsaUserToken");
					System.out.println(session.getId());
					return true;
				}

				///////////////////////
			} else {
				System.out.println("============= KHONG CO TICKET ");
				return false;
			}
		} catch (ParserConfigurationException | SAXException var8) {
			log.error("############################getAuthenticate ERROR:", var8);
			return false;
		}

	}

	public String getPassportLoginURL() {
		return passportLoginURL;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public String getPassportValidateURL() {
		return passportValidateURL;
	}

	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public static String getErrorUrl() {
		return errorUrl;
	}

	public static void setAllowedUrls(String[] strs) {
		allowedUrls = new String[strs.length];

		for(int i = 0; i < strs.length; ++i) {
			allowedUrls[i] = strs[i];
		}

	}

	public static String[] getAllowedUrls() {
		String[] tmps = new String[allowedUrls.length];

		for(int i = 0; i < allowedUrls.length; ++i) {
			tmps[i] = allowedUrls[i];
		}

		return tmps;
	}

	public static String getSSOLink() throws UnsupportedEncodingException {
		return KmConnector.passportLoginURL + "?appCode=" + KmConnector.domainCode + "&service=" + URLEncoder.encode(KmConnector.serviceURL, "UTF-8");
	}

	public static UserToken parseXMLResponse(String response, boolean isWebInterface) throws ParserConfigurationException, SAXException, IOException {
		log.warn("---------------------------------RESPONSE-SSO-------------------------" + response);
		if (response != null && response.length() != 0 && !response.trim().equalsIgnoreCase("no")) {
			UserToken userToken = new UserToken();
			isAuthen=true;
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(response)));
			NodeList nl = doc.getElementsByTagName("cas:authenticationSuccess");
			if (nl != null && nl.getLength() > 0) {
				Element userEle = (Element)nl.item(0);
				userToken.setUserName(XMLUtil.getTextValue(userEle, "cas:user"));

			}

			return userToken;
		} else {
			isAuthen = false;
			return null;
		}
	}

}


/*package viettel.filter;

import clients.ConnectService;
import com.viettel.crypto.CryptoUtil;
import org.apache.log4j.Logger;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.util.Connector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.openkm.util.CommonUtil.getStringConfig;

public class KmConnector {
	private HttpServletRequest request;
	private String ticket;
	public static String passportLoginURL;
	public static String serviceURL;
	public static String domainCode;
	public static String passportValidateURL;
	public static String errorUrl;
	public static String[] allowedUrls;
	public static Boolean isGetRoleAndMenu;
	public static String vsaadminv3ServiceUrl;
	public static String actorUserName;
	public static String actorPassword;
	public static String actorIpLan;
	public static String actorIpWan;
	public static String appCode;
	public static String serviceUsers;
	public static String ticketServiceUrl;
	public static final String FILE_URL = "cas";
	public static ResourceBundle rb;
	private static Logger log = Logger.getLogger(KmConnector.class);

	static {
		try {
			rb = ResourceBundle.getBundle("cas");
			passportLoginURL = getStringConfig("loginUrl");
			serviceURL = getStringConfig("service");
			domainCode = getStringConfig("domainCode");
			passportValidateURL = getStringConfig("validateUrl");
			errorUrl = getStringConfig("errorUrl");
			allowedUrls = getStringConfig("AllowUrl").split(",");
			if (rb.containsKey("isGetRoleAndMenu")) {
				isGetRoleAndMenu = "true".equalsIgnoreCase(getStringConfig("isGetRoleAndMenu"));
			} else {
				isGetRoleAndMenu = false;
			}

			if (rb.containsKey("vsaadminv3ServiceUrl")) {
				vsaadminv3ServiceUrl = getStringConfig("vsaadminv3ServiceUrl");
			}

			if (rb.containsKey("actorUserName")) {
				actorUserName = getStringConfig("actorUserName");
			}

			if (rb.containsKey("actorPassword")) {
				actorPassword = getStringConfig("actorPassword");
			}

			if (rb.containsKey("appCode")) {
				appCode = getStringConfig("appCode");
			}

			if (rb.containsKey("actorIpLan")) {
				actorIpLan = getStringConfig("actorIpLan");
			}

			if (rb.containsKey("actorIpWan")) {
				actorIpWan = getStringConfig("actorIpWan");
			}

			if (rb.containsKey("sso.serviceUsers")) {
				serviceUsers = getStringConfig("sso.serviceUsers");
			}

			if (rb.containsKey("ticketServiceUrl")) {
				ticketServiceUrl = getStringConfig("ticketServiceUrl");
			}
		} catch (MissingResourceException var1) {
			log.error("Init parameter ERROR:", var1);
		}

	}

	public static String getStringConfig(String key) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + key + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public  static String getLogoutURL(){
		try {
			return rb.getString("logoutUrl");
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + "logoutUrl" + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public KmConnector(HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
	}

	public Boolean isAuthenticate() {
		return this.request.getSession().getAttribute("vsaUserToken") != null;
	}

	public Boolean hadTicket() {
		return this.request.getParameter("ticket") != null;
	}

	public Boolean getAuthenticate() throws IOException {

//
//		Long TIME_OUT = 1800000l;
		try {
			String ticket_tmp = this.request.getParameter("ticket");
			if (ticket_tmp != null && !"".equalsIgnoreCase(ticket_tmp)) {

//				System.out.println("###### SAVED TICKET " + ticket_tmp);
//
//				String sessionUrl = "http://10.60.101.245:8888/ServiceMobile_V02/resources/";
//				String appUrl = "http://10.60.133.36:8080/kms";
//				ConnectService cservice = new ConnectService(sessionUrl, appUrl, 180000l);
//				boolean check = cservice.loginSSO(ticket_tmp);
//				System.out.println("###############TICKET " + ticket_tmp + " RESULT " + check);
//				System.out.println("##### APPURL " + appUrl);

				ServiceTicketValidator stValidator = new ServiceTicketValidator();
				stValidator.setCasValidateUrl(passportValidateURL);
				stValidator.setServiceTicket(ticket_tmp);
				stValidator.setService(serviceURL);
				stValidator.setDomainCode(domainCode);
				stValidator.validate();
				HttpSession session = this.request.getSession();
				String ip = this.request.getHeader("VTS-IP");
				String mac = this.request.getHeader("VTS-MAC");
				session.setAttribute("VTS-IP", ip);
				session.setAttribute("VTS-MAC", mac);
//				if (!stValidator.isAuthenticationSuccesful()) {
				log.info("========= GET USERNAME ");
				log.info(stValidator.getUserToken().getUserName());

				log.info("========= isAuthenticationSuccesful ");
				log.info(stValidator.isAuthenticationSuccesful());

				log.info("========= getUserToken ");
				log.info( stValidator.getUserToken().toString());

				if (!stValidator.isAuthenticationSuccesful() || stValidator.getUserToken() == null || stValidator.getUserToken().getUserName() == null) {
					session.setAttribute("vsaUserToken", (Object)null);
					session.setAttribute("netID", (Object)null);
					return false;
				} else {
					try {
						if (ip != null && ip.length() > 0) {
							ip = CryptoUtil.parseIP(ip);
						} else {
							ip = "Unknown";
						}

						if (mac != null && mac.length() > 0) {
							mac = CryptoUtil.parseMAC(mac);
						} else {
							mac = "Unknown";
						}
					} catch (Exception var7) {
						log.error("@@@@@@@@@@@@@@@@@ getAuthenticate ERROR:", var7);
					}

					session.setAttribute("vsaUserToken", stValidator.getUserToken());
					session.setAttribute("netID", stValidator.getUser());
					session.setAttribute("VTS-IP", ip);
					session.setAttribute("VTS-MAC", mac);
					session.setAttribute("AuthTicket", ticket_tmp);
					return true;
				}
			} else {
				return false;
			}
		} catch (ParserConfigurationException var8) {
			log.error("getAuthenticate ERROR:", var8);
			return false;
		}
	}

	public String getPassportLoginURL() {
		return passportLoginURL;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public String getPassportValidateURL() {
		return passportValidateURL;
	}

	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public static String getErrorUrl() {
		return errorUrl;
	}

	public static void setAllowedUrls(String[] strs) {
		allowedUrls = new String[strs.length];

		for(int i = 0; i < strs.length; ++i) {
			allowedUrls[i] = strs[i];
		}

	}

	public static String[] getAllowedUrls() {
		String[] tmps = new String[allowedUrls.length];

		for(int i = 0; i < allowedUrls.length; ++i) {
			tmps[i] = allowedUrls[i];
		}

		return tmps;
	}

	public static String getSSOLink() throws UnsupportedEncodingException {
		return KmConnector.passportLoginURL + "?appCode=" + KmConnector.domainCode + "&service=" + URLEncoder.encode(KmConnector.serviceURL, "UTF-8");
	}

}*/

/*package viettel.filter;

import com.viettel.crypto.CryptoUtil;
import org.apache.log4j.Logger;
import viettel.passport.client.ServiceTicketValidator;
import viettel.passport.client.UserToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class KmConnector {
	private HttpServletRequest request;
	private String ticket;
	public static String passportLoginURL;
	public static String serviceURL;
	public static String domainCode;
	public static String passportValidateURL;
	public static String errorUrl;
	public static String[] allowedUrls;
	public static Boolean isGetRoleAndMenu;
	public static String vsaadminv3ServiceUrl;
	public static String actorUserName;
	public static String actorPassword;
	public static String actorIpLan;
	public static String actorIpWan;
	public static String appCode;
	public static String serviceUsers;
	public static String ticketServiceUrl;
	public static final String FILE_URL = "cas";
	public static ResourceBundle rb;
	public static boolean isAuthen = false;
	private static Logger log = Logger.getLogger(KmConnector.class);

	static {
		try {
			rb = ResourceBundle.getBundle("cas");
			passportLoginURL = getStringConfig("loginUrl");
			serviceURL = getStringConfig("service");
			domainCode = getStringConfig("domainCode");
			passportValidateURL = getStringConfig("validateUrl");
			errorUrl = getStringConfig("errorUrl");
			allowedUrls = getStringConfig("AllowUrl").split(",");
			if (rb.containsKey("isGetRoleAndMenu")) {
				isGetRoleAndMenu = "true".equalsIgnoreCase(getStringConfig("isGetRoleAndMenu"));
			} else {
				isGetRoleAndMenu = false;
			}

			if (rb.containsKey("vsaadminv3ServiceUrl")) {
				vsaadminv3ServiceUrl = getStringConfig("vsaadminv3ServiceUrl");
			}

			if (rb.containsKey("actorUserName")) {
				actorUserName = getStringConfig("actorUserName");
			}

			if (rb.containsKey("actorPassword")) {
				actorPassword = getStringConfig("actorPassword");
			}

			if (rb.containsKey("appCode")) {
				appCode = getStringConfig("appCode");
			}

			if (rb.containsKey("actorIpLan")) {
				actorIpLan = getStringConfig("actorIpLan");
			}

			if (rb.containsKey("actorIpWan")) {
				actorIpWan = getStringConfig("actorIpWan");
			}

			if (rb.containsKey("sso.serviceUsers")) {
				serviceUsers = getStringConfig("sso.serviceUsers");
			}

			if (rb.containsKey("ticketServiceUrl")) {
				ticketServiceUrl = getStringConfig("ticketServiceUrl");
			}
		} catch (MissingResourceException var1) {
			log.error("Init parameter ERROR:", var1);
		}

	}

	public static String getStringConfig(String key) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + key + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public  static String getLogoutURL(){
		try {
			return rb.getString("logoutUrl");
		} catch (MissingResourceException var2) {
			log.debug("Init parameter " + "logoutUrl" + " ERROR:" + var2.getMessage());
			return "";
		}
	}

	public KmConnector(HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
	}

	public Boolean isAuthenticate() {
		return this.request.getSession().getAttribute("vsaUserToken") != null;
	}

	public Boolean hadTicket() {
		return this.request.getParameter("ticket") != null;
	}

	public Boolean getAuthenticate() throws IOException {

//
//		Long TIME_OUT = 1800000l;
		try {
			String ticket_tmp = this.request.getParameter("ticket");
			if (ticket_tmp != null && !"".equalsIgnoreCase(ticket_tmp)) {

//				System.out.println("###### SAVED TICKET " + ticket_tmp);
//
//				String sessionUrl = "http://10.60.101.245:8888/ServiceMobile_V02/resources/";
//				String appUrl = "http://10.60.133.36:8080/kms";
//				ConnectService cservice = new ConnectService(sessionUrl, appUrl, 180000l);
//				boolean check = cservice.loginSSO(ticket_tmp);
//				System.out.println("###############TICKET " + ticket_tmp + " RESULT " + check);
//				System.out.println("##### APPURL " + appUrl);

				ServiceTicketValidator stValidator = new ServiceTicketValidator();
				stValidator.setCasValidateUrl(passportValidateURL);
				stValidator.setServiceTicket(ticket_tmp);
				stValidator.setService(serviceURL);
				stValidator.setDomainCode(domainCode);
				stValidator.validate();
				HttpSession session = this.request.getSession(true);
				String ip = this.request.getHeader("VTS-IP");
				String mac = this.request.getHeader("VTS-MAC");
				session.setAttribute("VTS-IP", ip);
				session.setAttribute("VTS-MAC", mac);
//				if (!stValidator.isAuthenticationSuccesful()) {
				log.info("================ GET USERNAME ");
				log.info(stValidator.getUserToken().getUserName());

				log.info("================ isAuthenticationSuccesful ");
				log.info(stValidator.isAuthenticationSuccesful());

				log.info("================= getUserToken ");
				log.info( stValidator.getUserToken().toString());

				if (!stValidator.isAuthenticationSuccesful() || stValidator.getUserToken() == null || stValidator.getUserToken().getUserName() == null) {
					session.setAttribute("vsaUserToken", (Object)null);
					session.setAttribute("netID", (Object)null);
					System.out.println("############## KO AUTHEN DUOC ");
					return false;
				} else {
					try {
						if (ip != null && ip.length() > 0) {
							ip = CryptoUtil.parseIP(ip);
						} else {
							ip = "Unknown";
						}

						if (mac != null && mac.length() > 0) {
							mac = CryptoUtil.parseMAC(mac);
						} else {
							mac = "Unknown";
						}
					} catch (Exception var7) {
						log.error("@@@@@@@@@@@@@@@@@ getAuthenticate ERROR:", var7);
					}

					session.setAttribute("vsaUserToken", stValidator.getUserToken());
					session.setAttribute("netID", stValidator.getUser());
					session.setAttribute("VTS-IP", ip);
					session.setAttribute("VTS-MAC", mac);
					session.setAttribute("AuthTicket", ticket_tmp);
					System.out.println("=============TOKEN LAY TU SESSION ");
					UserToken temp = (UserToken) session.getAttribute("vsaUserToken");
					System.out.println(session.getId());
					return true;
				}
			} else {
				System.out.println("============= KHONG CO TICKET ");
				return false;
			}
		} catch (ParserConfigurationException var8) {
			log.error("############################getAuthenticate ERROR:", var8);
			return false;
		}
	}

	public String getPassportLoginURL() {
		return passportLoginURL;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public String getDomainCode() {
		return domainCode;
	}

	public String getPassportValidateURL() {
		return passportValidateURL;
	}

	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public static String getErrorUrl() {
		return errorUrl;
	}

	public static void setAllowedUrls(String[] strs) {
		allowedUrls = new String[strs.length];

		for(int i = 0; i < strs.length; ++i) {
			allowedUrls[i] = strs[i];
		}

	}

	public static String[] getAllowedUrls() {
		String[] tmps = new String[allowedUrls.length];

		for(int i = 0; i < allowedUrls.length; ++i) {
			tmps[i] = allowedUrls[i];
		}

		return tmps;
	}

	public static String getSSOLink() throws UnsupportedEncodingException {
		return KmConnector.passportLoginURL + "?appCode=" + KmConnector.domainCode + "&service=" + URLEncoder.encode(KmConnector.serviceURL, "UTF-8");
	}

}*/


