<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/4/2026
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create a new employee</title>
</head>
<body>
<h3>Welcome, Enter the Employee Details</h3>
<form:form method="post" action="add-employee" modelAttribute="employee">
    <table>
        <tr>
            <td><form:label path="id">Employee ID:</form:label></td>
            <td><form:input path="id"></form:input></td>
        </tr>
        <tr>
            <td><form:label path="name">Employee's Name:</form:label></td>
            <td><form:input path="name"></form:input></td>
        </tr>
        <tr>
            <td><form:label path="contactNumber">Contact's number::</form:label></td>
            <td><form:input path="contactNumber"></form:input></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
