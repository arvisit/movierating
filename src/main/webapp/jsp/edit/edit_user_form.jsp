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
            <c:choose>
                <c:when test="${(sessionScope.user.role == 'ADMIN' && requestScope.user.role == 'USER') 
                        || (sessionScope.user.id == requestScope.user.id)}">
                    <h2>The User:</h2>
                    <p><b>Login:</b> ${requestScope.user.login}</p>
                    <p><b>Registration:</b> ${requestScope.user.registration}</p>
                    <h2>Edit user:</h2>
                    <form method="post" action="controller">
                        <input name="command" type="hidden" value="edit_user"/>
                        <input name="id" type="hidden" value="${requestScope.user.id}"/>
                        <label for="email">Email:</label>
                        <input required="required" type="email" id="email" name="email" value="${requestScope.user.email}"
                                 minlength="5" maxlength="100"/>
                        <br>
                        <label for="role">Role:</label>
                        <select required="required" id="role" name="role" size="1">
                            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                <option ${(requestScope.user.role == 'ADMIN' ? 'selected' : '')} value="admin">Admin</option>
                                <option ${(requestScope.user.role == 'USER' ? 'selected' : '')} value="user">User</option>
                            </c:if>
                            <c:if test="${sessionScope.user.role != 'ADMIN'}">
                                <option selected value="user">User</option>
                            </c:if>
                        </select>
                        <br>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <label for="reputation">Reputation:</label>
                            <input required="required" type="number" id="reputation" name="reputation" min="-32768" max="32767" 
                                    value="${requestScope.user.reputation}"/>
                            <c:if test="${sessionScope.user.id != requestScope.user.id}">
                                <input name="info" type="hidden" value="${requestScope.user.info}"/>
                            </c:if>
                            <br>
                        </c:if>
                        <c:if test="${sessionScope.user.id == requestScope.user.id}">
                            <input name="reputation" type="hidden" value="${requestScope.user.reputation}"/>
                            <label for="info">Info:</label>
                            <textarea id="info" name="info">${requestScope.user.info}</textarea>
                            <br>
                        </c:if>
                        <input type="submit" value="Save changes"/>
                    </form>
                </c:when>
                <c:otherwise>
                    You have no rights for this edit operation
                </c:otherwise>
            </c:choose>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>