<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Film</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The Film</h2>
            <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
                <a class="edit" href="controller?command=edit_film_form&id=${film.id}">(edit)</a>
            </c:if>
            <p><b>Title:</b> <c:out value="${film.title}"/></p>
            <p><b>Description:</b> <c:out value="${film.description}"/></p>
            <p><b>Release year:</b> <c:out value="${film.releaseYear}"/></p>
            <p><b>Length:</b> <c:out value="${film.length}"/></p>
            <p><b>Age rating:</b> ${film.ageRating.name}</p>
            <p><b>Average score:</b> ${film.averageScore}</p>
            <p><a href="controller?command=film_scores&id=${film.id}">All scores</a></p>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>