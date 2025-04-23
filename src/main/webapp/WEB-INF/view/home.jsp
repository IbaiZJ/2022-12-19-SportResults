<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="pageTitle" scope="request" value="home" />
<jsp:include page="/WEB-INF/includes/header.jsp" />


<main class="centered-content">
  <section class="card">
    <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">

      <c:choose>
        <c:when test="${not empty sessionScope.user}">
          <h2 class="card-title">
            <fmt:message key="hello" />
            <c:out value="${sessionScope.user.username}" />!</h2>
          <div class="card-body">
            <p>
              <fmt:message key="logged" />
            </p>
            <table>
              <caption>
                <c:out value="${sessionScope.user.username}" default="Unknown" />
              </caption>
              <thead>
                <tr>
                  <th>
                    <fmt:message key="userId" />
                  </th>
                  <th>
                    <fmt:message key="username" />
                  </th>
                  <!-- password shouldn't be shown -->
                  <th>
                    <fmt:message key="firstName" />
                  </th>
                  <th>
                    <fmt:message key="secondName" />
                  </th>
                  <th>
                    <fmt:message key="email" />
                  </th>
                  <th>
                    <fmt:message key="edit" />
                  </th>
                  <th>
                    <fmt:message key="delete" />
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <c:out value="${sessionScope.user.userId}" default="Unknown" />
                  </td>
                  <td>
                    <c:out value="${sessionScope.user.username}" default="Unknown" />
                  </td>
                  <td>
                    <c:out value="${sessionScope.user.firstName}" default="Unknown" />
                  </td>
                  <td>
                    <c:out value="${sessionScope.user.secondName}" default="Unknown" />
                  </td>
                  <td>
                    <c:out value="${sessionScope.user.email}" default="Unknown" />
                  </td>
                  <td><a class="button" href="/user/${user.userId}/edit">
                      <fmt:message key="edit" /></a></td>
                  <td><a class="button" href="/user/${user.userId}/delete">
                      <fmt:message key="delete" /></a></td>
                </tr>
              </tbody>
            </table>
            <a class="button" href="/login/logout">
              <fmt:message key="logout" /></a>
          </div>
        </c:when>
        <c:otherwise>
          <h2 class="card-title">
            <fmt:message key="login" />
          </h2>
          <form class="card-body" action="login/login" method="post">
            <label>
              <fmt:message key="username" />:
              <input type="text" name="username" required
              value="<c:out value='${sessionScope.username}' default='' />"
              placeholder="<fmt:message key='username'/>"/>
            </label>
            <label>
              <fmt:message key="password" />:
              <input type="password" name="password" required
                placeholder="<fmt:message key='password' />"/>
            </label>
            <button type="submit">
              <fmt:message key="login" /></button>
          </form>
        </c:otherwise>
      </c:choose>
    </fmt:bundle>
  </section>
</main>
<jsp:include page="/WEB-INF/includes/footer.jsp" />