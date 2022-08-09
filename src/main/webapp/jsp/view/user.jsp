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
            <p><b>Login:</b> <c:out value="${user.login}"/></p>
            <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                    || sessionScope.user.id == user.id)}">
                <p><b>Email:</b> <c:out value="${user.email}"/></p>
            </c:if>
            <p><b>Reputation:</b> ${user.reputation}</p>
            <p><b>Registration:</b> ${user.registration}</p>
            <p><b>Role:</b> ${user.role}</p>
            <p><b>Info:</b> <c:out value="${user.info}"/></p>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>