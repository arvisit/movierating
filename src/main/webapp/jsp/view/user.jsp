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
        <title>${user.login}</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <table>
                <tr>
                    <td>
                        <img class="avatar" src="${user.avatar}" alt="${user.login}'s avatar"/>
                        <br>
                        <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                                || sessionScope.user.id == user.id)}">
                            <a class="edit" href="controller?command=edit_user_form&id=${user.id}"><fmt:message key="msg.view.user.edit"/></a>
                        </c:if>
                        <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN')}">
                            <a class="ban" href="controller?command=create_ban_form&id=${user.id}"><fmt:message key="msg.view.user.ban"/></a>
                        </c:if>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td colspan="2">
                                    <c:if test="${user.role == 'USER'}">
                                        <a href="controller?command=user_scores&id=${user.id}"><fmt:message key="msg.view.user.data.my_scores"/></a>
                                        <a href="controller?command=user_reviews&id=${user.id}"><fmt:message key="msg.view.user.data.my_reviews"/></a>
                                    </c:if>
                                    <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                                            || sessionScope.user.id == user.id)}">
                                        <a class="edit" href="controller?command=user_bans&id=${user.id}"><fmt:message key="msg.view.user.data.my_bans"/></a>
                                    </c:if>
                                    <c:if test="${sessionScope.user != null && (sessionScope.user.role == 'ADMIN' && user.id == sessionScope.user.id)}">
                                        <a class="edit" href="controller?command=assigned_bans&id=${user.id}"><fmt:message key="msg.view.user.data.assigned_bans"/></a>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.user.data.login"/></b>
                                </td>
                                <td>
                                    <c:out value="${user.login}"/>
                                </td>
                            </tr>
                            <c:if test="${sessionScope.user != null && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                                    || sessionScope.user.id == user.id)}">
                                <tr>
                                    <td>
                                        <b><fmt:message key="msg.view.user.data.email"/></b>
                                    </td>
                                    <td>
                                        <c:out value="${user.email}"/>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.user.data.reputation"/></b>
                                </td>
                                <td>
                                    ${user.reputation}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.user.data.registration"/></b>
                                </td>
                                <td>
                                    ${user.registration}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.user.data.role"/></b>
                                </td>
                                <td>
                                    <c:if test="${user.role == 'ADMIN'}">
                                        <fmt:message key="msg.view.user.data.role.admin"/>
                                    </c:if>
                                    <c:if test="${user.role == 'USER'}">
                                        <fmt:message key="msg.view.user.data.role.user"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <b><fmt:message key="msg.view.user.data.info"/></b>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <c:out value="${user.info}"/>
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