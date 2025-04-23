<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="pageTitle" scope="request" value="error.${sessionScope.errorCode}.title" />
<jsp:include page="/WEB-INF/includes/header.jsp" />

<main class="centered-content">
  <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
    <div class="card">
      <h2 class="card-title">
        <fmt:message key="error.${sessionScope.errorCode}.title" />
      </h2>
      <div class="card-body">
        <p>
          <fmt:message key="error.${sessionScope.errorCode}.message" />
        </p>
      </div>
    </div>
  </fmt:bundle>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp" />