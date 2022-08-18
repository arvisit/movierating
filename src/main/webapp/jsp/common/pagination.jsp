<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pagination">
    <c:if test="${requestScope.totalPages != 1}">
        <c:choose>
            <c:when test="${requestScope.currentPage != 1}">
                <a href="controller?command=${requestScope.paginatedJsp}&page=1">&laquo;</a>
                <a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.currentPage - 1}">&lsaquo;</a>
            </c:when>
            <c:otherwise>
                <a role="link" aria-disabled="true">&laquo;</a>
                <a role="link" aria-disabled="true">&lsaquo;</a>
            </c:otherwise>
        </c:choose>
        <a role="link" aria-disabled="true">Page ${requestScope.currentPage} / ${requestScope.totalPages}</a>
        <c:choose>
            <c:when test="${requestScope.currentPage != requestScope.totalPages}">
                <a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.currentPage + 1}">&rsaquo;</a>
                <a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.totalPages}">&raquo;</a>
            </c:when>
            <c:otherwise>
                <a role="link" aria-disabled="true">&rsaquo;</a>
                <a role="link" aria-disabled="true">&raquo;</a>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>