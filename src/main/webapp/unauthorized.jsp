<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.util.FormatUtil" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page isErrorPage="true" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <% if (FormatUtil.isMobile(request)) { %>
  <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0"/>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css" />
  <% } else { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css" />
  <% } %>
  <%
    ResourceBundle rb = ResourceBundle.getBundle("cas");
    String logoutUrl = rb.getString("logoutUrl");
    String service = rb.getString("service");
    String address =  logoutUrl + "?service=" + URLEncoder.encode(service, "UTF-8");
    session.invalidate();
  %>

  <title>Kms Error</title>
</head>

<body>
  <table border="0" width="100%" align="center" style="padding-top: 125px">
  <tr><td align="center">
  <table>
    <tr>
      <td colspan="2" align="center" style="padding-top: 25px;">
      	<h2>Tài khoản đăng nhập không tồn tại trên hệ thống!</h2>
        <h1>HÃY KIỂM TRA LẠI HOẶC LIỆU HỆ VỚI QUẢN TRỊ HỆ THỐNG</h1>
        <h1>TRÂN TRỌNG CẢM ON!</h1>

      </td>
    </tr>
<%--    <tr>--%>
<%--      <td><b>Date:</b></td>--%>
<%--      <td><%= new java.util.Date() %></td>--%>
<%--    </tr>--%>
    <tr>
      <td colspan="2" align="center"><input type="button" value="Về trang chủ" onclick="window.open('<%= address%>', '_self', null)" /></td>
    </tr>
  </table>
  </td></tr></table>
</body>
</html>
