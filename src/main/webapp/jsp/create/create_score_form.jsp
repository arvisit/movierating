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
        <title><fmt:message key="msg.create_score_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <table>
                <tr>
                    <td>
                        <img class="poster" src="${requestScope.film.poster}" alt="${requestScope.film.title}'s poster"/>
                    </td>
                    <td>
                        <b><c:out value="${requestScope.film.title}"/></b>
                        <br>
                        <br>
                        <c:out value="${requestScope.film.description}"/>
                    </td>
                </tr>
            </table>
            <h2><fmt:message key="msg.create_score_form.score_header"/></h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="create_score"/>
                <input name="id" type="hidden" value="${requestScope.film.id}"/>
                <label for="value"><fmt:message key="msg.create_score_form.form.label.score"/></label>
                <input required="required" type="number" id="value" name="value" min="1" max="10" value="1"/>
                <br>
                <input type="submit" value="<fmt:message key='msg.create_score_form.form.button.score.value'/>"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>