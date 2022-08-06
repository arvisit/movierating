<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pagination">
    <c:if test="${requestScope.totalPages != 1}">
        <ul>
            <c:choose>
                <c:when test="${requestScope.currentPage != 1}">
                    <li><a href="controller?command=${requestScope.paginatedJsp}&page=1">&lt&lt</a></li>
                    <li><a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.currentPage - 1}">&lt</a></li>
                </c:when>
                <c:otherwise>
                    <li>&lt&lt</li>
                    <li>&lt</li>
                </c:otherwise>
            </c:choose>
            <li>Page ${requestScope.currentPage} / ${requestScope.totalPages}</li>
            <c:choose>
                <c:when test="${requestScope.currentPage != requestScope.totalPages}">
                    <li><a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.currentPage + 1}">&gt</a></li>
                    <li><a href="controller?command=${requestScope.paginatedJsp}&page=${requestScope.totalPages}">&gt&gt</a></li>
                </c:when>
                <c:otherwise>
                    <li>&gt</li>
                    <li>&gt&gt</li>
                </c:otherwise>
            </c:choose>
        </ul>
    </c:if>
</div>