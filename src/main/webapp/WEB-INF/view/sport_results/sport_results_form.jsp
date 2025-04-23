<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
  <c:when test="${empty requestScope.sportResult}">
    <!-- Sortu -->
    <c:set var="pageTitle" scope="request" value="Sortu" />
    <c:set var="action" scope="page" value="/sportResult/create" />
    <c:set var="team1Name" scope="page" value="" />
    <c:set var="team1Result" scope="page" value="" />
    <c:set var="team2Name" scope="page" value="" />
    <c:set var="team2Result" scope="page" value="" />
  </c:when>
  <c:otherwise>
    <!-- Editatu -->
    <c:set var="pageTitle" scope="request" value="Editatu" />
    <c:set var="action" scope="page" value="/sportResult/${requestScope.sportResult.sportResultId}/edit" />
    <c:set var="team1Name" scope="page" value="${requestScope.sportResult.team1Name}" />
    <c:set var="team1Result" scope="page" value="${requestScope.sportResult.team1Result}" />
    <c:set var="team2Name" scope="page" value="${requestScope.sportResult.team2Name}" />
    <c:set var="team2Result" scope="page" value="${requestScope.sportResult.team2Result}" />
  </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/includes/header.jsp" />
<main class="centered-content">
  <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
    <form class="card" action="${action}" method="post">
      <h2 class="card-title">${pageTitle}</h2>
      <div class="card-body">
        <label>
          1st team's name:
          <input type="text" name="team1Name" value="${team1Name}" placeholder="1st team's name" />
        </label>
        <label>
          1st team's result:
          <input type="number" name="team1Result" value="${team1Result}" placeholder="1st team's result" />
        </label>
        <label>
          2nd team's name:
          <input type="text" name="team2Name" value="${team2Name}" placeholder="2nd team's name" />
        </label>
        <label>
          2nd team's result:
          <input type="number" name="team2Result" value="${team2Result}" placeholder="2nd team's result" />
        </label>
        <button type="submit"><fmt:message key="save" /></button>
      </div>
    </form>
  </fmt:bundle>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp" />
