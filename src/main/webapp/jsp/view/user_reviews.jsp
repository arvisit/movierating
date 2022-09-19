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
        <title><fmt:message key="msg.view.user_reviews.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${reviews.size() == 0}">
                    <h2><fmt:message key="msg.view.user_reviews.no_reviews"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.user_reviews.reviews_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${reviews}" var="review">
                            <tr>
                                <td>
                                    <a href="controller?command=film&id=${review.film.id}"><c:out value="${review.film.title}"/></a>
                                </td>
                                <td>${review.publicationDate}</td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <c:out value="${review.content}"/>
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