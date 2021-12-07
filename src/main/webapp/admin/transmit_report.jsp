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

            $('#results-KQTT').dataTable({
                "bStateSave": true,
                "iDisplayLength": 15,
                "lengthMenu": [[10, 15, 20], [10, 15, 20]],
                "fnDrawCallback": function (oSettings) {
                    dataTableAddRows(this, oSettings);
                }
            });
            $('#results-CLVB').dataTable({
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
                        element.datepicker("option", "minDate", selectedDate);
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
                        element.datepicker("option", "maxDate", selectedDate);
                        $('.ui-datepicker-trigger').css("vertical-align", "middle");
                    }
                });
            }

            dbegin($("#dbegin"));
            dend($("#dend"));

            dbegin($("#dbegin-KQTT"));
            dend($("#dend-KQTT"));

            dbegin($("#dbegin-CLVB"));
            dend($("#dend-CLVB"));

            $('.ui-datepicker-trigger').css('vertical-align', 'middle');

            $('select#user').chosen({disable_search_threshold: 10});


            $('select#user-KQTT').chosen({disable_search_threshold: 10});


            $('select#user-CLVB').chosen({disable_search_threshold: 10});


            $("#filter-input-THDVB").click(() => {
                $("#action-input-THDVB").val("Filter-THDVB")
            })

            $("#filter-input-KQTT").click(() => {
                $("#action-input-KQTT").val("Filter-KQTT")
            })

            $("#filter-input-CLVB").click(() => {
                $("#action-input-CLVB").val("Filter-CLVB")
            })


            $("#transmit-export-btn-KQTT").click(() => {
                $("#action-input-KQTT").val("KQTT")
            })
            $("#export-btn-THDVB-DOC").click(() => {
                $("#action-input-THDVB").val("THDVB")
                $("#type-report-THDVB").val("DOC")
            })
            $("#export-btn-THDVB-XLS").click(() => {
                $("#action-input-THDVB").val("THDVB")
                $("#type-report-THDVB").val("XLS")
            })
            $("#export-btn-CLVB-DOC").click(() => {
                $("#action-input-CLVB").val("CLVB")
                $("#type-report-CLVB").val("DOC")
            })
            $("#export-btn-CLVB-XLS").click(() => {
                $("#action-input-CLVB").val("CLVB")
                $("#type-report-CLVB").val("XLS")
            })



            $('#orgSearchSubmitBtn').click((e) => {
                e.preventDefault();
                $('#table-org-search tbody').empty()
                $.ajax({
                    url: '/kms/services/rest/organization/search',
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-search-org").serialize(),
                    success: (response) => {
                        loadOrgSearchParent(response)
                    }
                });
            })

            function loadOrgSearchParent(jsonData) {
                jsonData.forEach(org => {
                    let tr = document.createElement('tr');
                    let tdName = document.createElement('td'), tdCode = document.createElement('td'),
                        tdChoose = document.createElement('td');
                    tdName.innerText = org.name;
                    tdCode.innerText = org.code;

                    let iconChoose = document.createElement('span');
                    iconChoose.setAttribute('class', "fa fa-check-circle")
                    iconChoose.setAttribute('data-target', org.id)

                    iconChoose.onclick = function () {
                        $('.modal-backdrop').css('display', 'none');
                        $('#myModal').css('display', 'none');

                        switch (orgInputTab) {
                            case "orgNameTHDVB":
                                $('#orgIdTHDVB').val(org.id)
                                $('#orgNameTHDVB').val(org.name);
                                break;
                            case "orgNameKQTT":
                                $('#orgIdKQTT').val(org.id)
                                $('#orgNameKQTT').val(org.name);
                                break;
                            case "orgNameCLVB":
                                $('#orgIdCLVB').val(org.id)
                                $('#orgNameCLVB').val(org.name);
                                break;
                        }

                    };

                    tdChoose.append(iconChoose)

                    tr.appendChild(tdCode);
                    tr.appendChild(tdName);
                    tr.appendChild(tdChoose);
                    $('#table-org-search tbody').append(tr);
                })

            }
            var orgInputTab;
            $(".org-input").click((e) => {
                let id = e.target.id;
                switch (id) {
                    case "orgNameTHDVB":
                        orgInputTab = id;
                        break;
                    case "orgNameKQTT":
                        orgInputTab = id;
                        break;
                    case "orgNameCLVB":
                        orgInputTab = id;
                        break;
                }
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

                <li  class="${tab=='THDVB' ? 'active' : ''}"><a data-toggle="tab" href="#THDVB">Báo cáo tình hình đọc văn bản</a></li>
                <li  class="${tab=='KQTT' ? 'active' : ''}"><a data-toggle="tab" href="#KQTT">Báo cáo kết quả truyền thông</a></li>
                <li  class="${tab=='CLVB' ? 'active' : ''}"><a data-toggle="tab" href="#CLVB">Báo cáo chất lượng văn bản</a></li>

            </ul>
            <div class="tab-content">
                <div id="THDVB" class="${tab=='THDVB' ? 'tab-pane fade in active' : 'tab-pane fade'}">
                    <div class="card">
                        <div class="card-body">
                            <table id="results" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="right" colspan="9">
                                        <form action="ReportExport" style="width: 50vw">
                                            <%--<input class="form-control" name="orgParent" id="orgParent" placeholder="Tìm đơn vị cha"
                                                   type="text" autocomplete="off" data-toggle="modal" data-target="#myModal">--%>
                                            <b>Đơn vị</b> <input type="text" name="orgNameTHDVB" id="orgNameTHDVB" size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal" class="org-input"/>
                                            <input type="hidden" name="orgIdTHDVB">
                                            <b>From</b> <input type="text" name="dbegin" id="dbegin"  size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend"  size="15"
                                                             readonly="readonly" value="${dendFilter}"/>
                                            <b>User</b>
                                            <select name="user" id="user" style="width: 125px;" data-placeholder="&nbsp;">
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
                                            <input type="hidden" name="action_" id="action-input-THDVB" value="THDVB">
                                            <input type="hidden" name="type_report" id="type-report-THDVB">
                                            <button type="submit" class="btn btn-primary" id="export-btn-THDVB-DOC" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-THDVB-XLS" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;XLS
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
                                        <td>
                                            <fmt:formatNumber type="number" maxFractionDigits="1" value="${act.totalTimeView/60000}" />
                                                </td>
                                        <td>${act.author}</td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.timeUpload}" pattern="MM/dd/yyyy HH:mm"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>

                <div id="KQTT" class="${tab=='KQTT' ? 'tab-pane fade in active' : 'tab-pane fade'}">
                    <div class="card">
                        <div class="card-body">
                            <table id="results-KQTT" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="right" colspan="9">
                                        <form action="ReportExport" style="width: 50vw">
                                            <b>Đơn vị</b> <input type="text" name="orgNameKQTT" id="orgNameKQTT" size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal" class="org-input"/>
                                            <input type="hidden" name="orgIdKQTT">
                                            <b>From</b> <input type="text" name="dbegin" id="dbegin-KQTT"  size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend-KQTT"  size="15"
                                                             readonly="readonly" value="${dendFilter}"/>

                                            <b>User</b>
                                            <select name="user" id="user-KQTT" style="width: 125px;" data-placeholder="&nbsp;">
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
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary" id="filter-input-KQTT"/>
                                            <input type="hidden" name="action_" id="action-input-KQTT">
                                            <button type="submit" class="btn btn-success" id="transmit-export-btn-KQTT" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;Export
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Đơn vị</th>
                                    <th>Họ tên</th>
                                    <th>Mã nhân viên</th>
                                    <th>Tên văn bản</th>
                                    <th>Ngày nhận văn bản</th>
                                    <th>Thời điểm xác nhận đọc</th>
                                    <th>Thời điểm đọc</th>
                                    <th>Thời điểm kết thúc</th>
                                    <th>Thời gian đọc (phút)</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="act" items="${resultsKQTT}" varStatus="row">
                                    <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>${act.orgName}</td>
                                        <td>${act.fullname}</td>
                                        <td>${act.employeeCode}</td>
                                        <td>${act.docName}</td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.assignDoc}" pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.confirmDate}" pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.startConfirm}" pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.endConfirm}" pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td>${act.timeRead/60000}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div id="CLVB" class="${tab=='CLVB' ? 'tab-pane fade in active' : 'tab-pane fade'}">

                    <div class="card">
                        <div class="card-body">
                            <table id="results-CLVB" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="right" colspan="9">
                                        <form action="ReportExport" style="width: 50vw">
                                            <b>Đơn vị</b> <input type="text" name="orgNameCLVB" id="orgNameCLVB" size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal"/>
                                            <input type="hidden" name="orgIdCLVB">
                                            <b>From</b> <input type="text" name="dbegin" id="dbegin-CLVB"  size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend-CLVB"  size="15"
                                                             readonly="readonly" value="${dendFilter}"/>
                                            <b>User</b>
                                            <select name="user" id="user-CLVB" style="width: 125px;" data-placeholder="&nbsp;">
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
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary" id="filter-input-CLVB"/>
                                            <input type="hidden" name="action_" id="action-input-CLVB">
                                            <input type="hidden" name="type_report" id="type-report-CLVB">
                                            <button type="submit" class="btn btn-primary" id="export-btn-CLVB-DOC" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-CLVB-XLS" style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;XLS
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Tên tài liệu</th>
                                    <th>Lượt user truy cập</th>
                                    <th>Tổng số lượt xem</th>
                                    <th>Số lượt xem < 1 phút</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="act" items="${resultsCLVB}" varStatus="row">
                                    <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>${act.docName}</td>
                                        <td>${act.totalAccess}</td>
                                        <td>${act.totalView}</td>
                                        <td>${act.totalLessOneMin}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="myModal">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <form id="form-search-org" class="row" style="width: 100%;">

                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="Tên đơn vị"
                                               name="orgName">
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" placeholder="Mã đơn vị"
                                               name="orgCode">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="submit" class="btn btn-success" id="orgSearchSubmitBtn">
                                            Tìm kiếm
                                        </button>
                                    </div>

                                    <button type="button" class="close" data-dismiss="modal">&times;</button>

                                </form>
                            </div>
                            <!-- Modal body -->
                            <div class="modal-body">

                                <div class="row">
                                    <table class="table table-hover table-bordered" id="table-org-search">
                                        <thead>
                                        <tr>
                                            <th>Mã đơn vị</th>
                                            <th>Tên đơn vị</th>
                                            <th>Chọn</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
