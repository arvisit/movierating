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
        <script>
            var jsDefaultPoster = "${requestScope.defaultPoster}";
        </script>
        <script type="text/javascript" src="jsscript/image.js">
        </script>
        <title><fmt:message key="msg.edit_film_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2><fmt:message key="msg.edit_film_form.edit_film_header"/></h2>
            <form method="post" action="controller" enctype="multipart/form-data">
                <input name="command" type="hidden" value="edit_film"/>
                <input name="id" type="hidden" value="${requestScope.film.id}"/>
                <input id="posterForm" name="posterForm" type="hidden" value="${requestScope.film.poster}"/>
                <table class="list-center">
                    <tr>
                        <td>
                            <img class="poster" id="posterImg" src="${requestScope.film.poster}" 
                                    alt="${requestScope.film.title}'s poster"/>
                            <br>
                            <input id="selectedImg" name="imgUploaded" class="imgUploaded" type="file" 
                                    accept=".jpg, .jpeg, .png" onchange="loadImageTo('posterImg')"/>
                            <input type="button" value="<fmt:message key='msg.edit_film_form.form.button.browse.value'/>" 
                                    onclick="document.getElementById('selectedImg').click();"/>
                            <input type="button" onclick="deleteImage('posterForm', 'posterImg', jsDefaultPoster)" 
                                    value="<fmt:message key='msg.edit_film_form.form.button.delete.value'/>"/>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <label for="title">
                                            <fmt:message key="msg.edit_film_form.form.label.title"/>
                                        </label>
                                    </td>
                                    <td>
                                        <input required="required" type="text" id="title" name="title" 
                                                value="${requestScope.film.title}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="description">
                                            <fmt:message key="msg.edit_film_form.form.label.description"/>
                                        </label>
                                    </td>
                                    <td>
                                        <textarea required="required" id="description" name="description">${requestScope.film.description}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="releaseYear">
                                            <fmt:message key="msg.edit_film_form.form.label.release_year"/>
                                        </label>
                                    </td>
                                    <td>
                                        <input required="required" type="number" id="releaseYear" name="releaseYear" 
                                                min="1" max="32767" value="${requestScope.film.releaseYear}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="length">
                                            <fmt:message key="msg.edit_film_form.form.label.length"/>
                                        </label>
                                    </td>
                                    <td>
                                        <input required="required" type="number" id="length" name="length" min="1" 
                                                max="32767" value="${requestScope.film.length}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="ageRating">
                                            <fmt:message key="msg.edit_film_form.form.label.age_rating"/>
                                        </label>
                                    </td>
                                    <td>
                                        <select required="required" id="ageRating" name="ageRating" size="1">
                                            <option ${(requestScope.film.ageRating.name == 'G' ? 'selected' : '')} 
                                                    value="G">G</option>
                                            <option ${(requestScope.film.ageRating.name == 'PG' ? 'selected' : '')} 
                                                    value="PG">PG</option>
                                            <option ${(requestScope.film.ageRating.name == 'PG-13' ? 'selected' : '')} 
                                                    value="PG-13">PG-13</option>
                                            <option ${(requestScope.film.ageRating.name == 'R' ? 'selected' : '')} 
                                                    value="R">R</option>
                                            <option ${(requestScope.film.ageRating.name == 'NC-17' ? 'selected' : '')} 
                                                    value="NC-17">NC-17</option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="<fmt:message key='msg.edit_film_form.form.button.save.value'/>"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>