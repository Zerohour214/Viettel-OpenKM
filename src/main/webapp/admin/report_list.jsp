<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="Shortcut icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" href="../css/dataTables-1.10.10/jquery.dataTables-1.10.10.min.css" />
<link rel="stylesheet" type="text/css" href="css/admin-style.css" />
  <link rel="stylesheet" type="text/css" href="../renew/css/layout-admin.css" />
  <link rel="stylesheet" type="text/css" href="../renew/fonts/glyphicons/glyphicons.css" />
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../js/jquery.dataTables-1.10.10.min.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $('#results').dataTable({
      "bStateSave" : true,
      "iDisplayLength" : 15,
      "lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
      "fnDrawCallback" : function(oSettings) {
        dataTableAddRows(this, oSettings);
      }
    });
  });
</script>
<title>Reports</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <ul id="breadcrumb">
        <li class="path"><a href="Report">Reports</a></li>
      </ul>
      <div style="width: 70%; margin-left: auto; margin-right: auto;">
        <br />
        <table id="results" class="results">
          <thead>
            <tr>
              <th>Name</th>
              <th>File Name</th>
              <th>Active</th>
              <th width="100px">
                <c:url value="Report" var="urlCreate">
                  <c:param name="action" value="create" />
                </c:url>
                <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="padding: 0 6px 0 4px; font-size: 15px; color: #27B45F" title="New report"></span></a>
                  <a href="#"><span class="glyphicons glyphicons-notes-2" style="padding: 0 6px 0 4px; font-size: 15px; color: #27B45F" title="Báo cáo "></span></a>
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="rp" items="${reports}" varStatus="row">
              <c:url value="Report" var="urlEdit">
                <c:param name="action" value="edit" />
                <c:param name="rp_id" value="${rp.id}" />
              </c:url>
              <c:url value="Report" var="urlDelete">
                <c:param name="action" value="delete" />
                <c:param name="rp_id" value="${rp.id}" />
              </c:url>
              <c:url value="Report" var="urlParams">
                <c:param name="action" value="paramList" />
                <c:param name="rp_id" value="${rp.id}" />
              </c:url>
              <c:url value="Report" var="urlGetParams">
                <c:param name="action" value="getParams" />
                <c:param name="rp_id" value="${rp.id}" />
              </c:url>
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td>${rp.name}</td>
                <td>${rp.fileName}</td>
                <td align="center"><c:choose>
                    <c:when test="${rp.active}">
                      <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="Inactive"></span>
                    </c:otherwise>
                  </c:choose></td>
                <td align="center">
                  <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a>
                  &nbsp;
                  <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a>
                  &nbsp;
                  <c:choose>
                    <c:when test="${rp.fileMime == 'application/x-report'}">
                      <a href="${urlParams}"><span class="glyphicons glyphicons-list-alt" style="font-size: 15px; color: #27B45F;" title="Parameters"></span></a>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-list-alt" style="font-size: 15px; color: #DBDBDB;" title="Parameters"></span>
                    </c:otherwise>
                  </c:choose>
                  &nbsp;
                  <a href="${urlGetParams}"><span class="glyphicons glyphicons-electricity" style="font-size: 15px; color: #27B45F;" title="Execute"></span></a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>
