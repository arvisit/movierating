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
        <title><fmt:message key="msg.view.films.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${films.size() == 0}">
                    <c:choose>
                        <c:when test="${param.title != null}">
                            <h2><fmt:message key="msg.view.films.not_found"/> <i><c:out value="${param.title}"/></i></h2>
                        </c:when>
                        <c:otherwise>
                            <h2><fmt:message key="msg.view.films.no_films"/></h2>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.films.films_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                            <th><fmt:message key="msg.view.films.table_header.poster"/></th>
                            <th><fmt:message key="msg.view.films.table_header.title"/></th>
                            <th><fmt:message key="msg.view.films.table_header.release_year"/></th>
                            <th><fmt:message key="msg.view.films.table_header.age_rating"/></th>
                            <th><fmt:message key="msg.view.films.table_header.average_score"/></th>
                        </tr>
                        <c:forEach items="${films}" var="film">
                            <tr>
                                <td><img class="poster" src="${film.poster}" alt="${film.title}'s poster"/></td>
                                <td>
                                    <a href="controller?command=film&id=${film.id}"><c:out value="${film.title}"/></a>
                                    <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
                                        <br>
                                        <a class="edit" href="controller?command=edit_film_form&id=${film.id}"><fmt:message key="msg.view.films.edit"/></a>
                                    </c:if>
                                </td>
                                <td>${film.releaseYear}</td>
                                <td>${film.ageRating.name}</td>
                                <td>${film.averageScore}</td>
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