package com.openkm.servlet.frontend;

import clients.ConnectService;
import clients.utils.AES;
import clients.utils.FunctionCommon;
import clients.utils.ServiceConnection;
import clients.utils.User;
import com.google.gson.Gson;
import com.openkm.api.OKMDocument;
import com.openkm.bean.Document;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthTicketDao;
import com.openkm.frontend.client.bean.GWTVOfficeDocument;
import com.openkm.frontend.client.service.VOfficeService;
import com.openkm.spring.PrincipalUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

import static com.openkm.util.CommonUtil.getStringConfig;

public class VOfficeServlet extends OKMRemoteServiceServlet implements VOfficeService {
	private static Logger log = LoggerFactory.getLogger(VTOrgServlet.class);
	private static final long serialVersionUID = 1L;

	OKMDocument okmDocument = OKMDocument.getInstance();

	String sessionUrl = getStringConfig("config", "session.url");
	String appUrl = getStringConfig("config", "app.url");
	String downloadPath = getStringConfig("config", "download.path");
	Long TIME_OUT = 1800000l;
	//	String USER = "tuandn5";
//	String PASS = "222222a@";
	String USER = "197117";
	String PASS = "123456a@";

	@Override
	public void loginSSO() throws Exception {
		String empCode = PrincipalUtils.getUser();
		String ticket = "";
		try {
			ticket = AuthTicketDao.getTicket(empCode);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		ConnectService service = new ConnectService(sessionUrl, appUrl, TIME_OUT);
//		boolean check = service.login("265858", "Poiu098$$");
		System.out.println("##################### Session URL  " + sessionUrl);
		System.out.println("##################### appUrl  " + appUrl);
		System.out.println("##################### ticket  " + ticket);
		boolean check = service.loginSSO(ticket);

		System.out.println("###### LOGIN Result " + check);
	}

	@Override
	public List<GWTVOfficeDocument> getVOfficeDocuments(String code, String password) {
		log.debug("getVOfficeDocuments()");
		String ticket = "";
		List<GWTVOfficeDocument> vOfficeDocs = new ArrayList<GWTVOfficeDocument>();

		if (code == null || code.isEmpty()) {
			return vOfficeDocs;
		}

		String empCode = PrincipalUtils.getUser();
//		try {
//			ticket = AuthTicketDao.getTicket(empCode);
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//		}
		ConnectService service = new ConnectService(sessionUrl, appUrl, TIME_OUT);
		boolean check = service.login(empCode, password);
//        boolean check = service.loginSSO(ticket);
//        check = true;
		if (check) {
			System.out.println("Đã qua check");
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("isSearchMobile", "1"); // Tham so mac dinh khong thay doi
			params.put("status", "0"); // Tham so mac dinh khong thay doi
			params.put("type", "1"); // Tham so mac dinh khong thay doi
			params.put("isNotDuplicateReceivedDocument", "1");

			params.put("keyword", ""); // Tham so truyen vao
			params.put("pageSize", "20"); // Tham so truyen vao
			params.put("startRecord", "0"); // Tham so truyen vao

			JSONObject obj = new JSONObject();
			try {
				obj.put("code", code);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			params.put("document", obj);

			String result = service.getData("DocumentAction.search", params);
			if (result.startsWith("[")) {
				result = result.substring(1);
			}
			if (result.startsWith("]")) {
				result = result.substring(0, result.length() - 1);
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String docCode = jsonObject.getString("code");
				String title = jsonObject.getString("title");
				String area = jsonObject.getString("area");
				JSONArray listAttachment = jsonObject.getJSONArray("listAttachment");
				System.out.println("############# SIZE LIST ATTACHMENT " + listAttachment.length());
				Gson gson = new Gson();
				for (int i = 0; i < listAttachment.length(); i++) {
					GWTVOfficeDocument document = gson.fromJson(listAttachment.get(i).toString(), GWTVOfficeDocument.class);
					document.setCode(docCode);
					document.setTitle(title);
					document.setArea(area);
					vOfficeDocs.add(document);
				}
			} catch (JSONException err) {
				err.printStackTrace();
			}
		}
		log.debug("getVOfficeDocuments: {}", vOfficeDocs);
		return vOfficeDocs;
	}

	@Override
	public void getFileFromVOffice(String path, List<GWTVOfficeDocument> gwtvOfficeDocuments, String password) throws Exception {
		log.debug("getFileFromVOffice()");
		ConnectService service = new ConnectService(sessionUrl, appUrl, TIME_OUT);
//
		String empCode = PrincipalUtils.getUser();
		String ticket = "";
		boolean check = service.login(empCode, password);
//		try {
//			ticket = AuthTicketDao.getTicket(empCode);
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//		}
//        boolean check = service.loginSSO(ticket);
		List<String> errorUploadFiles = new ArrayList<>();
		if (check) {
			for (GWTVOfficeDocument gwtVOfficeDocument : gwtvOfficeDocuments) {
				String downloadFilePath = downloadFileFromVOffice(service, gwtVOfficeDocument);
				try {
					uploadFileToOpenKM(path, gwtVOfficeDocument, downloadFilePath);
				} catch (Exception ex) {
					errorUploadFiles.add(gwtVOfficeDocument.getName());
				} finally {
					File file = new File(downloadFilePath);
					file.delete();
				}
			}
		}
		if (!errorUploadFiles.isEmpty()) {
			String errorStr = String.join(", ", errorUploadFiles);
			throw new Exception(errorStr);
		}
		log.debug("getFileFromVOffice: {}", gwtvOfficeDocuments);
	}

	private String downloadFileFromVOffice(ConnectService service, GWTVOfficeDocument gwtVOfficeDocument) {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("type", "0"); // Tham so mac dinh khong thay doi
		params.put("isOriginal", "0"); // Tham so mac dinh khong thay doi

		params.put("documentId", gwtVOfficeDocument.getDocumentId().toString()); // Tham so truyen vao
		params.put("filePath", gwtVOfficeDocument.getAttachment()); // Tham so truyen vao
		params.put("storage", gwtVOfficeDocument.getStorage()); // Tham so truyen vao

		String downloadFilePath =  downloadPath + gwtVOfficeDocument.getName();
		service.downloadFile("Files.DownloadContentFile", params, downloadFilePath);
		return downloadFilePath;
	}

	private void uploadFileToOpenKM(String path, GWTVOfficeDocument gwtVOfficeDocument, String downloadFilePath) throws Exception {
		Document doc = new Document();
		doc.setPath(path + "/" + gwtVOfficeDocument.getName());
		doc.setDocName(gwtVOfficeDocument.getTitle());
		doc.setDocCode(gwtVOfficeDocument.getCode());
		InputStream is = new FileInputStream(downloadFilePath);
		okmDocument.create(null, doc, is);
		is.close();
	}
}
