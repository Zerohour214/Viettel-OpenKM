<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
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
        "iDisplayLength": 15,
        "lengthMenu": [[10, 15, 20], [10, 15, 20]],
        "fnDrawCallback": function (oSettings) {
          dataTableAddRows(this, oSettings);
        }
      });
    });
  </script>
  <title>Mime types</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <ul id="breadcrumb">
        <li class="path">
          <a href="MimeType">Mime types</a>
        </li>
      </ul>
      <br/>
      <div style="width:80%; margin-left:auto; margin-right:auto;">
	      <table id="results" class="results">
	       <thead>
	         <tr>
	           <th class= "table-first-header-renew">Name</th><th>Description</th><th>Image</th><th>Extensions</th><th>Search</th>
	           <th width="50px">
	             <c:url value="MimeType" var="urlCreate">
	               <c:param name="action" value="create"/>
	             </c:url>
	             <c:url value="MimeType" var="urlExport">
	               <c:param name="action" value="export"/>
	             </c:url>
	             <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="padding: 0 6px 0 0px; font-size: 15px; color: #27B45F" title="New mime type"></span></a>
	             <a href="${urlExport}"><span class="glyphicons glyphicons-file-export" style="padding: 0 6px 0 0px; font-size: 15px; color: #27B45F" title="SQL export"></span></a>
	           </th>
	         </tr>
	       </thead>
	       <tbody>
	         <c:forEach var="mt" items="${mimeTypes}" varStatus="row">
	           <c:url value="/mime/${mt.name}" var="urlIcon">
	           </c:url>
	           <c:url value="MimeType" var="urlEdit">
	             <c:param name="action" value="edit"/>
	             <c:param name="mt_id" value="${mt.id}"/>
	           </c:url>
	           <c:url value="MimeType" var="urlDelete">
	             <c:param name="action" value="delete"/>
	             <c:param name="mt_id" value="${mt.id}"/>
	           </c:url>
	           <tr>
	             <td>${fn:escapeXml(mt.name)}</td>
	             <td>${fn:escapeXml(mt.description)}</td>
	             <td align="center"><img src="${urlIcon}"/></td>
	             <td>${fn:escapeXml(mt.extensions)}</td>
	             <td align="center">
	               <c:choose>
	                 <c:when test="${mt.search}">
	                   <span class="glyphicons glyphicons-ok" style="font-size: 15px; color: #27B45F;" title="Search"></span>
	                 </c:when>
	                 <c:otherwise>
	                   <span class="glyphicons glyphicons-remove" style="font-size: 15px; color: red;" title="No Search"></span>
	                 </c:otherwise>
	               </c:choose>
	             </td>
	             <td width="50px" align="center">
	               <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a>
	               &nbsp;
	               <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a>
	             </td>
	           </tr>
	         </c:forEach>
	       </tbody>
	       <tfoot>
		       <tr>
		         <td align="right" colspan="6">
		           <form action="MimeType" method="post" enctype="multipart/form-data">
		             <input type="hidden" name="action" value="import"/>
	                 <input class=":required :only_on_blur" type="file" name="sql-file"/>
	                 <input type="submit" value="Import mime types" class="addButton btn btn-success"/>
		           </form>
		         </td>
		       </tr>
	       </tfoot>
	      </table>
      </div>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>
