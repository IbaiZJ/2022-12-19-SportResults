<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section id="sport_results">
  <a class="action_button" href="/sportResult/create">Create</a><!-- if permited -->
  <span class="sport_result_group">
    <span class="sport_result">
      <img src="/images/teamA.png" alt="teamA logo" title="teamA" class="team" /><!--src from the name -->
      <span class="result">1</span>
      -
      <span class="result">2</span>
      <img src="/images/teamB.png" alt="teamB logo" title="teamB" class="team" />
    </span>
    <span class="sport_result_actions"> <!-- if permited -->
      <a class="action_button" href="/sportResult/1/edit">Edit</a>
      <a class="action_button" href="/sportResult/1/delete">Delete</a>
    </span>
  </span>
  <span class="sport_result_group">
    <span class="sport_result">
      <img src="/images/teamB.png" alt="teamB logo" title="teamB" class="team" />
      <span class="result">1</span>
      -
      <span class="result">3</span>
      <img src="/images/teamC.png" alt="teamC logo" title="teamC" class="team" />
    </span>
    <span class="sport_result_actions">
      <a class="action_button" href="/sportResult/2/edit">Edit</a>
      <a class="action_button" href="/sportResult/2/delete">Delete</a>
    </span>
  </span>
</section>
