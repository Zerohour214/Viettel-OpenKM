<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bsh.TargetError" %>
<%@ page import="com.openkm.frontend.client.OKMException" %>
<%@ page import="com.openkm.util.FormatUtil" %>
<%@ page isErrorPage="true" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <% if (FormatUtil.isMobile(request)) { %>
    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css"/>
    <% } else { %>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css"/>
    <% } %>
    <style>
        .btn-warning {
            background-color: #e69a2a !important;
            border: solid 1px #e69a2a !important;
        }

        .btn {
            transition: all 0.3s ease;
            color: white;
            background-size: 13px;
            background-position: 6px center;
        }

        .btn-warning:hover, .btn-warning:focus, .btn-warning[disabled] {
            background-color: rgba(230, 154, 42, 0.85) !important;
            border: solid 1px rgba(230, 154, 42, 0.1) !important;
        }
    </style>
    <title>OpenKM Error</title>
</head>
<body>
<table border="0" width="100%" align="center" style="padding-top: 150px">
    <tr>
        <td align="center">
            <table class="form">
                <tr class="fuzzy">
                    <td colspan="2" style="text-align: center; font-weight: bold; font-size: 12px">Application error
                    </td>
                </tr>
                <tr>
                    <td><b>Class:</b></td>
                    <td><%=exception.getClass().getName() %>
                    </td>
                </tr>
                <% if (exception instanceof OKMException) { %>
                <tr>
                    <td><b>Code:</b></td>
                    <td><%=((OKMException) exception).getCode() %>
                    </td>
                </tr>
                <tr>
                    <td><b>Message:</b></td>
                    <td>
                        <pre style="white-space: pre-line; font: 16px 'Times New Roman';">
                            <%=((OKMException) exception).getMessage() %>
                        </pre>
                    </td>
                </tr>
                <% } else if (exception instanceof TargetError) { %>
                <tr>
                    <td><b>Text:</b></td>
                    <td><%=((TargetError) exception).getErrorText() %>
                    </td>
                </tr>
                <tr>
                    <td><b>Source:</b></td>
                    <td><%=((TargetError) exception).getErrorSourceFile() %>
                    </td>
                </tr>
                <tr>
                    <td><b>Line:</b></td>
                    <td><%=((TargetError) exception).getErrorLineNumber() %>
                    </td>
                </tr>
                <% } else { %>
                <tr>
                    <td><b>Message:</b></td>
                    <td>
                        <pre style="white-space: pre-line; font: 16px 'Times New Roman';">
                            <%=exception.getMessage() %>
                        </pre>

                    </td>
                </tr>
                <% } %>
                <tr>
                    <td><b>Date:</b></td>
                    <td><%= new java.util.Date() %>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style="padding-top: 10px;"><input
                        type="button" value="Return" class="yesButton btn btn-warning"
                        onclick="javascript:history.go(-1)"
                        style="box-shadow: none; border: 1px solid #A5A596; height: 28px; width: 76px;"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
