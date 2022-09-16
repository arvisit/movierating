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
        <title><fmt:message key="msg.create_ban_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <table class="list-center">
                <tr>
                    <td>
                        <img class="avatar" src="${requestScope.user.avatar}" alt="${requestScope.user.login}'s avatar"/>
                        <br>
                        <a href="controller?command=user&id=${requestScope.user.id}">
                            <c:out value="${requestScope.user.login}"/>
                        </a>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <form method="post" action="controller">
                                        <input name="command" type="hidden" value="create_ban"/>
                                        <input name="id" type="hidden" value="${requestScope.user.id}"/>
                                        <label for="duration">
                                            <fmt:message key="msg.create_ban_form.form.label.duration"/>
                                        </label>
                                        <input required="required" type="number" id="duration" name="duration" 
                                                min="1" max="31" value="1"/>
                                        <br>
                                        <label for="reason">
                                            <fmt:message key="msg.create_ban_form.form.label.reason"/>
                                        </label>
                                        <textarea required="required" id="reason" name="reason"></textarea>
                                        <br>
                                        <input type="submit" 
                                                value="<fmt:message key='msg.create_ban_form.form.button.value'/>"/>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>