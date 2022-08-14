<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>${requestScope.title}</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <c:choose>
                <c:when test="${bans.size() == 0}">
                    <h2>There is no bans yet</h2>
                </c:when>
                <c:otherwise>
                    <h2>${requestScope.title}</h2>
                    <%@ include file="/jsp/common/pagination.jsp" %>
                    <table class="list-center">
                        <c:forEach items="${bans}" var="ban">
                            <tr>
                                <td>
                                    <c:if test="${sessionScope.user.role == 'ADMIN' }">
                                        <b>User: </b>
                                        <a href="controller?command=user&id=${ban.user.id}">
                                            <c:out value="${ban.user.login}"/>
                                        </a>
                                        <br>
                                    </c:if>
                                    <b>Start date: </b>${ban.startDate}
                                    <br>
                                    <b>End date: </b>${ban.endDate}
                                    <br>
                                    <b>Assigned by: </b>
                                    <a href="controller?command=user&id=${ban.admin.id}">
                                        <c:out value="${ban.admin.login}"/>
                                    </a>
                                    <br>
                                    <b>Reason: </b><c:out value="${ban.reason}"/>
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