<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <title><fmt:message key="msg.sign_in_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2><fmt:message key="msg.sign_in_form.sign_in_header"/></h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="sign_in"/>
                <label for="login"><fmt:message key="msg.sign_in_form.form.label.login"/></label>
                <input required="required" type="text" maxlength="100" id="login" name="login"/>
                <br>
                <label for="password"><fmt:message key="msg.sign_in_form.form.label.password"/></label>
                <input required="required" type="password" maxlength="32" id="password" name="password"/>
                <br>
                <input type="submit" value="<fmt:message key='msg.sign_in_form.form.button.value'/>"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>