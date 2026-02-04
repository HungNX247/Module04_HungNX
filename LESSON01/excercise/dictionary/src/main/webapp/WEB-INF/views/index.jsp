<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dictionary</title>
</head>
<body>
<h2>Từ điển Anh - Việt</h2>
<form action="search" method="post">
    <input type="text" name="word" placeholder="Nhập từ tiếng Anh">
    <button type="submit">Tra cứu</button>
</form>

<c:if test="${not empty meaning}">
    <p>${word} : <b>${meaning}</b></p>
</c:if>

<c:if test="${not empty message}">
    <p style="color: red">${message}</p>
</c:if>
</body>
</html>