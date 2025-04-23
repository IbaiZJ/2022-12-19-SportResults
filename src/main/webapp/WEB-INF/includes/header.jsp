<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="language" scope="page" value="${sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'].toLanguageTag()}" />
<c:if test="${empty language}">
  <c:set var="browserLanguageLong" scope="page" value="${ fn:split(header['Accept-Language'], ',')[0] }" />
  <c:set var="language" scope="page" value="${ fn:split(browserLanguageLong, ';')[0] }" />
</c:if>

<!DOCTYPE html>
<html lang="${language}">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="/css/style.css"/>
  <link rel="stylesheet" href="/css/sport_result.css"/>
  <link rel="icon" href="/images/mu_light.svg" type="image/svg+xml"/>
  <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
  <title>MVC Exercise 3 - <fmt:message key="${requestScope.pageTitle}"/></title>
  </fmt:bundle>
</head>
<body>
  <header>
    <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Labels">
    <h1>MVC Exercise 3 - <fmt:message key="${requestScope.pageTitle}"/></h1>
    <nav>
      <div id="left-nav">
        <a href="/" class="${requestScope.pageTitle=='home' || requestScope.pageTitle=='User' ? 'current' : ''}"><fmt:message key="home"/></a>
        
        <c:if test="${not empty sessionScope.user}">
        <a  href="/user/list"
            class="${requestScope.pageTitle=='userList' ? 'current' : ''}"
            id="user-list-nav">
            <fmt:message key="userList" />
        </a>
        </c:if>
        <a href="/user/create"
          class="${requestScope.pageTitle=='createUser' ? 'current' : ''}"
          id="create-user-nav">
          <fmt:message key="createUser" />
        </a>
        <a href="/news/list"
          class="${requestScope.pageTitle=='newsList' ? 'current' : ''}"
          id="news-list-nav">
          <fmt:message key="newsList" /></a>
        <c:if test="${not empty sessionScope.user}">
        <a href="/news/create"
          class="${requestScope.pageTitle=='createNewsItem' ? 'current' : ''}"
          id="create-news-item-nav">
          <fmt:message key="createNewsItem" />
        </a>
        <a href="/login?action=logout"
          title="${sessionScope.user.username}"
          id="logout-nav">
          <fmt:message key="logout" />
        </a>
        </c:if>
      </div>
      <div id="right-nav">
        <a href="/lang?language=eu&country=ES"
          class="${fn:startsWith(language,'eu') ? 'current' : '' }">
          <fmt:message key="language.eu" />
        </a>
        <a href="/lang?language=es&country=ES"
          class="${fn:startsWith(language,'es') ? 'current' : '' }">
          <fmt:message key="language.es" />
        </a>
        <a href="/lang?language=en&country=UK"
          class="${fn:startsWith(language,'en') ? 'current' : '' }">
          <fmt:message key="language.en" />
        </a>
      </div>
    </nav>
    </fmt:bundle>
    <jsp:include page="/WEB-INF/view/sport_results/sport_results.jsp" />
    <fmt:bundle basename="edu.mondragon.webeng1.mvc_exercise.resources.Notifications">
    <div id="notifications">
      <c:if test="${not empty sessionScope.error}">
        <p class="error"><fmt:message key="${sessionScope.error}"/></p>
        <c:remove var="error" scope="session" />
      </c:if>
      <c:if test="${not empty sessionScope.message}">
        <p class="message"><fmt:message key="${sessionScope.message}"/></p>
        <c:remove var="message" scope="session" />
      </c:if>
    </div>
    </fmt:bundle>
  </header>