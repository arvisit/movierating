<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.language != null}">
    <fmt:setLocale value="${sessionScope.language}"/>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title><fmt:message key="msg.create_user_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2><fmt:message key="msg.create_user_form.register_header"/></h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="create_user"/>
                <table class="list-center">
                    <tr>
                        <td>
                            <label for="login"><fmt:message key="msg.create_user_form.form.label.login"/></label>
                        </td>
                        <td>
                            <input required="required" type="text" minlength="4" maxlength="100" id="login" name="login"
                                    pattern="[A-Za-z0-9_]{4,100}" 
                                    title="<fmt:message key='msg.create_user_form.form.input.login.help'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="email"><fmt:message key="msg.create_user_form.form.label.email"/></label>
                        </td>
                        <td>
                            <input required="required" type="email" id="email" name="email" minlength="5" maxlength="100"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="password"><fmt:message key="msg.create_user_form.form.label.password"/></label>
                        </td>
                        <td>
                            <input required="required" pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,32}" 
                                    type="password" minlength="8" maxlength="32" id="password" name="password"
                                    title="<fmt:message key='msg.create_user_form.form.input.password.help'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="confirmedPassword">
                                <fmt:message key="msg.create_user_form.form.label.confirmed_password"/>
                            </label>
                        </td>
                        <td>
                            <input required="required" pattern="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,32}" 
                                    type="password" minlength="8" maxlength="32" id="confirmedPassword" 
                                    name="confirmedPassword" 
                                    title="<fmt:message key='msg.create_user_form.form.input.confirmed_password.help'/>"/>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="<fmt:message key='msg.create_user_form.form.button.register.value'/>"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>