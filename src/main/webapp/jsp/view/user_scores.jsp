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
        <title><fmt:message key="msg.view.user_scores.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${scores.size() == 0}">
                    <h2><fmt:message key="msg.view.user_scores.no_scores"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.user_scores.scores_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                            <th><fmt:message key="msg.view.user_scores.table_header.score"/></th>
                            <th><fmt:message key="msg.view.user_scores.table_header.film"/></th>
                            <th><fmt:message key="msg.view.user_scores.table_header.date"/></th>
                        </tr>
                        <c:forEach items="${scores}" var="score">
                            <tr>
                                <td><c:out value="${score.value}"/></td>
                                <td>
                                    <a href="controller?command=film&id=${score.film.id}"><c:out value="${score.film.title}"/></a>
                                </td>
                                <td>${score.publicationDate}</td>
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