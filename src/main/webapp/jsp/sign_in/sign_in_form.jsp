<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Sign in</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>Sign in:</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="sign_in"/>
                <label for="login">Login:</label>
                <input required="required" type="text" maxlength="100" id="login" name="login"/>
                <br>
                <label for="password">Password:</label>
                <input required="required" type="password" maxlength="32" id="password" name="password"/>
                <br>
                <input type="submit" value="Sign in"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>