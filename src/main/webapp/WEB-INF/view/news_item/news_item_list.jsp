<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" scope="request" value="newsList" />
<jsp:include page="/WEB-INF/includes/header.jsp" />
<main class="news-grid">
	<fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
		<c:forEach items="${requestScope.news}" var="newsItem">
			<!--div class="column"-->
				<div class="card">
					<h2 class="card-title">
						<a href="/news/${newsItem.newsItemId}">
							<c:out value="${newsItem.title}" />
						</a>
					</h2>
					<div class="card-body">
						<div class="news-item-body">
							<c:out value="${newsItem.body}" escapeXml="false" />
						</div>
					</div>
					<footer class="card-footer">
						<span class="author">
							<b>
								<fmt:message key="author" />:</b>
							<i><a href="/user/${newsItem.author.userId}/view">
									<c:out value="${newsItem.author.username}" />
								</a></i>
						</span>
						<span class="creationDate">
							<b>
								<fmt:message key="creationDate" />:</b>
							<i>
								<c:out value="${newsItem.date}" /></i>
						</span>
						<span class="language">
							<b>
								<fmt:message key="language" />:</b>
							<i>
								<fmt:message key="language.${newsItem.lang.language}" /></i>
						</span>
						<span class="actions">
							<c:if test="${newsItem.author.userId==sessionScope.user.userId}">
								<a class="button" href="/news/${newsItem.newsItemId}/delete">
									<fmt:message key="delete" />
								</a>
								<a class="button" href="/news/${newsItem.newsItemId}/edit">
									<fmt:message key="edit" />
								</a>
							</c:if>
						</span>
					</footer>
				</div>
			<!--/div-->
		</c:forEach>
	</fmt:bundle>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp" />