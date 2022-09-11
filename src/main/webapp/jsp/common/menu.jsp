<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="menu">
    <c:if test="${sessionScope.user != null}">
        <div class="loginMessage">Hello, <c:out value="${sessionScope.user.login}"/></div>
    </c:if>
    <div class="menu-right">
        <form method="get" action="controller">
            <input name="command" type="hidden" value="search_film"/>
            <input name="title" type="search" minlength="1" maxlength="100" placeholder="Search film"/>
        </form>
        <a href=".">Home</a>
        <a href="controller?command=films">Films</a>
        <a href="controller?command=users">Users</a>
        <c:if test="${sessionScope.user == null}">
            <a href="controller?command=sign_in_form">Sign in</a>
            <a href="controller?command=create_user_form">Sign up</a>
        </c:if>
        <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
            <a href="controller?command=create_film_form">Add film</a>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <a href="controller?command=user&id=${sessionScope.user.id}">My Profile</a>
            <a href="controller?command=sign_out">Sign out</a>
        </c:if>
    </div>
</div>