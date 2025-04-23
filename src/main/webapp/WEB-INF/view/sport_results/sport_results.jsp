<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section id="sport_results">
  <c:if test="${not empty sessionScope.user}">
    <a class="action_button" href="/sportResult/create">Create</a><!-- if permited -->
  </c:if>
  <c:forEach items="${requestScope.sportResults}" var="result">
    <span class="sport_result_group">
      <span class="sport_result">
        <img src="/images/${result.team1Name}.png" alt="${result.team1Name} logo" title="${result.team1Name}" class="team" /><!--src from the name -->
        <span class="result">${result.team1Result}</span>
        -
        <span class="result">${result.team2Result}</span>
        <img src="/images/${result.team2Name}.png" alt="${result.team2Name} logo" title="${result.team2Name}" class="team" />
      </span>
      <c:if test="${not empty sessionScope.user}">
        <span class="sport_result_actions"> <!-- if permited -->
          <a class="action_button" href="/sportResult/${result.sportResultId}/edit">Edit</a>
          <a class="action_button" href="/sportResult/${result.sportResultId}/delete">Delete</a>
        </span>
      </c:if>
    </span>
  </c:forEach>
</section>
