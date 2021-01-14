<%@ page import="com.epam.training.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Личный кабинет</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
    response.sendRedirect("/index.jsp");
} else {
%>
<br><br><br>
<div align="center">
    <form action="${pageContext.request.contextPath}/update" method="post">
        <div align="center"><h1>Изменение личных данных</h1></div>
        <table cellpadding="5">
            <tr>
                <td>Login: </td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td>Email: </td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Изменить"></td>
            </tr>
        </table>
    </form>
    <br><br>
    <div align="center"><h1>${resulTask}</h1></div>
    <br><br>
</div>
<div align="center">
    <form action="update">
        <input type="submit" value="Удалить страницу" name="deletePage">
    </form>
</div>
<br><br><br>
<%}%>
</body>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</html>
