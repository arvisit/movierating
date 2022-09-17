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
        <title><fmt:message key="msg.view.film_scores.title"/></title>
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
                <c:when test="${scores.size() == 0}">
                    <h2><fmt:message key="msg.view.film_scores.no_scores"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="msg.view.film_scores.scores_header"/></h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${scores}" var="score">
                            <tr>
                                <td rowspan="2">
                                    <img class="avatar" id="avatarImg" src="${score.user.avatar}" 
                                            alt="${score.user.login}'s avatar"/>
                                    <br>
                                    <a href="controller?command=user&id=${score.user.id}"><c:out value="${score.user.login}"/></a>
                                </td>
                                <td>${score.publicationDate}</td>
                            </tr>
                            <tr>
                                <td>
                                    <b><c:out value="${score.value}"/></b>
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