<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Ban user</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The User:</h2>
            <p><b>Login:</b> <c:out value="${requestScope.user.login}"/></p>
            <h2>Ban user:</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="ban_user"/>
                <input name="id" type="hidden" value="${requestScope.user.id}"/>
                <label for="duration">Duration (days):</label>
                <input required="required" type="number" id="duration" name="duration" min="1" max="31" value="1"/>
                <br>
                <label for="reason">Reason:</label>
                <textarea required="required" id="reason" name="reason"></textarea>
                <br>
                <input type="submit" value="Apply"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>