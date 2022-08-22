<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Score film</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>The Film:</h2>
            <p><b>Title:</b> <c:out value="${requestScope.film.title}"/></p>
            <p><b>Description:</b> <c:out value="${requestScope.film.description}"/></p>
            <h2>Score film:</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="create_score"/>
                <input name="id" type="hidden" value="${requestScope.film.id}"/>
                <label for="value">Score value:</label>
                <input required="required" type="number" id="value" name="value" min="1" max="10" value="1"/>
                <br>
                <input type="submit" value="Score"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>