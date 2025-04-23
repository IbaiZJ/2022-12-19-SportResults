<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="pageTitle" scope="request" value="home"/>
<jsp:include page="/WEB-INF/includes/header.jsp"/>
<main class="centered-content">
  <div class="card">
    <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
    <h2 class="card-title"><c:out value="${requestScope.user.username}"/></h2>
    <div class="card-body">
      <table>
        <caption><fmt:message key="userList"/></caption>
        <thead>
          <tr>
            <th><fmt:message key="userId"/></th>
            <th><fmt:message key="username"/></th>
            <!-- password shouldn't be shown -->
            <th><fmt:message key="firstName"/></th>
            <th><fmt:message key="secondName"/></th>
            <th><fmt:message key="email"/></th>
            <th><fmt:message key="edit"/></th>
            <th><fmt:message key="delete"/></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td><a href="/user/${requestScope.user.userId}">${requestScope.user.userId}</a></td>
            <td>${requestScope.user.username}</td>
            <!-- password shouldn't be shown -->
            <td>${requestScope.user.firstName}</td>
            <td>${requestScope.user.secondName}</td>
            <td>${requestScope.user.email}</td>
            <c:choose>
            <c:when test="${sessionScope.user.userId==requestScope.user.userId}">
            <td><a class="button" href="/user/${requestScope.user.userId}/edit"><fmt:message key="edit"/></a></td>
            <td><a class="button" href="/user/${requestScope.user.userId}/delete"><fmt:message key="delete"/></a></td>
            </c:when>
            <c:otherwise>
            <td>-</td><td>-</td>
            </c:otherwise>
            </c:choose>
          </tr>
        </tbody>
      </table>
    </div>
    </fmt:bundle>
  
  </div>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp"/>