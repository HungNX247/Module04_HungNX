<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body>
<h2>Currency Converter Advanced</h2>
<form action="convert" method="post">
    From:
    <select name="from">
        <c:forEach items="${currencies}" var="c">
            <option value="${c}">${c}</option>
        </c:forEach>
    </select>

    <br><br>

    To:
    <select name="to">
        <c:forEach items="${currencies}" var="c">
            <option value="${c}">${c}</option>
        </c:forEach>
    </select>

    <br><br>
    Amount:
    <input type="number" step="0.01" name="amount"/>

    <br><br>
    <button type="submit">Convert</button>
</form>

<br>

<c:if test="${not empty result}">
    Result: <b>${result}</b>
</c:if>

<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
</body>
</html>