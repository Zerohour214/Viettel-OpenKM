
<%@ page import="com.openkm.util.FormatUtil" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="viettel.filter.KmConnector" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%

    %>
    <title>Log in</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="./js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="./js/jstree.min.js"></script>
</head>
<body>
    <div class="container">
        <form id="login-form">
            <div class="mb-3 mt-3">
                <label for="email" class="form-label">Tên đăng nhập:</label>
                <input type="email" class="form-control" id="email" placeholder="Nhập username, email" name="email">
            </div>
            <div class="mb-3">
                <label for="pwd" class="form-label">Mật khẩu:</label>
                <input type="password" class="form-control" id="pwd" placeholder="Nhập mật khẩu" name="pswd">
            </div>
            <div class="d-flex justify-content-between">
                <button type="button" class="btn btn-success" onClick="login('<%=request.getAttribute("urlLogin")%>')">Đăng nhập</button>
                <button type="button" class="btn btn-outline-primary" onClick="loginSSO('<%=request.getAttribute("urlSSO")%>')">Đăng nhập SSO</button>
            </div>

        </form>
    </div>
</body>
<script>
    function loginSSO(url) {
        window.location.href = url;
        return false;
    }

    function login(url) {
        window.location.href = url;
        return false;
    }

</script>
</html>
