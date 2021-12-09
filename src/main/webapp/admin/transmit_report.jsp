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

        #remove-org-search-THDVB,
        #remove-doc-search-THDVB,
        #remove-doc-search-CLVB,
        #remove-doc-search-KQTT,
        #remove-org-search-KQTT{
            position: relative;
            left: -21px;
            font-size: 16px;
            cursor: pointer;
        }

        #docNameCLVB,
        #orgNameKQTT,
        #orgNameTHDVB,
        #docNameKQTT,
        #docNameTHDVB {
            padding-right: 25px;
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

            function setDataTable(element) {
                element.dataTable({
                    "bStateSave": true,
                    "iDisplayLength": 15,
                    "lengthMenu": [[10, 15, 20], [10, 15, 20]],
                    "fnDrawCallback": function (oSettings) {
                        dataTableAddRows(this, oSettings);
                    }
                });
            }

            setDataTable($('#results'))
            setDataTable($('#results-KQTT'))
            setDataTable($('#results-CLVB'))
            setDataTable($('#results-THCNVB'))



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

            dbegin($("#dbegin-THCNVB"));
            dend($("#dend-THCNVB"));


            $('.ui-datepicker-trigger').css('vertical-align', 'middle');

            $('select#user').chosen({disable_search_threshold: 10});


            $('select#user-KQTT').chosen({disable_search_threshold: 10});


            $('select#user-CLVB').chosen({disable_search_threshold: 10});

            $('select#user-THCNVB').chosen({disable_search_threshold: 10});


            $("#filter-input-THDVB").click(() => {
                $("#action-input-THDVB").val("Filter-THDVB")
            })

            $("#filter-input-KQTT").click(() => {
                $("#action-input-KQTT").val("Filter-KQTT")
            })

            $("#filter-input-CLVB").click(() => {
                $("#action-input-CLVB").val("Filter-CLVB")
            })
            $("#filter-input-THCNVB").click(() => {
                $("#action-input-THCNVB").val("Filter-THCNVB")
            })


            $("#export-btn-KQTT-DOC").click(() => {
                $("#action-input-KQTT").val("KQTT")
                $("#type-report-KQTT").val("DOC")
            })
            $("#export-btn-KQTT-XLS").click(() => {
                $("#action-input-KQTT").val("KQTT")
                $("#type-report-KQTT").val("XLS")
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
            $("#export-btn-THCNVB-DOC").click(() => {
                $("#action-input-THCNVB").val("THCNVB")
                $("#type-report-THCNVB").val("DOC")
            })
            $("#export-btn-THCNVB-XLS").click(() => {
                $("#action-input-THCNVB").val("THCNVB")
                $("#type-report-THCNVB").val("XLS")
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
                        console.log(response)
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
                        // $('.modal-backdrop').css('display', 'none');
                        $('#myModal').modal("hide");

                        switch (orgInputTab) {
                            case "orgNameTHDVB":
                                $('#orgIdTHDVB').val(org.id)
                                $('#orgNameTHDVB').val(org.name);
                                break;
                            case "orgNameKQTT":
                                $('#orgIdKQTT').val(org.id)
                                $('#orgNameKQTT').val(org.name);
                                break;
                            case "orgNameTHCNVB":
                                $('#orgIdTHCNVB').val(org.id)
                                $('#orgNameTHCNVB').val(org.name);
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
                    case "orgNameTHCNVB":
                        orgInputTab = id;
                        break;

                }
            })

            var docInputTab;

            $('#docSearchSubmitBtn').click((e) => {
                e.preventDefault();
                $('#table-doc-search tbody').empty()
                $.ajax({
                    url: '/kms/services/rest/document/search',
                    type: 'POST',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    data: $("#form-search-doc").serialize(),
                    success: (response) => {
                        loadDocSearch(response)
                    }
                });
            })

            function loadDocSearch(jsonData) {
                console.log(jsonData)
                jsonData.forEach(doc => {
                    let tr = document.createElement('tr');
                    let tdName = document.createElement('td'), tdCode = document.createElement('td'),
                        tdChoose = document.createElement('td');
                    tdName.innerText = doc.docName;
                    tdCode.innerText = doc.docCode;

                    let iconChoose = document.createElement('span');
                    iconChoose.setAttribute('class', "fa fa-check-circle")
                    iconChoose.setAttribute('data-target', doc.id)

                    iconChoose.onclick = function () {

                        switch (docInputTab) {
                            case "docNameTHDVB":
                                $('#docIdTHDVB').val(doc.id)
                                $('#docNameTHDVB').val(doc.docName);
                                break;
                            case "docNameKQTT":
                                $('#docIdKQTT').val(doc.id)
                                $('#docNameKQTT').val(doc.docName);
                                break;
                            case "docNameCLVB":
                                $('#docIdCLVB').val(doc.id)
                                $('#docNameCLVB').val(doc.docName);
                                break;
                            case "docNameTHCNVB":
                                $('#docIdTHCNVB').val(doc.id)
                                $('#docNameTHCNVB').val(doc.docName);
                                break;

                        }
                        $('#docSearchModal').modal("hide");

                    };

                    tdChoose.append(iconChoose)

                    tr.appendChild(tdCode);
                    tr.appendChild(tdName);
                    tr.appendChild(tdChoose);
                    $('#table-doc-search tbody').append(tr);
                })

            }

            $(".doc-input").click((e) => {
                let id = e.target.id;
                switch (id) {
                    case "docNameTHDVB":
                        docInputTab = id;
                        break;
                    case "docNameKQTT":
                        docInputTab = id;
                        break;
                    case "docNameCLVB":
                        docInputTab = id;
                        break;
                    case "docNameTHCNVB":
                        docInputTab = id;
                        break;

                }
            })

            function removeSearchText(removeButton, idInput, nameInput) {
                removeButton.click(e => {
                    if(idInput)
                        idInput.val("");
                    if(nameInput)
                        nameInput.val("");
                })
            }

            removeSearchText($('#remove-org-search-THDVB'), $('#orgIdTHDVB'), $('#orgNameTHDVB'))
            removeSearchText($('#remove-doc-search-THDVB'), $('#docIdTHDVB'), $('#docNameTHDVB'))
            removeSearchText($('#remove-org-search-KQTT'), $('#orgIdKQTT'), $('#orgNameKQTT'))
            removeSearchText($('#remove-doc-search-KQTT'), $('#docIdKQTT'), $('#docNameKQTT'))
            removeSearchText($('#remove-doc-search-CLVB'), $('#docIdCLVB'), $('#docNameCLVB'))
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


                <li class="${tab=='THDVB' ? 'active' : ''}"><a data-toggle="tab" href="#THDVB"
                                                               onclick="$('#THDVB-form').submit()">Báo cáo tình hình đọc
                    văn bản</a></li>
                <li class="${tab=='KQTT' ? 'active' : ''}"><a data-toggle="tab" href="#KQTT"
                                                              onclick="$('#KQTT-form').submit()">Báo cáo kết quả truyền
                    thông</a></li>
                <li class="${tab=='CLVB' ? 'active' : ''}"><a data-toggle="tab" href="#CLVB">Báo cáo chất lượng văn
                    bản</a></li>

                <li class="${tab=='THCNVB' ? 'active' : ''}"><a data-toggle="tab" href="#THCNVB">Báo cáo tình hình cập
                    nhật văn bản</a></li>


            </ul>
            <br/>
            <div class="tab-content">
                <div id="THDVB" class="${tab=='THDVB' ? 'tab-pane fade in active' : 'tab-pane fade'}">
                    <div class="card">
                        <div class="card-body">
                            <table id="results" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="left" colspan="9">
                                        <form action="ReportExport" style="width: 100vw" id="THDVB-form">

                                                <%--<input class="form-control" name="orgParent" id="orgParent" placeholder="Tìm đơn vị cha"
                                                       type="text" autocomplete="off" data-toggle="modal" data-target="#myModal">--%>
                                            <b>Đơn vị</b> <input type="text" name="orgNameTHDVB" id="orgNameTHDVB"
                                                                 size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal"
                                                                 class="org-input" value="${orgNameTHDVB}"/>
                                            <input type="hidden" name="orgIdTHDVB" id="orgIdTHDVB"
                                                   value="${orgIdTHDVB}">

                                            <span
                                                id="remove-org-search-THDVB"
                                            >
                                                &times;
                                            </span>

                                            <b>Tài liệu</b> <input type="text" name="docNameTHDVB" id="docNameTHDVB"
                                                                   size="30" autocomplete="off"
                                                                   data-toggle="modal" data-target="#docSearchModal"
                                                                   class="doc-input" value="${docNameTHDVB}"/>
                                            <input type="hidden" name="docIdTHDVB" id="docIdTHDVB"
                                                   value="${docIdTHDVB}">

                                                    <span
                                                        id="remove-doc-search-THDVB"
                                                    >
                                                &times;
                                            </span>

                                            <b>From</b> <input type="text" name="dbegin" id="dbegin" size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend" size="15"
                                                             readonly="readonly" value="${dendFilter}"/>
                                            <b>User</b>

                                            <input list="user" name="user" value="${userFilter}" autocomplete="off"/>
                                            <datalist name="" id="user" style="width: 125px;" data-placeholder="&nbsp;">

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

                                            </datalist>
                                            <button type="submit" class="searchButton btn btn-primary"
                                                    id="filter-input-THDVB" style="padding: 0px 15px !important;">
                                                <span class="fa fa-search"></span>&nbsp;Filter
                                            </button>
                                            <input type="hidden" name="action_" id="action-input-THDVB"
                                                   value="Filter-THDVB">

                                            <input type="hidden" name="type_report" id="type-report-THDVB">
                                            <button type="submit" class="btn btn-primary" id="export-btn-THDVB-DOC"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-THDVB-XLS"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;XLS
                                            </button>
                                            <button type="button" class="btn btn-danger" id="reset-btn-THDVB-XLS"
                                                    style="padding: 0px 15px !important;">
                                                &nbsp;Reset
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
                                            <fmt:formatNumber type="number" maxFractionDigits="1"
                                                              value="${act.totalTimeView/60000}"/>
                                        </td>
                                        <td>${act.author}</td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.timeUpload}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
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

                                    <td align="left" colspan="9">
                                        <form action="ReportExport" style="width: 100vw" id="KQTT-form">

                                            <b>Đơn vị</b> <input type="text" name="orgNameKQTT" id="orgNameKQTT"
                                                                 size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal"
                                                                 class="org-input"/>
                                            <input type="hidden" name="orgIdKQTT" id="orgIdKQTT">

                                            <span
                                                id="remove-org-search-KQTT"
                                            >
                                                &times;
                                            </span>

                                            <b>Tài liệu</b> <input type="text" name="docNameKQTT" id="docNameKQTT"
                                                                   size="30" autocomplete="off"
                                                                   data-toggle="modal" data-target="#docSearchModal"
                                                                   class="doc-input"/>
                                            <input type="hidden" name="docIdKQTT" id="docIdKQTT">

                                            <span
                                                id="remove-doc-search-KQTT"
                                            >
                                                &times;
                                            </span>

                                            <b>From</b> <input type="text" name="dbegin" id="dbegin-KQTT" size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend-KQTT" size="15"
                                                             readonly="readonly" value="${dendFilter}"/>

                                            <b>User</b>
                                            <select name="user" id="user-KQTT" style="width: 125px;"
                                                    data-placeholder="&nbsp;">
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
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary"
                                                   id="filter-input-KQTT"/>

                                            <input type="hidden" name="action_" id="action-input-KQTT"
                                                   value="Filter-KQTT">

                                            <input type="hidden" name="type_report" id="type-report-KQTT">
                                            <button type="submit" class="btn btn-primary" id="export-btn-KQTT-DOC"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-KQTT-XLS"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;XLS
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
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.assignDoc}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.confirmDate}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.startConfirm}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
                                        <td nowrap="nowrap"><fmt:formatDate value="${act.endConfirm}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
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
                                    <td align="left" colspan="9">
                                        <form action="ReportExport" style="width: 100vw">
                                            <b>Tài liệu</b> <input type="text" name="docNameCLVB" id="docNameCLVB"
                                                                   size="30" autocomplete="off"
                                                                   data-toggle="modal" data-target="#docSearchModal"
                                                                   class="doc-input" value="${docNameCLVB}"/>
                                            <input type="hidden" name="docIdCLVB" id="docIdCLVB" value="${docIdCLVB}">

                                            <span
                                                id="remove-doc-search-CLVB"
                                            >
                                                &times;
                                            </span>

                                            <b>From</b> <input type="text" name="dbegin" id="dbegin-CLVB" size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend-CLVB" size="15"
                                                             readonly="readonly" value="${dendFilter}"/>
