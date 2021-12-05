<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <link rel="stylesheet" href="../css/bootstrap/bootstrap.css"/>
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
    <script type="text/javascript" src="../js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#results').dataTable({
                "bStateSave": true,
                "iDisplayLength": 15,
                "lengthMenu": [[10, 15, 20], [10, 15, 20]],
                "fnDrawCallback": function (oSettings) {
                    dataTableAddRows(this, oSettings);
                }
            });

            function dbegin(element) {
                element.datepicker({
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
            }

            function dend(element) {
                element.datepicker({
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
            }

            dbegin($("#dbegin"));
            dend($("#dend"));
            dbegin($("#dbegin1"));
            dend($("#dend1"));
            dbegin($("#dbegin2"));
            dend($("#dend2"));


            $('.ui-datepicker-trigger').css('vertical-align', 'middle');
            $('select#user').chosen({disable_search_threshold: 10});
            $('select#action').chosen({disable_search_threshold: 10});

            $("#filter-input-THDVB").click(() => {
                $("#action-input-THDVB").val("Filter")
            })
            $("#transmit-export-btn-KQTT").click(() => {
                $("#action-input-KQTT").val("KQTT")
            })
            $("#transmit-export-btn-THDVB").click(() => {
                $("#action-input-THDVB").val("THDVB")
            })
            $("#transmit-export-btn-CLVB").click(() => {
                $("#action-input-CLVB").val("CLVB")
            })

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
                <a href="ActivityLog">Báo cáo</a>
            </li>
        </ul>
        <br/>
        <div style="width:95%; margin-left:auto; margin-right:auto;">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#THDVB">Báo cáo tình hình đọc văn bản</a></li>
                <li><a data-toggle="tab" href="#KQTT">Báo cáo kết quả truyền thông</a></li>
                <li><a data-toggle="tab" href="#THCNTL">Báo cáo tình hình cập nhập tài liệu</a></li>
                <li><a data-toggle="tab" href="#CLVB">Báo cáo chất lượng văn bản</a></li>
            </ul>

            <div class="tab-content">
                <div id="THDVB" class="tab-pane fade in active">
                    <div class="card">
                        <div class="card-body">
                            <table id="results" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="right" colspan="9">
                                        <form action="ReportExport" style="width: 50vw">
                                            <b>From</b> <input type="text" name="dbegin" id="dbegin"  size="15"
                                                               readonly="readonly"/>
                                            <b>To</b> <input type="text" name="dend" id="dend"  size="15"
                                                             readonly="readonly"/>
                                            <b>User</b>
                                            <select name="user" id="user" style="width: 125px" data-placeholder="&nbsp;">
                                                <option value="">All</option>
                                                <c:forEach var="user" items="${users}" varStatus="row">
                                                    <c:choose>
                                                        <c:when test="${user == userFilter}">
                                                            <option value="${user}" selected="selected">${user}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${user}">${user}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary" id="filter-input-THDVB"/>
                                            <input type="hidden" name="action_" id="action-input-THDVB">
                                            <button type="submit" class="btn btn-success" id="transmit-export-btn-THDVB" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;Export
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Đơn vị</th>
                                    <th>Họ tên</th>
                                    <th>Mã nhân viên</th>
                                    <th>Tên văn bản đã xem</th>
                                    <th>Số lần xem</th>
                                    <th>Tổng thời gian xem (phút)</th>
                                    <th>Người upload văn bản</th>
                                    <th>Thời điểm upload</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="act" items="${results}" varStatus="row">
                                    <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>${act.orgName}</td>
                                        <td>${act.fullname}</td>
                                        <td>${act.employeeCode}</td>
                                        <td>${act.docName}</td>
                                        <td>${act.viewNum}</td>
                                        <td>${act.totalTimeView/60000}</td>
                                        <td>${act.author}</td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.timeUpload}" pattern="MM/dd/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="KQTT" class="tab-pane fade">
                    <div class="card">
                        <div class="card-body">
                            <form action="ReportExport" style="width: 50vw">
                                <b>From</b> <input type="text" name="dbegin" id="dbegin2"  size="15"
                                                   readonly="readonly"/>
                                <b>To</b> <input type="text" name="dend" id="dend2"  size="15"
                                                 readonly="readonly"/>
                                <input type="hidden" name="action_" id="action-input-KQTT">
                                <button type="submit" class="btn btn-success" id="transmit-export-btn-KQTT" style="padding: 0px 15px !important;">
                                    <span class="fa fa-download"></span>&nbsp;Export
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div id="THCNTL" class="tab-pane fade">
                    <h3>Menu 2</h3>
                    <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
                </div>
                <div id="CLVB" class="tab-pane fade">
                    <div class="card">
                        <div class="card-header"><h4>Báo cáo chất lượng văn bản</h4></div>
                        <div class="card-body">
                            <form action="ReportExport" style="width: 50vw">
                                <b>From</b> <input type="text" name="dbegin" id="dbegin1"  size="15"
                                                   readonly="readonly"/>
                                <b>To</b> <input type="text" name="dend" id="dend1"  size="15"
                                                 readonly="readonly"/>
                                <label>
                                    <b>Số phút</b>
                                </label>
                                <input type="number" name="minutes">
                                <input type="hidden" name="action_" id="action-input-CLVB">
                                <button type="submit" class="btn btn-success" id="transmit-export-btn-CLVB" style="padding: 0px 15px !important;">
                                    <span class="fa fa-download"></span>&nbsp;Export
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
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
