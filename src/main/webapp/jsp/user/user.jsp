<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>User</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The User</h2>
            <p><b>Login:</b> ${user.login}</p>
            <p><b>Registration:</b> ${user.registration}</p>
            <p><b>Role:</b> ${user.role}</p>
            <p><b>Info:</b> ${user.info}</p>
            <p><b>Reputation:</b> ${user.reputation}</p>
        </div>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>