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
        <title>${film.title}, ${film.releaseYear}</title>
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
                        <br>
                        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
                            <a class="edit" href="controller?command=edit_film_form&id=${film.id}"><fmt:message key="msg.view.film.edit"/></a>
                        </c:if>
                        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'USER'}">
                            <a class="edit" href="controller?command=create_score_form&id=${film.id}"><fmt:message key="msg.view.film.score"/></a>
                            <a class="edit" href="controller?command=create_review_form&id=${film.id}"><fmt:message key="msg.view.film.review"/></a>
                        </c:if>
                        
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td colspan="2">
                                    <a href="controller?command=film_scores&id=${film.id}"><fmt:message key="msg.view.film.all_scores"/></a>
                                    <a href="controller?command=film_reviews&id=${film.id}"><fmt:message key="msg.view.film.all_reviews"/></a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.film.data.title"/></b>
                                </td>
                                <td>
                                    <c:out value="${film.title}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.film.data.release_year"/></b>
                                </td>
                                <td>
                                    <c:out value="${film.releaseYear}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.film.data.length"/></b>
                                </td>
                                <td>
                                    <c:out value="${film.length}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.film.data.age_rating"/></b>
                                </td>
                                <td>
                                    ${film.ageRating.name}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b><fmt:message key="msg.view.film.data.average_score"/></b>
                                </td>
                                <td>
                                    ${film.averageScore}
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <b><fmt:message key="msg.view.film.data.description"/></b>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <c:out value="${film.description}"/>
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