<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="Shortcut icon" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="../css/dataTables-1.10.10/jquery.dataTables-1.10.10.min.css"/>
    <link rel="stylesheet" type="text/css" href="../css/jquery-ui-1.10.3/jquery-ui-1.10.3.css"/>
    <link rel="stylesheet" type="text/css" href="../css/chosen.css"/>
    <link rel="stylesheet" type="text/css" href="css/admin-style.css"/>
    <link rel="stylesheet" type="text/css" href="../renew/css/layout-admin.css"/>
    <link rel="stylesheet" type="text/css" href="../renew/fonts/glyphicons/glyphicons.css"/>
    <link rel="stylesheet" href="../css/font-awesome/font-awesome.min.css"/>
    <style type="text/css">
        .ui-datepicker-trigger {
            padding-left: 4px;
            padding-right: 14px;
        }
    </style>

    <script type="text/javascript" src="../js/utils.js"></script>
    <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../js/jquery-ui-1.10.3/jquery-ui-1.10.3.js"></script>
    <script type="text/javascript" src="../js/jquery.dataTables-1.10.10.min.js"></script>
    <script type="text/javascript" src="../js/chosen.jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $("#dbegin").datepicker({
                showOn: "button",
                buttonImage: "img/action/calendar_icon_green.png",
                buttonImageOnly: true,
                dateFormat: "yy-mm-dd",
                defaultDate: "-1w",
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 1,
                showWeek: false,
                firstDay: 1,
                onClose: function (selectedDate) {
                    $("#dend").datepicker("option", "minDate", selectedDate);
                    $('.ui-datepicker-trigger').css("vertical-align", "middle");
                }
            });

            $("#dend").datepicker({
                showOn: "button",
                buttonImage: "img/action/calendar_icon_green.png",
                buttonImageOnly: true,
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                changeYear: true,
                numberOfMonths: 1,
                showWeek: false,
                firstDay: 1,
                onClose: function (selectedDate) {
                    $("#dbegin").datepicker("option", "maxDate", selectedDate);
                    $('.ui-datepicker-trigger').css("vertical-align", "middle");
                }
            });

            $('.ui-datepicker-trigger').css('vertical-align', 'middle');
            $('select#user').chosen({disable_search_threshold: 10});
            $('select#action').chosen({disable_search_threshold: 10});

        });
    </script>
    <title>Activity Log</title>
</head>
<body>
<c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%>
</c:set>
<c:choose>
    <c:when test="${isAdmin}">
        <ul id="breadcrumb">
            <li class="path">
                <a href="ActivityLog">Activity log</a>
            </li>
        </ul>
        <br/>
        <div style="width:95%; margin-left:auto; margin-right:auto;">

            <div class="card">
                <div class="card-header"><h4>Báo cáo tình hình đọc văn bản</h4></div>
                <div class="card-body">
                    <form action="ReportExport" style="width: 50vw">
                        <b>From</b> <input type="text" name="dbegin" id="dbegin"  size="15"
                                           readonly="readonly"/>
                        <b>To</b> <input type="text" name="dend" id="dend"  size="15"
                                         readonly="readonly"/>
                        <input type="hidden" name="action_" value="THDVB">
                        <button type="submit" class="btn btn-success" id="transmit-export-btn" style="padding: 0px 15px !important;">
                            <span class="fa fa-download"></span>&nbsp;Export
                        </button>
                    </form>
                </div>
            </div>


        </div>
    </c:when>
    <c:otherwise>
        <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
</c:choose>
</body>
</html>
