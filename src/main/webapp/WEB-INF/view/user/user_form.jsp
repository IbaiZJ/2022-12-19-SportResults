<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
  <c:when test="${empty requestScope.user}">
    <c:set var="pageTitle" scope="request" value="createUser"/>
    <c:set var="action" scope="page" value="/user/create"/>
    <c:set var="username" scope="page" value=""/>
    <c:set var="firstName" scope="page" value=""/>
    <c:set var="secondName" scope="page" value=""/>
    <c:set var="email" scope="page" value=""/>
  </c:when>
  <c:otherwise>
    <c:set var="pageTitle" scope="request" value="editUser"/>
    <c:set var="action" scope="page" value="/user/${requestScope.user.userId}/edit"/>
    <c:set var="username" scope="page" value="${requestScope.user.username }"/>
    <c:set var="firstName" scope="page" value="${requestScope.user.firstName }"/>
    <c:set var="secondName" scope="page" value="${requestScope.user.secondName }"/>
    <c:set var="email" scope="page" value="${requestScope.user.email }"/>
  </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/includes/header.jsp"/>
<main class="centered-content">
  <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
  <form class="card" action="${action}" method="post">
    <h2 class="card-title"><fmt:message key="${requestScope.pageTitle}"/></h2>
    <div class="card-body">
      <label>
        <fmt:message key="username"/>:
        <input type="text" name="username" value="${username}" placeholder="<fmt:message key="username"/>"/>
      </label>
      <label>
        <fmt:message key="password"/>:
        <input type="password" name="password" placeholder="<fmt:message key="password"/>"/>
      </label>
      <label>
        <fmt:message key="firstName"/>:
        <input type="text" name="firstName" value="${firstName}" placeholder="<fmt:message key="firstName"/>"/>
      </label>
      <label>
        <fmt:message key="secondName"/>:
        <input type="text" name="secondName" value="${secondName}" placeholder="<fmt:message key="secondName"/>"/>
      </label>
      <label>
        <fmt:message key="email"/>:
        <input type="email" name="email" value="${email}" placeholder="<fmt:message key="email"/>"/>
      </label>
      <button type="submit"><fmt:message key="save"/></button>
    </div>
  </form>
  </fmt:bundle>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp"/>