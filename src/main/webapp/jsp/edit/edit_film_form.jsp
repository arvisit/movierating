<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Edit film</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>Edit film:</h2>
            <form method="post" action="controller">
                <input name="command" type="hidden" value="edit_film"/>
                <input name="id" type="hidden" value="${requestScope.film.id}"/>
                <label for="title">Title:</label>
                <input required="required" type="text" id="title" name="title" value="${requestScope.film.title}"/>
                <br>
                <label for="description">Description:</label>
                <textarea required="required" id="description" name="description">${requestScope.film.description}</textarea>
                <br>
                <label for="releaseYear">Release year:</label>
                <input required="required" type="number" id="releaseYear" name="releaseYear" min="1" max="32767" 
                        value="${requestScope.film.releaseYear}"/>
                <br>
                <label for="length">Length:</label>
                <input required="required" type="number" id="length" name="length" min="1" max="32767" 
                        value="${requestScope.film.length}"/>
                <br>
                <label for="ageRating">Age rating:</label>
                <select required="required" id="ageRating" name="ageRating" size="1">
                    <option ${(requestScope.film.ageRating.name == 'G' ? 'selected' : '')} value="G">G</option>
                    <option ${(requestScope.film.ageRating.name == 'PG' ? 'selected' : '')} value="PG">PG</option>
                    <option ${(requestScope.film.ageRating.name == 'PG-13' ? 'selected' : '')} value="PG-13">PG-13</option>
                    <option ${(requestScope.film.ageRating.name == 'R' ? 'selected' : '')} value="R">R</option>
                    <option ${(requestScope.film.ageRating.name == 'NC-17' ? 'selected' : '')} value="NC-17">NC-17</option>
                </select>
                <br>
                <input type="submit" value="Save changes"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>