<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  $(document).ready(function () {
      $('#results').dataTable({
        "bStateSave": true,
        "iDisplayLength": 10,
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "fnDrawCallback": function (oSettings) {
          dataTableAddRows(this, oSettings);
        }
      });
    });
  </script>
  <title>Role list</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <ul id="breadcrumb">
        <li class="path">
          <a href="Auth?action=roleList">Role list</a>
        </li>
        <li class="action">
          <a href="Auth">
            <span class="glyphicons glyphicons-cogwheel" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Generic"></span>
            User list
          </a>
        </li>
      </ul>
      <br/>
      <div style="width: 40%; margin-left: auto; margin-right: auto;">
        <table id="results" class="results">
          <thead>
            <tr>
              <th>#</th>
              <th>Id</th>
              <th width="25px">Active</th>
              <th width="75px"><c:url value="Auth" var="urlCreate">
                  <c:param name="action" value="roleCreate" />
                </c:url> <c:url value="Auth" var="urlExport">
                  <c:param name="action" value="roleListExport" />
                </c:url> 
                <c:if test="${db}">
                  <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="font-size: 15px; color: #27B45F" title="New role"></span></a>
	              &nbsp;
	            </c:if> 
                <a href="${urlExport}"><span class="glyphicons glyphicons-file-export" style="font-size: 15px; color: #27B45F" title="CSV export"></span></a>
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="role" items="${roles}" varStatus="row">
              <c:url value="Auth" var="urlEdit">
                <c:param name="action" value="roleEdit" />
                <c:param name="rol_id" value="${role.id}" />
              </c:url>
              <c:url value="Auth" var="urlDelete">
                <c:param name="action" value="roleDelete" />
                <c:param name="rol_id" value="${role.id}" />
              </c:url>
              <c:url value="Auth" var="urlActive">
                <c:param name="action" value="roleActive" />
                <c:param name="rol_id" value="${role.id}" />
                <c:param name="rol_active" value="${!role.active}" />
              </c:url>
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td width="20px">${row.index + 1}</td>
                <td>${role.id}</td>
                <td align="center">
                  <c:choose>
                    <c:when test="${db}">
                      <c:choose>
                        <c:when test="${role.active}">
                          <a href="${urlActive}"><span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span></a>
                        </c:when>
                        <c:otherwise>
                          <a href="${urlActive}"><span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="Inactive"></span></a>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td align="center">
                  <c:if test="${db}">
                    <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a>
	                &nbsp;
	                <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a>
                  </c:if>
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