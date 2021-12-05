/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) 2006-2017  Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.servlet.admin;


import com.google.zxing.common.detector.MathUtils;
import com.openkm.bean.THDVBReportBeanDetail;
import com.openkm.bean.THDVBReportBeanGeneral;

import com.openkm.bean.KQTTReportBean;

import com.openkm.core.DatabaseException;
import com.openkm.dao.ReportExportDAO;
import com.openkm.dao.UserDAO;
import com.openkm.dao.bean.ActivityFilter;
import com.openkm.dao.bean.OrganizationVTX;
import com.openkm.util.DownloadReportUtils;
import com.openkm.util.WebUtils;
import com.spire.doc.Document;
import com.spire.doc.Table;
import com.spire.doc.TableRow;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Activity log servlet
 */
public class ReportExportServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ReportExportServlet.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		log.debug("doGet({}, {})", request, response);

		ServletContext sc = getServletContext();
		request.setCharacterEncoding("UTF-8");
		String dbegin = WebUtils.getString(request, "dbegin");
		String dend = WebUtils.getString(request, "dend");
		String user = WebUtils.getString(request, "user");
		String action = WebUtils.getString(request, "action");
		String minutes = WebUtils.getString(request, "minutes");
		String item = WebUtils.getString(request, "item");
		String action_ = WebUtils.getString(request, "action_");

		String userId = request.getRemoteUser();



		try {
			if (!dbegin.equals("") && !dend.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ActivityFilter filter = new ActivityFilter();
				Calendar begin = Calendar.getInstance();
				begin.setTime(sdf.parse(dbegin));
				begin.set(Calendar.HOUR, 0);
				begin.set(Calendar.MINUTE, 0);
				begin.set(Calendar.SECOND, 0);
				begin.set(Calendar.MILLISECOND, 0);
				filter.setBegin(begin);
				Calendar end = Calendar.getInstance();
				end.setTime(sdf.parse(dend));
				end.add(Calendar.DAY_OF_MONTH, 1);
				end.set(Calendar.HOUR, 0);
				end.set(Calendar.MINUTE, 0);
				end.set(Calendar.SECOND, 0);
				end.set(Calendar.MILLISECOND, 0);
				filter.setEnd(end);
				filter.setUser(user);
				filter.setAction(action);
				filter.setItem(item);


				OrganizationVTX orgUser = UserDAO.getInstance().getOrgByUserId(userId);


				 if ("KQTT".equals(action_))
					doExportKQTT(filter,response);


				if("THDVB".equals(action_)) {
					doExportTHDVB(filter, response, orgUser);
				}
				if("CLVB".equals(action_)) {
					doExportCLVB(filter, Double.parseDouble(minutes));
				}

			}
		} catch (ParseException e) {
			sendErrorRedirect(request, response, e);
		} catch (DatabaseException e) {
			sendErrorRedirect(request, response, e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void doExportTHDVB(ActivityFilter filter, HttpServletResponse response, OrganizationVTX org) throws IOException, URISyntaxException, DatabaseException, ServletException {

		List<THDVBReportBeanDetail> exportBeanList = ReportExportDAO.exportTHDVBByFilter(filter);
		List<THDVBReportBeanGeneral> exportGeneralBeanList = ReportExportDAO.exportTHDVBGeneralByFilter(filter);

		URL res = getClass().getClassLoader().getResource("template/BC_SITUATION_DOCUMENT.doc");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();

		Document docSpire = new Document(absolutePath);
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		map.put("fromDate", format1.format(filter.getBegin().getTime()));
		map.put("toDate", format1.format(DateUtils.addDays(filter.getEnd().getTime(), -1)));
		map.put("orgName", org.getName());
		int index1 = 1;
		Table table = docSpire.getSections().get(0).getTables().get(2);

		List<String> docNameList = new ArrayList<>();
		List<Long> viewNumList = new ArrayList<>();

		for (THDVBReportBeanGeneral elb : exportGeneralBeanList) {
			docNameList.add(elb.getDocName());
			viewNumList.add(elb.getViewedUser());
			List arrList = new ArrayList();
			arrList.add(index1);
			arrList.add(elb.getOrgName());
			arrList.add(elb.getDocName());
			arrList.add(elb.getTotalUser());
			arrList.add(elb.getViewedUser());
			TableRow dataRow = table.addRow();
			for (int col = 0; col < arrList.size(); ++col) {
				dataRow.getCells().get(col).addParagraph().appendText(String.valueOf(arrList.get(col)));
			}

			index1++;

		}

		TableRow dataRow = table.addRow();
		dataRow.getCells().get(0).addParagraph().appendText("TỔNG");
		dataRow.getCells().get(2).addParagraph().appendText(String.valueOf(docNameList.stream().distinct().count()));
		dataRow.getCells().get(4).addParagraph().appendText(String.valueOf(viewNumList.stream().reduce((a, b)->a+b).get()));


		index1 = 1;
		Table table2 = docSpire.getSections().get(0).getTables().get(4);
		for (THDVBReportBeanDetail elb : exportBeanList) {
			List arrList = new ArrayList();
			arrList.add(index1);
			arrList.add(elb.getOrgName());
			arrList.add(elb.getFullname());
			arrList.add(elb.getEmployeeCode());
			arrList.add(elb.getDocName());
			arrList.add(elb.getViewNum());

//			arrList.add(TimeUnit.MILLISECONDS.toMinutes(elb.getTotalTimeView()));
			Double totalTimeView = elb.getTotalTimeView()/60000.0;
			DecimalFormat df = new DecimalFormat("#.#");;
			arrList.add(df.format(totalTimeView));
			arrList.add(elb.getAuthor().split("@")[0]);
			arrList.add(elb.getTimeUpload());



			TableRow dataRow2 = table2.addRow();
			for (int col = 0; col < arrList.size(); ++col) {
				dataRow2.getCells().get(col).addParagraph().appendText(String.valueOf(arrList.get(col)));

			}

			index1++;
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			docSpire.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
		}



		ServletContext context = getServletContext();
		DownloadReportUtils downloadReportUtils = new DownloadReportUtils();
		downloadReportUtils.downloadReportDOC(docSpire, response, context, "download/BC_SITUATION_DOCUMENT.doc");

	}

	public void doExportCLVB(ActivityFilter filter, Double minute) {

	}

	public void doExportKQTT(ActivityFilter filter, HttpServletResponse response) throws IOException, URISyntaxException, DatabaseException, ServletException {

		List<KQTTReportBean> exportBeanList = ReportExportDAO.exportKQTTByFilter(filter);


		URL res = getClass().getClassLoader().getResource("template/BC_RESULT_TRANSMIT.doc");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();

		Document docSpire = new Document(absolutePath);
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		map.put("fromDate", format1.format(filter.getBegin().getTime()));
		map.put("toDate", format1.format(DateUtils.addDays(filter.getEnd().getTime(), -1)));
		int index1 = 1;
		/*Table table = docSpire.getSections().get(0).getTables().get(2);


		List<String> docNameList = new ArrayList<>();
		for (ActivityLogExportBean elb : exportBeanList) {
			List arrList = new ArrayList();
			arrList.add(index1);
			arrList.add(elb.getOrgName());
			arrList.add(elb.getDocumentName());

			String actionName = "";
			switch (elb.getAction()) {
				case "CREATE_DOCUMENT":
					actionName = "Thêm mới";
					break;
				case "CHECKIN_DOCUMENT":
					actionName = "Sửa";
					break;
				case "DELETE_DOCUMENT":
					actionName = "Xóa (thùng rác)";
					break;
				case "PURGE_DOCUMENT":
					actionName = "Xóa";
					break;
				case "MOVE_DOCUMENT":
					actionName = "Phục hồi";
					break;
			}
			arrList.add(actionName);
			TableRow dataRow = table.addRow();
			for (int col = 0; col < arrList.size(); ++col) {
				dataRow.getCells().get(col).addParagraph().appendText(String.valueOf(arrList.get(col)));
			}
			docNameList.add(elb.getDocumentName());
			index1++;

		}

		TableRow dataRow = table.addRow();
		dataRow.getCells().get(0).addParagraph().appendText("TỔNG");
		dataRow.getCells().get(2).addParagraph().appendText(String.valueOf(docNameList.stream().distinct().count()));*/


		index1 = 1;
		Table table2 = docSpire.getSections().get(0).getTables().get(4);
		for (KQTTReportBean elb : exportBeanList) {
			List arrList = new ArrayList();
			arrList.add(index1);
			arrList.add(elb.getOrgName());
			arrList.add(elb.getFullname());
			arrList.add(elb.getEmployeeCode());
			arrList.add(elb.getDocName());
			arrList.add(elb.getAssignDoc());
			arrList.add(elb.getConfirmDate());
			arrList.add(elb.getStartConfirm());
			arrList.add(elb.getEndConfirm());
			Double totalTimeView = elb.getTimerRead()/60000.0;
			DecimalFormat df = new DecimalFormat("#.#");;
			arrList.add(df.format(totalTimeView));



			TableRow dataRow2 = table2.addRow();
			for (int col = 0; col < arrList.size(); ++col) {
				dataRow2.getCells().get(col).addParagraph().appendText(String.valueOf(arrList.get(col)));

			}

			index1++;
		}

		for (Map.Entry<String, String> entry : map.entrySet()) {
			docSpire.replace("${" + entry.getKey() + "}", entry.getValue(), false, true);
		}

		ServletContext context = getServletContext();

		DownloadReportUtils downloadReportUtils = new DownloadReportUtils();
		downloadReportUtils.downloadReportDOC(docSpire, response, context, "download/BC_SITUATION_DOCUMENT.doc");

	}
}
