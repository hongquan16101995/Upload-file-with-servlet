<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 07/29/2022
  Time: 7:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <button type="submit">Upload</button>
</form>
<c:forEach items="${filename}" var="f">
    <img style="width: 200px; height: 200px" src="${f}" alt="${f}">
    <br>
</c:forEach>
</body>
</html>
