<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u"%>
<u:constantsMap className="com.openkm.core.Config" var="Config" />
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
<title>Automation rules</title>
</head>
<body>
  <c:choose>
    <c:when test="${u:isAdmin()}">
      <ul id="breadcrumb">
        <li class="path"><a href="Automation">Automation rules</a></li>
        <li class="action">
          <a href="Automation?action=registeredList"> 
            <span class="glyphicons glyphicons-cogwheel" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Generic"></span>
            Registered list
          </a>
        </li>
      </ul>
      <br />
      <div style="width: 70%; margin-left: auto; margin-right: auto;">
        <table id="results" class="results">
          <thead>
            <tr>
              <th>#</th>
              <th>Order</th>
              <th>Name</th>
              <th>Event</th>
              <th>At</th>
              <th>Validations</th>
              <th>Actions</th>
              <th>Exclusive</th>
              <th>Active</th>
              <th width="80px">
                <c:url value="Automation" var="urlCreate">
                  <c:param name="action" value="create" />
                </c:url> 
                <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="font-size: 15px; color: #27B45F" title="New rule"></span></a>
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="ar" items="${automationRules}" varStatus="row">
              <c:url value="Automation" var="urlEdit">
                <c:param name="action" value="edit" />
                <c:param name="ar_id" value="${ar.id}" />
              </c:url>
              <c:url value="Automation" var="urlDelete">
                <c:param name="action" value="delete" />
                <c:param name="ar_id" value="${ar.id}" />
              </c:url>
              <c:url value="Automation" var="urlDefinition">
                <c:param name="action" value="definitionList" />
                <c:param name="ar_id" value="${ar.id}" />
              </c:url>
              <c:url value="Automation" var="urlAction">
                <c:param name="action" value="actionList" />
                <c:param name="ar_id" value="${ar.id}" />
              </c:url>
              <c:url value="Automation" var="urlViewDefinition">
                <c:param name="action" value="viewDefinition" />
                <c:param name="ar_id" value="${ar.id}" />
              </c:url>
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td width="20px">${row.index + 1}</td>
                <td>${ar.order}</td>
                <td>${ar.name}</td>
                <td>${events.get(ar.event)}</td>
                <td>${ar.at}</td>
                <td align="center">${ar.validations.size()}</td>
                <td align="center">${ar.actions.size()}</td>
                <td align="center">
                  <c:choose>
                    <c:when test="${ar.exclusive}">
                      <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="Inactive"></span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td align="center">
                  <c:choose>
                    <c:when test="${ar.active}">
                      <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Active"></span>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="Inactive"></span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td width="80px" align="center">
                  <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a> &nbsp; 
                  <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a> &nbsp; 
                  <a href="${urlDefinition}"><span class="glyphicons glyphicons-filter" style="font-size: 15px; color: #27B45F;" title="Definition"></span></a>
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