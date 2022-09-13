<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<div class="menu">
    <c:if test="${sessionScope.user != null}">
        <div class="loginMessage"><fmt:message key="msg.menu.hello"/> <c:out value="${sessionScope.user.login}"/></div>
    </c:if>
    <div class="menu-right">
        <form method="get" action="controller">
            <input name="command" type="hidden" value="search_film"/>
            <input required="required" name="title" type="search" minlength="1" maxlength="100" placeholder="<fmt:message key='msg.menu.search.placeholder'/>"/>
        </form>
        <a href="."><fmt:message key="msg.menu.home"/></a>
        <a href="controller?command=films"><fmt:message key="msg.menu.films"/></a>
        <a href="controller?command=users"><fmt:message key="msg.menu.users"/></a>
        <c:if test="${sessionScope.user == null}">
            <a href="controller?command=sign_in_form"><fmt:message key="msg.menu.sign_in"/></a>
            <a href="controller?command=create_user_form"><fmt:message key="msg.menu.sign_up"/></a>
        </c:if>
        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
            <a href="controller?command=create_film_form"><fmt:message key="msg.menu.add_film"/></a>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.menu.my_profile"/></a>
            <a href="controller?command=sign_out"><fmt:message key="msg.menu.sign_out"/></a>
        </c:if>
    </div>
</div>