<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="../css/dataTables-1.10.10/jquery.dataTables-1.10.10.min.css" />
  <link rel="stylesheet" type="text/css" href="../css/chosen.css" />
  <link rel="stylesheet" type="text/css" href="css/admin-style.css" />
  <link rel="stylesheet" type="text/css" href="../renew/css/layout-admin.css" />
  <link rel="stylesheet" type="text/css" href="../renew/fonts/glyphicons/glyphicons.css" />
  <script type="text/javascript" src="../js/utils.js"></script>
  <script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
  <script type="text/javascript" src="../js/jquery.dataTables-1.10.10.min.js"></script>
  <script type="text/javascript" src="../js/chosen.jquery.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {
      $('select#roleFilter').chosen({disable_search_threshold: 10});

      $('#fumi').click(function(event) {
        $("#dest").removeClass('ok').removeClass('error').html('<b>Checking....</b>');
        $("#dest").load('MailAccount', {
          action : "checkAll"
        }, function(response, status, xhr) {
          if (response == 'Success!') {
            $(this).removeClass('error').addClass('ok');
          } else {
            $(this).removeClass('ok').addClass('error');
          }
        });
      });

      $('#results').dataTable({
        "bStateSave" : true,
        "iDisplayLength" : 15,
        "lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
        "fnDrawCallback" : function(oSettings) {
          dataTableAddRows(this, oSettings);
        }
      });

      $("#resetButton").click(function() {
        $('#roleFilter option:eq(0)').prop('selected', true);
        $('#roleFilter').trigger("chosen:updated");
      });
    });
  </script>
  <title>User List</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%></c:set>
  <u:constantsMap className="com.openkm.core.Config" var="Config"/>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:url var="messageList" value="LoggedUsers">
      	<c:param name="action" value="messageList"></c:param>
      </c:url>
      <ul id="breadcrumb">
        <li class="path">
          <a href="Auth">User list</a>
        </li>
        <li class="action">
          <a href="Auth?action=roleList">
            <span class="glyphicons glyphicons-cogwheel" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Generic"></span>
            Role list
          </a>
        </li>
        <li class="action">
          <a href="${messageList}">
            <span class="glyphicons glyphicons-cogwheel" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Generic"></span>
            Message queue
          </a>
        </li>
        <li class="action">
          <a href="LoggedUsers">
            <span class="glyphicons glyphicons-cogwheel" style="padding: 2px 0px 0 0; font-size: 12px; color: #27B45F" title="Generic"></span>
            Logged users
          </a>
        </li>
      </ul>
      <br/>
      <div style="width: 90%; margin-left: auto; margin-right: auto;">
        <table id="results" class="results">
          <thead>
            <tr class="header">
              <td align="right" colspan="9">
                <c:url value="Auth" var="urlUserList">
                  <c:param name="action" value="userList"/>
                </c:url>
                <form action="${urlUserList}">
                  <input type="hidden" name="action" value="userList" />
                  <b>Role</b>
                  <select name="roleFilter" id="roleFilter" style="width: 250px" data-placeholder="&nbsp;">
                    <option value="">All</option>
                    <c:forEach var="role" items="${roles}">
                      <c:choose>
                        <c:when test="${role.id == roleFilter}">
                          <option value="${role.id}" selected="selected">${role.id}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${role.id}">${role.id}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                  <input type="button" value="Reset" class="resetButton btn btn-danger" id="resetButton" />
                  <input type="submit" value="Filter" class="searchButton btn btn-primary" />
                </form>
              </td>
            </tr>
            <tr>
              <th width="25px">#</th>
              <th>Id</th>
              <th>Name</th>
              <th>Mail</th>
              <th>Roles</th>
              <th>Profile</th>
              <th width="25px">Active</th>
              <th width="25px">Chat</th>
              <th width="185px">
                <c:url value="Auth" var="urlCreate">
                  <c:param name="action" value="userCreate" />
                </c:url>
                <c:url value="Auth" var="urlExport">
                  <c:param name="action" value="userListExport" />
                </c:url>
                <c:if test="${db}">
                  <a href="${urlCreate}"><span class="glyphicons glyphicons-plus-sign" style="padding: 0 6px 0 4px; font-size: 15px; color: #27B45F" title="New user"></span></a>
		          &nbsp;
		        </c:if>
                <a href="${urlExport}"><span class="glyphicons glyphicons-file-export" style="padding: 0 6px 0 0px; font-size: 15px; color: #27B45F" title="CSV export"></span></a>
              </th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="user" items="${users}" varStatus="row">
              <c:url value="Auth" var="urlEdit">
                <c:param name="action" value="userEdit" />
                <c:param name="usr_id" value="${user.id}" />
              </c:url>
              <c:url value="Auth" var="urlDelete">
                <c:param name="action" value="userDelete" />
                <c:param name="usr_id" value="${user.id}" />
              </c:url>
              <c:url value="Auth" var="urlActive">
                <c:param name="action" value="userActive" />
                <c:param name="usr_id" value="${user.id}" />
                <c:param name="roleFilter" value="${roleFilter}" />
                <c:param name="usr_active" value="${!user.active}" />
              </c:url>
              <c:url value="Auth" var="urlChatDisconnect">
                <c:param name="action" value="userChatDisconnect" />
                <c:param name="usr_id" value="${user.id}" />
                <c:param name="roleFilter" value="${roleFilter}" />
                <c:param name="usr_active" value="${!user.active}" />
              </c:url>
              <c:url value="UserConfig" var="urlConfig">
                <c:param name="uc_user" value="${user.id}" />
              </c:url>
              <c:url value="MailAccount" var="urlMail">
                <c:param name="ma_user" value="${user.id}" />
              </c:url>
              <c:url value="TwitterAccount" var="urlTwitter">
                <c:param name="ta_user" value="${user.id}" />
              </c:url>
              <c:url value="ActivityLog" var="urlLog">
                <c:param name="user" value="${user.id}" />
                <c:param name="dbegin" value="${date}" />
                <c:param name="dend" value="${date}" />
              </c:url>
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td width="20px">${row.index + 1}</td>
                <td>${user.id}</td>
                <td>${fn:escapeXml(user.name)}</td>
                <td>${user.email}</td>
                <td><c:forEach var="role" items="${user.roles}">
	                  ${role.id}
	                </c:forEach></td>
                <td>${user.profile}</td>
                <td align="center">
                  <c:if test="${multInstAdmin || user.id != Config.ADMIN_USER}">
                    <c:choose>
                      <c:when test="${db}">
                        <c:choose>
                          <c:when test="${user.active}">
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
                  </c:if>
                </td>
                <td align="center">
                  <c:choose>
                    <c:when test="${u:contains(chatUsers, user.id)}">
                      <a href="${urlChatDisconnect}"><span class="glyphicons glyphicons-user-conversation" style="font-size: 15px; color: #27B45F;" title="Disconnect user"></span></a>
                    </c:when>
                    <c:otherwise>
                      <span class="glyphicons glyphicons-user-ban" style="font-size: 15px; color: red;" title="User disconnected"></span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td align="center">
                  <c:if test="${multInstAdmin || user.id != Config.ADMIN_USER}">
                    <c:if test="${db}">
                      <a href="${urlEdit}"><span class="glyphicons glyphicons-pen" style="font-size: 15px; color: #27B45F;" title="Edit"></span></a>
	                    &nbsp;
	                    <a href="${urlDelete}"><span class="glyphicons glyphicons-bin" style="font-size: 15px; color: red;" title="Delete"></span></a>
	                    &nbsp;
	                  </c:if>
                      <a href="${urlConfig}"><span class="glyphicons glyphicons-wrench" style="font-size: 15px; color: #27B45F;" title="User config"></span></a>
	                  &nbsp;
	                  <a href="${urlMail}"><span class="glyphicons glyphicons-envelope" style="font-size: 15px; color: #27B45F;" title="Mail accounts"></span></a>
	                  &nbsp;
	                  <a href="${urlTwitter}"><span class="glyphicons glyphicons-comments" style="font-size: 15px; color: #27B45F;" title="Twitter accounts"></span></a>
	                  &nbsp;
	                  <a href="${urlLog}"><span class="glyphicons glyphicons-calendar" style="font-size: 15px; color: #27B45F;" title="Activity log"></span></a>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
          </tbody>
          <tfoot>
            <tr class="foot">
              <td align="right" colspan="9">
                <div style="text-align: right;" id="dest">
                  <input type="button" id="fumi" value="Force user mail import" class="executeButton btn btn-success" />
                </div>
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
