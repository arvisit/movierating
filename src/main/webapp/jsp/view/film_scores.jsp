<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Scores</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${scores.size() == 0}">
                    <h2>Film has no scores yet</h2>
                </c:when>
                <c:otherwise>
                    <h2>Scores</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <tr>
                        <th>Score</th>
                        <th>User</th>
                        <th>Date</th>
                        </tr>
                        <c:forEach items="${scores}" var="score">
                            <tr>
                                <td><c:out value="${score.value}"/></td>
                                <td>
                                    <a href="controller?command=user&id=${score.user.id}"><c:out value="${score.user.login}"/></a>
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