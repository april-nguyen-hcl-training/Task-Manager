<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
  <head>
    <%@ include file="bootstrap.jsp" %>
    <%@ include file="style.jsp" %>
    <title>Tasks</title>
  </head>

  <body class="text-center">
    <div class="tasks-bar">
      <h1 class="mb-3">Hello, ${user.firstName} ${user.lastName}</h1>
      <form:form action="logout" method="post">
        <input type="submit" value="Sign Out" class="btn btn-primary"/>
      </form:form>
    </div>
    <p style="color:green;"><c:out value="${successAlert}"/></p>
    <p style="color:red;"><c:out value="${failAlert}"/></p>
    <a href="/task">Add New Task</a>
    <div class="list">
      <c:if test="${!empty taskList}">
        <h3>Task List</h3>
        <table class="table tg">
          <thead>
            <tr>
              <th scope="col">Start</th>
              <th scope="col">End</th>
              <th scope="col">Name</th>
              <th scope="col">Description</th>
              <th scope="col">Severity</th>
              <th scope="col">Delete</th>
              <th scope="col">Edit</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${taskList}" var="task">
              <tr>
                <fmt:parseDate value="${task.start}" var="startDate" pattern="yyyy-MM-dd'T'HH:mm:ss" />
                <fmt:formatDate value="${startDate}" var="startDateString" pattern="MMM dd, yyyy" />
                <td><c:out value="${startDateString}"/></td>

                <fmt:parseDate value="${task.end}" var="endDate" pattern="yyyy-MM-dd'T'HH:mm:ss" />
                <fmt:formatDate value="${endDate}" var="endDateString" pattern="MMM dd, yyyy" />
                <td><c:out value="${endDateString}"/></td>

                <td>${task.name}</td>

                <td>${task.description}</td>

                <td>${task.severity}</td>
                <td>
                  <form:form method="post" action="/task/delete/${task.id}">
                  <input type="submit" value="Delete" class="btn btn-primary">
                  </form:form>
                </td>
                <td><a href="/task/${task.id}" class="btn btn-primary" role="button">Edit</a></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>
    </div>
  </body>
</html>