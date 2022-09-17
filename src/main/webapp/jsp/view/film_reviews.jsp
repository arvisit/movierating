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
        <title><fmt:message key="msg.view.film_reviews.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <table>
                <tr>
                    <td>
                        <img class="poster" id="posterImg" src="${requestScope.film.poster}" 
                                alt="${requestScope.film.title}'s poster"/>
                    </td>
                    <td>
                        <a href="controller?command=film&id=${requestScope.film.id}"><b><c:out value="${requestScope.film.title}"/></b></a>, ${requestScope.film.releaseYear}
                    </td>
                </tr>
            </table>
            <c:choose>
                <c:when test="${reviews.size() == 0}">
                    <h2><fmt:message key="msg.view.film_reviews.no_reviews"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.film_reviews.reviews_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${reviews}" var="review">
                            <tr>
                                <td rowspan="2">
                                    <img class="avatar" id="avatarImg" src="${review.user.avatar}" 
                                            alt="${review.user.login}'s avatar"/>
                                    <br>
                                    <a href="controller?command=user&id=${review.user.id}"><c:out value="${review.user.login}"/></a>
                                </td>
                                <td>${review.publicationDate}</td>
                            </tr>
                            <tr>
                                <td>
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