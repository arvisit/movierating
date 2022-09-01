<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Reviews</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${reviews.size() == 0}">
                    <h2>Film has no reviews yet</h2>
                </c:when>
                <c:otherwise>
                    <h2>Reviews</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${reviews}" var="review">
                            <tr>
                                <td>
                                    <a href="controller?command=user&id=${review.user.id}"><c:out value="${review.user.login}"/></a>
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