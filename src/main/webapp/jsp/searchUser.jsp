<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.epam.training.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Поиск пользователя</title>
    <style type="text/css">
        #footer {
            position: fixed;
            left: 50%; bottom: 0%;
        }
    </style>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
    response.sendRedirect("index.jsp");
} else {
%>
<form action="${pageContext.request.contextPath}/SearchUser">
    <table>
        <tr>
            <td>Поиск по id</td>
            <td><input type="number" name="userId"></td>
            <td><input type="submit" value="Найти"></td>
        </tr>
        <tr>
            <td>Поиск по email</td>
            <td><input type="text" name="email"></td>
            <td><input type="submit" value="Найти"></td>
        </tr>
        <tr>
            <td>Показать всех пользователей</td>
            <td><input type="submit" value="Показать" name="allUser"></td>
        </tr>
        <tr>
            <td>Показать только администраторов</td>
            <td><input type="submit" value="Показать" name="allAdmin"></td>
        </tr>
        </table>
</form>
<br><br>
<form action="${pageContext.request.contextPath}/ActionUser" method="get">
    <table border="1">
        <h2>Действия с пользователем</h2>
        <tr>
            <th><input type="number" placeholder="Введите id" name="userId"></th>
            <th><input type="submit" value="Delete" name="delete"></th>
            <th><input type="submit" value="Add admin" name="addAdmin"></th>
            <th><input type="submit" value="Back user" name="backUser"></th>
        </tr>
        <tfoot>
        <tr>
            <th align="center">${resultTask}</th>
        </tr>
        </tfoot>
    </table>
</form>
<br><br><br>
    <div align="center">
        <table border="1" cellpadding="5">
            <tr>
                <th>#</th>
                <th>Login</th>
                <th>Email</th>
                <th>Access admin</th>
            </tr>
            <c:forEach var="userList" items="${userResult}">
                <tr>
                    <td>${userList.userId}</td>
                    <td>${userList.login}</td>
                    <td>${userList.email}</td>
                    <td>${userList.adminAccess}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
<%}%>
</body>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</html>
