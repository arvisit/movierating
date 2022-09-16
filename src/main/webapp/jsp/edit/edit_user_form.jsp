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
            var jsDefaultAvatar = "${requestScope.defaultAvatar}";
        </script>
        <script type="text/javascript" src="jsscript/image.js">
        </script>
        <title><fmt:message key="msg.edit_user_form.title"/></title>
    </head>
    <body>
        <%@ include file="/jsp/common/header.jsp"%>
        <%@ include file="/jsp/common/menu.jsp"%>
        <div class="main">
            <h2><fmt:message key="msg.edit_user_form.edit_user_header"/></h2>
            <form method="post" action="controller" enctype="multipart/form-data">
                <input name="command" type="hidden" value="edit_user"/>
                <input name="id" type="hidden" value="${requestScope.user.id}"/>
                <input id="avatarForm" name="avatarForm" type="hidden" value="${requestScope.user.avatar}"/>
                <table class="list-center">
                    <tr>
                        <td>
                            <img class="avatar" id="avatarImg" src="${requestScope.user.avatar}" 
                                    alt="${requestScope.user.login}'s avatar"/>
                            <br>
                            <a href="controller?command=user&id=${requestScope.user.id}">
                                <c:out value="${requestScope.user.login}"/>
                            </a>
                            <c:if test="${sessionScope.user.id == requestScope.user.id}">
                                <br>
                                <input id="selectedImg" name="imgUploaded" class="imgUploaded" type="file" 
                                        accept=".jpg, .jpeg, .png" onchange="loadImageTo('avatarImg')"/>
                                <input type="button" value="<fmt:message key='msg.edit_user_form.form.button.browse.value'/>" 
                                        onclick="document.getElementById('selectedImg').click();"/>
                                <input type="button" value="<fmt:message key='msg.edit_user_form.form.button.delete.value'/>" 
                                        onclick="deleteImage('avatarForm', 'avatarImg', jsDefaultAvatar)"/>
                            </c:if>
                        </td>
                        <td>
                            <p><b><fmt:message key="msg.edit_user_form.registration"/></b> <c:out value="${requestScope.user.registration}"/></p>
                            <table>
                                <tr>
                                    <td>
                                        <label for="email"><fmt:message key="msg.edit_user_form.form.label.email"/></label>
                                    </td>
                                    <td>
                                        <input required="required" type="email" id="email" name="email" 
                                                value="${requestScope.user.email}" minlength="5" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label for="role"><fmt:message key="msg.edit_user_form.form.label.role"/></label>
                                    </td>
                                    <td>
                                        <select required="required" id="role" name="role" size="1">
                                            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                                <option ${(requestScope.user.role == 'ADMIN' ? 'selected' : '')} 
                                                    value="admin"><fmt:message key="msg.edit_user_form.form.select.admin"/></option>
                                                <option ${(requestScope.user.role == 'USER' ? 'selected' : '')} 
                                                    value="user"><fmt:message key="msg.edit_user_form.form.select.user"/></option>
                                            </c:if>
                                            <c:if test="${sessionScope.user.role != 'ADMIN'}">
                                                <option selected value="user"><fmt:message key="msg.edit_user_form.form.select.user"/></option>
                                            </c:if>
                                        </select>
                                    </td>
                                </tr>
                                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                    <tr>
                                        <td>
                                            <label for="reputation"><fmt:message key="msg.edit_user_form.form.label.reputation"/></label>
                                        </td>
                                        <td>
                                            <input required="required" type="number" id="reputation" name="reputation" 
                                                min="-32768" max="32767" value="${requestScope.user.reputation}"/>
                                        </td>
                                        <c:if test="${sessionScope.user.id != requestScope.user.id}">
                                            <input name="info" type="hidden" value="${requestScope.user.info}"/>
                                        </c:if>
                                    </tr>
                                </c:if>
                                <c:if test="${sessionScope.user.id == requestScope.user.id}">
                                    <input name="reputation" type="hidden" value="${requestScope.user.reputation}"/>
                                    <tr>
                                        <td>
                                            <label for="info"><fmt:message key="msg.edit_user_form.form.label.info"/></label>
                                        </td>
                                        <td>
                                            <textarea id="info" name="info">${requestScope.user.info}</textarea>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="<fmt:message key='msg.edit_user_form.form.button.save.value'/>"/>
            </form>
        </div>
        <%@ include file="/jsp/common/notification.jsp"%>
        <%@ include file="/jsp/common/footer.jsp"%>
    </body>
</html>