<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Films</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${films.size() == 0}">
                    <h2>There is no films yet</h2>
                </c:when>
                <c:otherwise>
                    <h2>Films</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                        <th>Poster</th>
                        <th>Title</th>
                        <th>Release year</th>
                        <th>Age rating</th>
                        <th>Average score</th>
                        </tr>
                        <c:forEach items="${films}" var="film">
                            <tr>
                                <td><img class="poster" src="${film.poster}" alt="${film.title}'s poster"/></td>
                                <td>
                                    <a href="controller?command=film&id=${film.id}"><c:out value="${film.title}"/></a>
                                    <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
                                        <br>
                                        <a class="edit" href="controller?command=edit_film_form&id=${film.id}">(edit)</a>
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