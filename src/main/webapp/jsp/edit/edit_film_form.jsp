<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <script>
            var jsDefaultPoster = "${requestScope.defaultPoster}";
        </script>
        <script type="text/javascript" src="jsscript/image.js">
        </script>
        <title>Edit film</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>Edit film:</h2>
            <p><img class="poster" id="posterImg" src="${requestScope.film.poster}" alt="${requestScope.film.title}'s poster"/></p>
            <form method="post" action="controller" enctype="multipart/form-data">
                <input name="command" type="hidden" value="edit_film"/>
                <input name="id" type="hidden" value="${requestScope.film.id}"/>
                <input id="posterForm" name="posterForm" type="hidden" value="${requestScope.film.poster}"/>
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
                <input id="selectedImg" name="imgUploaded" class="imgUploaded" type="file" accept=".jpg, .jpeg, .png" onchange="loadImageTo('posterImg')"/>
                <input type="button" value="Browse..." onclick="document.getElementById('selectedImg').click();"/>
                <input type="button" onclick="deleteImage('posterForm', 'posterImg', jsDefaultPoster)" value="Delete"/>
                <br>
                <input type="submit" value="Save changes"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>