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
        <title><fmt:message key="msg.view.users.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${users.size() == 0}">
                    <h2><fmt:message key="msg.view.users.no_users"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.users.users_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                            <th><fmt:message key="msg.view.users.data.avatar"/></th>
                            <th><fmt:message key="msg.view.users.data.login"/></th>
                            <th><fmt:message key="msg.view.users.data.role"/></th>
                            <th><fmt:message key="msg.view.users.data.reputation"/></th>
                        </tr>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td><img class="avatar" src="${user.avatar}" alt="${user.login}'s avatar"/></td>
                                <td>
                                    <a href="controller?command=user&id=${user.id}"><c:out value="${user.login}"/></a>
                                    <c:if test="${sessionScope.user != null 
                                            && ((sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN') 
                                            || sessionScope.user.id == user.id)}">
                                        <br>
                                        <a class="edit" href="controller?command=edit_user_form&id=${user.id}"><fmt:message key="msg.view.users.edit"/></a>
                                    </c:if>
                                    <c:if test="${sessionScope.user != null
                                             && (sessionScope.user.role == 'ADMIN' && user.role != 'ADMIN')}">
                                        <br>
                                        <a class="ban" href="controller?command=create_ban_form&id=${user.id}"><fmt:message key="msg.view.users.ban"/></a>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${user.role == 'ADMIN'}">
                                        <fmt:message key="msg.view.users.data.role.admin"/>
                                    </c:if>
                                    <c:if test="${user.role == 'USER'}">
                                        <fmt:message key="msg.view.users.data.role.user"/>
                                    </c:if>
                                </td>
                                <td>${user.reputation}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                </c:otherwise>
            </c:choose>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>