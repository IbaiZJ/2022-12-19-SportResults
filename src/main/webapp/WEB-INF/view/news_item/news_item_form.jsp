<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${empty requestScope.newsItem}">
		<c:set var="pageTitle" scope="request" value="createNewsItem"/>
		<c:set var="action" scope="page" value="/news/create"/>
		<c:set var="title" scope="page" value=""/>
		<c:set var="body" scope="page" value=""/>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle" scope="request" value="editNewsItem"/>
		<c:set var="action" scope="page" value="/news/${requestScope.newsItem.newsItemId}/edit"/>
		<c:set var="title" scope="page" value="${requestScope.newsItem.title }"/>
		<c:set var="body" scope="page" value="${requestScope.newsItem.body }"/>
	</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/includes/header.jsp"/>
<main class="centered-content">
	<fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
	<form class="card" action="${action}" method="post">
		<h2 class="card-title"><fmt:message key="${requestScope.pageTitle}"/></h2>
		<div class="card-body">
			<label>
				<fmt:message key="title"/>:
				<input	type="text"
						name="title"
						value="${title}"
						placeholder="<fmt:message key='title'/>" />
			</label>
			<label>
				<fmt:message key="body"/>:
				<textarea	rows="4"
							cols="50"
							name="body"
							placeholder="<fmt:message key="body"/>"
				><c:out value="${requestScope.newsItem.body}"/></textarea>
			</label>
			<button type="submit"><fmt:message key="save"/></button>
		</div>
	</form>
	</fmt:bundle>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp"/>