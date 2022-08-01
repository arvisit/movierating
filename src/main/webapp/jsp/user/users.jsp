<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
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
                    <table>
                        <tr>
                        <th>Id</th>
                        <th>Login</th>
                        <th>Role</th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><a href="controller?command=user&id=${user.id}">${user.id}</a></td>
                                <td>${user.login}</td>
                                <td>${user.role}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>