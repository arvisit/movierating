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
        <title>${requestScope.title}</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${bans.size() == 0}">
                    <h2><fmt:message key="msg.view.bans.no_bans"/></h2>
                </c:when>
                <c:otherwise>
                    <h2>${requestScope.title}</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${bans}" var="ban">
                            <tr>
                                <td>
                                    <c:if test="${sessionScope.user.role == 'ADMIN' }">
                                        <b><fmt:message key="msg.view.bans.user"/> </b>
                                        <a href="controller?command=user&id=${ban.user.id}">
                                            <c:out value="${ban.user.login}"/>
                                        </a>
                                        <br>
                                    </c:if>
                                    <b><fmt:message key="msg.view.bans.start_date"/> </b>${ban.startDate}
                                    <br>
                                    <b><fmt:message key="msg.view.bans.end_date"/> </b>${ban.endDate}
                                    <br>
                                    <b><fmt:message key="msg.view.bans.assigned"/> </b>
                                    <a href="controller?command=user&id=${ban.admin.id}">
                                        <c:out value="${ban.admin.login}"/>
                                    </a>
                                    <br>
                                    <b><fmt:message key="msg.view.bans.reason"/> </b><c:out value="${ban.reason}"/>
                                    <br>
                                    <a href="controller?command=edit_ban_form&id=${ban.id}"><fmt:message key="msg.view.bans.edit"/></a>
                                </td>
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