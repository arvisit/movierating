<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Edit ban</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The User:</h2>
            <p><b>Login:</b> <c:out value="${ban.user.login}"/></p>
            <h2>Edit ban:</h2>
            <b>Start date: </b>${ban.startDate}
            <br>
            <b>End date: </b>${ban.endDate}
            <br>
            <b>Reason: </b>${ban.reason}
            <br>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="edit_ban"/>
                <input name="id" type="hidden" value="${requestScope.ban.id}"/>
                <input name="start_date" type="hidden" value="${requestScope.ban.startDate}"/>
                <input type="checkbox" id="unban" name="unban" value="true">
                <label for="unban">Unban</label>
                <br>
                <label for="duration">Duration (days):</label>
                <input type="number" id="duration" name="duration" min="1" max="31" value="${requestScope.duration}"/>
                <br>
                <input type="submit" value="Apply"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>