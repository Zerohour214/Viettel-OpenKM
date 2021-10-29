<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.extension.servlet.admin.DocumentExpirationServlet" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="isMultipleInstancesAdmin"><%=BaseServlet.isMultipleInstancesAdmin(request)%></c:set>
<c:set var="isRepositoryNative"><%=Config.REPOSITORY_NATIVE%></c:set>
<c:set var="isDocumentExpiration"><%=DocumentExpirationServlet.isDocumentExpiration()%></c:set>
<!-- http://stackoverflow.com/questions/1708054/center-ul-li-into-div -->
<div style="text-align: center">
  <ul style="background: #F3F6F9; height: 44px;">
  <ul style="display: inline-block; float: left;" class="icon-admin-menu-focus-renew">
    <li>
      <a target="frame" href="home.jsp" title="Home">
        <span class="glyphicons glyphicons-home icon-admin-menu-renew"></span>
      </a>
    </li>
    <c:if test="${isMultipleInstancesAdmin}">
      <li>
        <a target="frame" href="Config" title="Configuration">
          <span class="glyphicons glyphicons-wrench icon-admin-menu-renew"></span>
        </a>
      </li>
    </c:if>
    <li>
      <a target="frame" href="MimeType" title="Mime types">
        <span class="glyphicons glyphicons-message-full icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="stats.jsp" title="Statistics">
        <span class="glyphicons glyphicons-stats icon-admin-menu-renew"></span>
      </a>
    </li>
    <c:if test="${isMultipleInstancesAdmin}">
<%--      <li>--%>
<%--        <a target="frame" href="Scripting" title="Scripting">--%>
<%--          <span class="glyphicons glyphicons-embed-close icon-admin-menu-renew"></span>--%>
<%--        </a>--%>
<%--      </li>--%>
      <c:if test="${!isRepositoryNative}">
        <li>
          <a target="frame" href="RepositorySearch" title="Repository search">
            <span class="glyphicons glyphicons-search icon-admin-menu-renew"></span>
          </a>
        </li>
      </c:if>
    </c:if>
    <li>
      <a target="frame" href="PropertyGroups" title="Metadata">
        <span class="glyphicons glyphicons-database icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="Auth" title="Users">
        <span class="glyphicons glyphicons-group icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="Profile"  title="Profiles">
        <span class="glyphicons glyphicons-user-key icon-admin-menu-renew"></span>
      </a>
    </li>
    <c:if test="${isMultipleInstancesAdmin}">
      <li>
        <a target="frame" href="DatabaseQuery" title="Database query">
          <span class="glyphicons glyphicons-database-search icon-admin-menu-renew"></span>
        </a>
      </li>
    </c:if>
    <li>
      <a target="frame" href="Report" title="Reports">
        <span class="glyphicons glyphicons-notes-2 icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="ActivityLog" title="Log">
        <span class="glyphicons glyphicons-important-day icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="Workflow" title="Workflow">
        <span class="glyphicons glyphicons-cogwheels icon-admin-menu-renew"></span>
      </a>
    </li>
    <li>
      <a target="frame" href="Automation" title="Automation">
        <span class="glyphicons glyphicons-robot icon-admin-menu-renew"></span>
      </a>
    </li>
    <c:if test="${isDocumentExpiration}">
      <li>
        <a target="frame" href="DocumentExpiration" title="Document expiration">
          <span class="glyphicons glyphicons-hourglass icon-admin-menu-renew"></span>
        </a>
      </li>
    </c:if>
    <c:if test="${isMultipleInstancesAdmin}">
      <li>
        <a target="frame" href="CronTab" title="Crontab">
          <span class="glyphicons glyphicons-clock icon-admin-menu-renew"></span>
        </a>
      </li>
      <li>
        <a target="frame" href="Omr" title="OMR">
          <span class="glyphicons glyphicons-list-numbered icon-admin-menu-renew"></span>
        </a>
      </li>
      <li>
        <a target="frame" href="generate_thesaurus.jsp" title="Thesaurus">
          <span class="glyphicons glyphicons-book icon-admin-menu-renew"></span>
        </a>
      </li>
      <li>
        <a target="frame" href="Language" title="Language">
          <span class="glyphicons glyphicons-globe-af icon-admin-menu-renew"></span>
        </a>
      </li>
<%--      <li>--%>
<%--        <a target="frame" href="Repository?action=import" title="Import">--%>
<%--          <span class="glyphicons glyphicons-inbox-in icon-admin-menu-renew"></span>--%>
<%--        </a>--%>
<%--      </li>--%>
<%--      <li>--%>
<%--        <a target="frame" href="Repository?action=export" title="Export">--%>
<%--          <span class="glyphicons glyphicons-inbox-out icon-admin-menu-renew"></span>--%>
<%--         </a>--%>
<%--      </li>--%>
      <li>
        <a target="frame" href="utilities.jsp" title="Utilities">
          <span class="glyphicons glyphicons-list icon-admin-menu-renew"></span>
         </a>
      </li>
      <li>
        <a target="frame" href="organization_vtx.jsp" title="Organization">
          <span class="glyphicons glyphicons-tree-conifer icon-admin-menu-renew"></span>
        </a>
      </li>
    </c:if>
    <c:if test="${isMultipleInstancesAdmin}">
      <li>
        <a target="frame" href="experimental.jsp">&nbsp;</a>
      </li>
    </c:if>
  </ul>
  </ul>
    <script type="text/javascript">
      // Identify if being loaded inside an iframe
      if (self == top) {
         document.write('<li>\n');
         document.write('<a href="logout.jsp" title="Exit"><span class="glyphicons glyphicons-exit" style="color: #27B45F; font-size: 43px;"></span></a>\n');
         document.write('</li>\n');
      }
    </script>
</div>
