<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.servlet.admin.BaseServlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
      "iDisplayLength" : 10,
      "lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
      "fnDrawCallback" : function(oSettings) {
        dataTableAddRows(this, oSettings);
      }
    });
  });
</script>
<title>User Profiles</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <ul id="breadcrumb">
        <li class="path"><a href="Profile">User profiles</a></li>
      </ul>
      <br />
      <div style="width: 40%; margin-left: auto; margin-right: auto;">
        <table id="results" class="results">
          <thead>
            <tr>
              <th>Name</th>
              <th width="25px">Active</th>
              <th width="75px">
                <c:url value="Profile" var="urlCreate">
                  <c:param name="action" value="create" />
                </c:url> 
                <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="padding: 0 6px 0 4px; font-size: 15px; color: #27B45F" title="New profile"></span></a>
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="prf" items="${userProfiles}" varStatus="row">
              <c:url value="Profile" var="urlEdit">
                <c:param name="action" value="edit" />
                <c:param name="prf_id" value="${prf.id}" />
              </c:url>
              <c:url value="Profile" var="urlDelete">
                <c:param name="action" value="delete" />
                <c:param name="prf_id" value="${prf.id}" />
              </c:url>
              <c:url value="Profile" var="urlClone">
                <c:param name="action" value="clone" />
                <c:param name="prf_id" value="${prf.id}" />
              </c:url>
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td>${prf.name}</td>
                <td align="center">
                  <c:choose>
                    <c:when test="${prf.active}">
                      <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="Inactive"></span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td align="center">
                  <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a> 
                  <c:if test="${prf.id != 1}">
                    &nbsp; <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a>
                  </c:if> 
                  &nbsp; <a href="${urlClone}"><span class="glyphicons glyphicons-duplicate" style="font-size: 15px; color: #27B45F;" title="Clone"></span></a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:when>
    <c:otherwise>
      <div class="error">
        <h3>Only admin users allowed</h3>
      </div>
    </c:otherwise>
  </c:choose>
</body>
</html>