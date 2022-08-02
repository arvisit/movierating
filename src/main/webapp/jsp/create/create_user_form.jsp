<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sign up</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>Register new user</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="create_user"/>
                <label for="login">Login:</label>
                <input required="required" type="text" min="5" id="login" name="login" pattern="[A-Za-z0-9_]{4,100}"
                        title="Use latin letters, underscore, decimal digits"/>
                <br>
                <label for="email">Email:</label>
                <input required="required" type="email" id="email" name="email"/>
                <br>
                <label for="password">Password:</label>
                <input required="required" pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,32}" type="password" 
                        minlength="8" maxlength="32" id="password" name="password"
                        title="Use at least one upper case, one lower case character and one decimal digit"/>
                <br>
                <label for="confirmedPassword">Confirm password:</label>
                <input required="required" pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,32}" type="password" 
                        minlength="8" maxlength="32" id="confirmedPassword" name="confirmedPassword"
                        title="Use at least one upper case, one lower case character and one decimal digit"/>
                <br>
                <input type="submit" value="Register"/>
            </form>
            <c:if test="${errorMessage != null}">
                <p class="errorMessage">${errorMessage}</p>
            </c:if>
        </div>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>