<<<<<<< HEAD
                                                <%--<b>User</b>
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
                                                </select>--%>
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary"
                                                   id="filter-input-CLVB"/>
                                            <input type="hidden" name="action_" id="action-input-CLVB">
                                            <input type="hidden" name="type_report" id="type-report-CLVB">
                                            <button type="submit" class="btn btn-primary" id="export-btn-CLVB-DOC"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-CLVB-XLS"
                                                    style="padding: 0px 15px !important;">
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

                <div id="THCNVB" class="${tab=='THCNVB' ? 'tab-pane fade in active' : 'tab-pane fade'}">
                    <div class="card">
                        <div class="card-body">
                            <table id="results-THCNVB" class="results">
                                <thead>
                                <tr class="header">
                                    <td align="left" colspan="9">
                                        <form action="ReportExport" style="width: 100vw">
                                                <%--<input class="form-control" name="orgParent" id="orgParent" placeholder="Tìm đơn vị cha"
                                                       type="text" autocomplete="off" data-toggle="modal" data-target="#myModal">--%>
                                            <b>Đơn vị</b> <input type="text" name="orgNameTHCNVB" id="orgNameTHCNVB"
                                                                 size="20" autocomplete="off"
                                                                 data-toggle="modal" data-target="#myModal"
                                                                 class="org-input" value="${orgNameTHCNVB}"/>
                                            <input type="hidden" name="orgIdTHCNVB" id="orgIdTHCNVB"
                                                   value="${orgIdTHCNVB}">
                                            <b>Tài liệu</b> <input type="text" name="docNameTHCNVB" id="docNameTHCNVB"
                                                                   size="30" autocomplete="off"
                                                                   data-toggle="modal" data-target="#docSearchModal"
                                                                   class="doc-input" value="${docNameTHCNVB}"/>
                                            <input type="hidden" name="docIdTHCNVB" id="docIdTHCNVB"
                                                   value="${docIdTHCNVB}">
                                            <b>From</b> <input type="text" name="dbegin" id="dbegin-THCNVB" size="15"
                                                               readonly="readonly" value="${dbeginFilter}"/>
                                            <b>To</b> <input type="text" name="dend" id="dend-THCNVB" size="15"
                                                             readonly="readonly" value="${dendFilter}"/>
                                            <b>User</b>
                                            <select name="user" id="user-THCNVB" style="width: 125px;"
                                                    data-placeholder="&nbsp;">
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
                                            <input type="submit" value="Filter" class="searchButton btn btn-primary"
                                                   id="filter-input-THCNVB"/>
                                            <input type="hidden" name="action_" id="action-input-THCNVB" value="THCNVB">
                                            <input type="hidden" name="type_report" id="type-report-THCNVB" />
                                            <button type="submit" class="btn btn-primary" id="export-btn-THCNVB-DOC"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;DOC
                                            </button>
                                            <button type="submit" class="btn btn-success" id="export-btn-THCNVB-XLS"
                                                    style="padding: 0px 15px !important;">
                                                <span class="fa fa-download"></span>&nbsp;XLS
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <th>Đơn vị</th>
                                    <th>Họ tên</th>
                                    <th>Mã nhân viên</th>
                                    <th>Tên văn bản</th>
                                    <th>Hành động</th>
                                    <th>Thời điểm tác động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="act" items="${resultsTHCNVB}" varStatus="row">
                                    <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>${act.orgName}</td>
                                        <td>${act.fullName}</td>
                                        <td>${act.employeeCode}</td>
                                        <td>${act.documentName}</td>
                                        <td>${act.action}</td>

                                        <td nowrap="nowrap"><fmt:formatDate value="${act.dateTime}"
                                                                            pattern="MM/dd/yyyy HH:mm"/></td>
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

                <div class="modal fade" id="docSearchModal">
                    <div class="modal-dialog modal-dialog-scrollable modal-lg ">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <form id="form-search-doc" class="row" style="width: 100%;">

                                    <div class="col-md-5">
                                        <input type="text" class="form-control" placeholder="Mã tài liệu"
                                               name="docCode">
                                    </div>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control" placeholder="Tên tài liệu"
                                               name="docName">
                                    </div>
                                    <div class="col-md-2">
                                        <button type="submit" class="btn btn-success" id="docSearchSubmitBtn">
                                            Tìm kiếm
                                        </button>
                                    </div>

                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                </form>
                            </div>
                            <!-- Modal body -->
                            <div class="modal-body">
                                <div class="row">
                                    <table class="table table-hover table-bordered" id="table-doc-search">
                                        <thead>
                                        <tr>
                                            <th>Mã tài liệu</th>
                                            <th>Tên tài liệu</th>
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

    </c:when>
    <c:otherwise>
        <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
</c:choose>
</body>
</html>
