<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Users</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${users.size() == 0}">
                    <h2>There is no users yet</h2>
                </c:when>
                <c:otherwise>
                    <h2>Users</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                        <th>Avatar</th>
                        <th>Login</th>
                        <th>Role</th>
                        <th>Reputation</th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><img class="avatar" src="${user.avatar}" alt="${user.login}'s avatar"/></td>
                                <td>
                                    <a href="controller?command=user&id=${user.id}"><c:out value="${user.login}"/></a>
                                    <c:if test="${sessionScope.user != null 
                                            && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                                            || sessionScope.user.id == user.id)}">
                                        <br>
                                        <a class="edit" href="controller?command=edit_user_form&id=${user.id}">(edit)</a>
                                    </c:if>
                                    <c:if test="${sessionScope.user != null
                                             && (sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN')}">
                                        <br>
                                        <a class="ban" href="controller?command=create_ban_form&id=${user.id}">(ban)</a>
                                    </c:if>
                                </td>
                                <td>${user.role}</td>
                                <td>${user.reputation}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>