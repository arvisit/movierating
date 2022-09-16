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
        <title><fmt:message key="msg.edit_ban_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <table class="list-center">
                <tr>
                    <td>
                        <img class="avatar" src="${ban.user.avatar}" alt="${ban.user.login}'s avatar"/>
                        <br>
                        <a href="controller?command=user&id=${ban.user.id}">
                            <c:out value="${ban.user.login}"/>
                        </a>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.edit_ban_form.ban.start_date"/> </b>${ban.startDate}
                                    <br>
                                    <b><fmt:message key="msg.edit_ban_form.ban.end_date"/> </b>${ban.endDate}
                                    <br>
                                    <b><fmt:message key="msg.edit_ban_form.ban.reason"/> </b>${ban.reason}
                                    <br>
                                    <form method="post" action="controller">
                                        <input name="command" type="hidden" value="edit_ban"/>
                                        <input name="id" type="hidden" value="${requestScope.ban.id}"/>
                                        <input name="start_date" type="hidden" value="${requestScope.ban.startDate}"/>
                                        <input type="checkbox" id="unban" name="unban" value="true">
                                        <label for="unban">
                                            <fmt:message key="msg.edit_ban_form.form.label.unban"/>
                                        </label>
                                        <br>
                                        <label for="duration">
                                            <fmt:message key="msg.edit_ban_form.form.label.duration"/>
                                        </label>
                                        <input type="number" id="duration" name="duration" min="1" max="31" 
                                                value="${requestScope.duration}"/>
                                        <br>
                                        <input type="submit" 
                                                value="<fmt:message key='msg.edit_ban_form.form.button.apply.value'/>"/>
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