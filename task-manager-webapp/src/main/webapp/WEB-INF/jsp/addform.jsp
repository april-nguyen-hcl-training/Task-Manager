<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.hcl.taskmanagerwebapp.model.Task"%>
<%@ page import="com.hcl.taskmanagerwebapp.model.User"%>

<html>
  <head>
    <%@ include file="bootstrap.jsp" %>
    <%@ include file="style.jsp" %>

    <title>Add Task</title>
  </head>
  <body class="text-left">
  <main class="form-task">
    <h1 class="mb-3 text-center">Add Task</h1>

    <form:form action='/task' method='post' modelAttribute="taskInput">
      <div class="mb-3">
        <label for="nameInput" class="form-label">Name</label>
        <form:input class="form-control" id="nameInput" path='name' required="required"
          value="${taskInput.name}"/>
        <p style="color:red;"><c:out value="${nameAlert}"/></p>
      </div>

      <div class="mb-3">
        <label for="descriptionInput" class="form-label">Description</label>
        <form:textarea class="form-control" rows="3" id="descriptionInput" path='description'
          value="${taskInput.description}"/>
        <p style="color:red;"><c:out value="${descriptionAlert}"/></p>
      </div>

      <div class="mb-3">
        <label for="startInput" class="form-label">Start Date</label>
        <fmt:formatDate value="${taskInput.start}" var="startDateString" pattern="yyyy-MM-dd" />
        <form:input class="form-control" type="date" id="startInput" path='start'
          value="${startDateString}" />
        <p style="color:red;"><c:out value="${startAlert}"/></p>
      </div>

      <div class="mb-3">
        <label for="endInput" class="form-label">End Date</label>
        <fmt:formatDate value="${taskInput.end}" var="endDateString" pattern="yyyy-MM-dd" />
        <form:input class="form-control" type="date" id="endInput" path='end'
          value="${endDateString}" />
        <p style="color:red;"><c:out value="${endAlert}"/></p>
      </div>

      <div class="mb-3">
        <label for="severityInput" class="form-label">Severity</label>
        <form:select items="${severityList}" class="form-control" id="severityInput" path="severity"
          value="${taskInput.severity}"/>
        <p style="color:red;"><c:out value="${severityAlert}"/></p>
      </div>

      <div>
        <form:hidden path="user"/>
      </div>

      <button class="w-100 btn btn-lg btn-primary" type="submit">Add</button>
    </form:form>

  </main>
  </body>
</html>
