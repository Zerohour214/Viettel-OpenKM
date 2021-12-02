package com.openkm.util;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class DownloadReportUtils {
	public void downloadReport(Document docSpire, HttpServletResponse response, ServletContext context, String templateResource) throws URISyntaxException, ServletException, IOException {
		URL res_ = getClass().getClassLoader().getResource(templateResource);
		File tmpFile = Paths.get(res_.toURI()).toFile();
		String absoluteTmpPath = tmpFile.getAbsolutePath();
		docSpire.saveToFile(absoluteTmpPath, FileFormat.Doc);


		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(tmpFile);
			os = response.getOutputStream();


			String mimeType = context.getMimeType(absoluteTmpPath);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setContentLength((int) tmpFile.length());


			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", tmpFile.getName());
			response.setHeader(headerKey, headerValue);


			int len = -1;
			byte[] buffer = new byte[4096000];
			while ((len = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, len);
			}

		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
	}
}
