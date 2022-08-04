<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="menu">
    <ul>
        <li><a href=".">Home</a></li>
        <li><a href="controller?command=users">Users</a></li>
        <li><a href="controller?command=sign_in_form">Sign in</a></li>
        <c:if test="${sessionScope.user != null}">
            <li><a href="controller?command=sign_out">Sign out</a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li><a href="controller?command=create_user_form">Sign up</a></li>
        </c:if>
    </ul>
</div>