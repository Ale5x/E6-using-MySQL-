<%@ page import="com.epam.training.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Сообщение для администраторов</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
    response.sendRedirect("index.jsp");
} else {
%>
<form action="${pageContext.request.contextPath}/MessageForAdmin" method="post">
    <p><b>Введите ваше сообщение</b></p>
    <p><textarea rows="5" cols="50" name="text"></textarea></p>
    <p><input type="submit" value="Отправить"> </p>
</form>
<div align="center">
    <h1>${resultTask}</h1>
</div>
<%}%>
</body>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</html>
