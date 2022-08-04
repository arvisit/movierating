<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="notification">
    <c:if test="${sessionScope.user != null}">
        <p class="loginMessage">Hello, ${sessionScope.user.login}</p>
    </c:if>
    <c:if test="${successMessage != null}">
        <p class="successMessage">${successMessage}</p>
    </c:if>
    <c:if test="${errorMessage != null}">
        <p class="errorMessage">${errorMessage}</p>
    </c:if>
</div>