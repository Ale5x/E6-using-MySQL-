<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@ page import="com.epam.training.model.User" %>
<html>
<head>
<meta charset="UTF-8">
<title>Поиск книги</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
    response.sendRedirect("index.jsp");
} else {
%>
<div align="center"><h1>Поиск книги по автору</h1></div>

<form action="${pageContext.request.contextPath}/SearchBook">
    <table>
        <tr>
            <td>Введите id книги: </td>
            <td><input type="number" name="bookId"></td>
            <td><input type="submit" value="Поиск"></td>
        </tr>
        <tr>
            <td>Введите имя автора: </td>
            <td><input type="text" name="author"></td>
            <td><input type="submit" value="Поиск"></td>
        </tr>
        <tr>
            <td>Введите название книги: </td>
            <td><input type="text" name="title"></td>
            <td><input type="submit" value="Поиск"></td>
        </tr>
        <tr>
            <td>Введите тип книги: </td>
            <td><input type="text" name="type"></td>
            <td><input type="submit" value="Поиск"></td>
        </tr>
        <tr>
            <td>Введите ключевое слово: </td>
            <td><input type="text" name="word"></td>
            <td><input type="submit" value="Поиск"></td>
        </tr>
    </table>
</form>
<br>
<div align="center">
    <h4>Количество найденных результатов - <c:out value="${countResult}"/></h4><br>
    <table border="1">
        <tr>
            <th>#</th>
            <th>Автор</th>
            <th>Название книги</th>
            <th>Тип книги</th>
        </tr>
            <c:forEach var="book" items="${listResult}">
        <tr>
                <td>${book.bookId}</td>
                <td>${book.author}</td>
                <td>${book.title}</td>
                <td>${book.type}</td>
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