<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <title>Edit user</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The User:</h2>
            <p><b>Login:</b> ${requestScope.user.login}</p>
            <p><b>Email:</b> ${requestScope.user.email}</p>
            <p><b>Registration:</b> ${requestScope.user.registration}</p>
            <h2>Edit user:</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="edit_user"/>
                <input name="id" type="hidden" value="${requestScope.user.id}"/>
                <label for="role">Role:</label>
                <select required="required" id="role" name="role" size="1">
                    <c:choose>
                        <c:when test="${requestScope.user.role.toString().equals('ADMIN')}">
                            <option selected="selected" value="admin">Admin</option>
                            <option value="user">User</option>
                        </c:when>
                        <c:otherwise>
                            <option value="admin">Admin</option>
                            <option selected="selected" value="user">User</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <br>
                <label for="reputation">Reputation:</label>
                <input required="required" type="number" id="reputation" name="reputation" min="-32768" max="32767" 
                        value="${requestScope.user.reputation}"/>
                <br>
                <label for="info">Info:</label>
                <textarea id="info" name="info">${requestScope.user.info}</textarea>
                <br>
                <input type="submit" value="Save changes"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>