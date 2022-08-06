<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
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
                    <table>
                        <tr>
                        <th>Login</th>
                        <th>Role</th>
                        <th>Reputation</th>
                        <th>Edit</th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><a href="controller?command=user&id=${user.id}">${user.login}</a></td>
                                <td>${user.role}</td>
                                <td>${user.reputation}</td>
                                <td><a href="controller?command=edit_user_form&id=${user.id}">Edit</a></td>
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