<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" scope="request" value="newsItem"/>
<jsp:include page="/WEB-INF/includes/header.jsp"/>
<main class="centered-content">
	<div class="card">
		<fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
		<h2 class="card-title"><c:out value="${requestScope.newsItem.title}"/></h2>
		<div class="card-body">
			<div class="news-item-body">
				<c:out value="${requestScope.newsItem.body}" escapeXml="false"/>
			</div>
		</div>
		<footer class="card-footer">
			<span class="author">
				<b><fmt:message key="author"/>:</b>
				<i><a href="/user/${requestScope.newsItem.author.userId}/view">			
					<c:out value="${requestScope.newsItem.author.username}"/>
				</a></i>
			</span>
			<span class="creationDate">
				<b><fmt:message key="creationDate"/>:</b>
				<i>
					<!-- type="both" means date and time -->
					<fmt:formatDate value="${requestScope.newsItem.date}" type="both"></fmt:formatDate>
				</i>
			</span>
			<span class="language">
				<b><fmt:message key="language"/>:</b>
				<i><fmt:message key="language.${requestScope.newsItem.lang.language}"/></i>
			</span>
			<span class="actions">
				<c:if test="${requestScope.newsItem.author.userId==sessionScope.user.userId}">
					<a class="button" href="/news/${requestScope.newsItem.newsItemId}/delete">
						<fmt:message key="delete"/>
					</a>
					<a class="button" href="/news/${requestScope.newsItem.newsItemId}/edit">
						<fmt:message key="edit"/>
					</a>
				</c:if>
			</span>
		</footer>
		</fmt:bundle>
	
	</div>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp"/>