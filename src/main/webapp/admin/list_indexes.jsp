<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/admin-style.css" />
  <link rel="stylesheet" type="text/css" href="../renew/css/layout-admin.css" />
  <link rel="stylesheet" type="text/css" href="../renew/fonts/glyphicons/glyphicons.css" />
  <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
  <title>List indexes</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isMultipleInstancesAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:url value="ListIndexes" var="urlActivate">
        <c:param name="id" value="${id}"/>
        <c:param name="showTerms" value="true"/>
      </c:url>
      <c:url value="ListIndexes" var="urlDeactivate">
        <c:param name="id" value="${id}"/>
        <c:param name="showTerms" value="false"/>
      </c:url>
      <ul id="breadcrumb">
        <li class="path">
          <a href="utilities.jsp">Utilities</a>
        </li>
        <li class="path">List indexes</li>
        <li class="action">
          <c:choose>
            <c:when test="${showTerms}">
              <a href="${urlDeactivate}">
                <span class="glyphicons glyphicons-light-beacon" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Disable"></span>
                Show terms
              </a>
            </c:when>
            <c:otherwise>
              <a href="${urlActivate}">
                <span class="glyphicons glyphicons-light-beacon" style="padding: 2px 0px 0 0; font-size: 12px; color: #DBDBDB" title="Enable"></span>
                Show terms
              </a>
            </c:otherwise>
          </c:choose>
        </li>
        <li class="action">
          <a href="ListIndexes?action=search">
            <span class="glyphicons glyphicons-search" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Search"></span>
            Search indexes
          </a>
        </li>
      </ul>
      <br/>
      <table class="results-old" width="60%">
        <thead>
          <tr class="fuzzy">
            <td colspan="2" align="right">
              Max: ${max}
              &nbsp;
              <c:choose>
                <c:when test="${id > 0}">
                  <a href="ListIndexes?id=0&showTerms=${showTerms}"><span class="glyphicons glyphicons-step-backward" style="font-size: 15px; color: #27B45F"></span></a>
                </c:when>
                <c:otherwise>
                  <span class="glyphicons glyphicons-step-backward" style="font-size: 15px; color: #DBDBDB"></span>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${prev}">
                  <a href="ListIndexes?id=${id - 1}&showTerms=${showTerms}"><span class="glyphicons glyphicons-play flip-horizontal-glyphicon" style="font-size: 15px; color: #27B45F"></span></a>
                </c:when>
                <c:otherwise>
                  <span class="glyphicons glyphicons-play flip-horizontal-glyphicon" style="font-size: 15px; color: #DBDBDB"></span>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${next}">
                  <a href="ListIndexes?id=${id + 1}&showTerms=${showTerms}"><span class="glyphicons glyphicons-play" style="font-size: 15px; color: #27B45F"></span></a>
                </c:when>
                <c:otherwise>
                <span class="glyphicons glyphicons-play" style="font-size: 15px; color: #DBDBDB"></span>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${id < max}">
                  <a href="ListIndexes?id=${max}&showTerms=${showTerms}"><span class="glyphicons glyphicons-step-forward" style="font-size: 15px; color: #27B45F"></span></a>
                </c:when>
                <c:otherwise>
                  <span class="glyphicons glyphicons-step-forward" style="font-size: 15px; color: #DBDBDB"></span>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr><th>Field</th><th>Value</th></tr>
        </thead>
        <tbody>
          <tr class="even">
            <td><b>#</b></td><td>${id}</td>
          </tr>
          <c:forEach var="fld" items="${fields}" varStatus="row">
            <tr class="${row.index % 2 == 0 ? 'odd' : 'even'}">
              <td width="150px"><b>${fld.name}</b></td>
              <td><u:escapeHtml string="${fld.value}"/></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>