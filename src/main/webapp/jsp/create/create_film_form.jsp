<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Add film</title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2>Add new film</h2>
            <form method="post" action="controller" enctype="multipart/form-data">
                <input name="command" type="hidden" value="create_film"/>
                <label for="title">Title:</label>
                <input required="required" type="text" id="title" name="title"/>
                <br>
                <label for="description">Description:</label>
                <textarea required="required" id="description" name="description"></textarea>
                <br>
                <label for="releaseYear">Release year:</label>
                <input required="required" type="number" id="releaseYear" name="releaseYear" min="1" max="32767" 
                        value="1"/>
                <br>
                <label for="length">Length:</label>
                <input required="required" type="number" id="length" name="length" min="1" max="32767" 
                        value="1"/>
                <br>
                <label for="ageRating">Age rating:</label>
                <select required="required" id="ageRating" name="ageRating" size="1">
                    <option selected value="G">G</option>
                    <option value="PG">PG</option>
                    <option value="PG-13">PG-13</option>
                    <option value="R">R</option>
                    <option value="NC-17">NC-17</option>
                </select>
                <br>
                <label for="poster">Choose image to upload</label>
                <input id="poster" name="poster" type="file" accept=".jpg, .jpeg, .png"/>
                <br>
                <input type="submit" value="Add"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>