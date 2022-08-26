<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>User</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The User</h2>
            <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                    || sessionScope.user.id == user.id)}">
                <a class="edit" href="controller?command=edit_user_form&id=${user.id}">(edit)</a>
            </c:if>
            <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN')}">
                <a class="ban" href="controller?command=create_ban_form&id=${user.id}">(ban)</a>
            </c:if>
            <p><b>Login:</b> <c:out value="${user.login}"/></p>
            <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                    || sessionScope.user.id == user.id)}">
                <p><b>Email:</b> <c:out value="${user.email}"/></p>
                <a class="edit" href="controller?command=user_bans&id=${user.id}">My bans</a>
            </c:if>
            <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'ADMIN' && user.id == sessionScope.user.id)}">
                <a class="edit" href="controller?command=assigned_bans&id=${user.id}">Assigned bans</a>
            </c:if>
            <p><b>Reputation:</b> ${user.reputation}</p>
            <p><b>Registration:</b> ${user.registration}</p>
            <p><b>Role:</b> ${user.role}</p>
            <p><b>Info:</b> <c:out value="${user.info}"/></p>
            <c:if test="${user.role == 'USER'}">
                <p><a href="controller?command=user_scores&id=${user.id}">My scores</a></p>
            </c:if>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>