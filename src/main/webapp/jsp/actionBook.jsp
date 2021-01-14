<%@ page import="com.epam.training.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Работа с каталогом книг</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
    response.sendRedirect("/index.jsp");
} else {
%>
<br><br>
<div align="center">
    <form action="${pageContext.request.contextPath}/AddBook" method="post">
        <table border="1">
        <table>
            <thead>
            <tr>
                <th colspan="2">Добавление книги в библиотеку</th>
            </tr>
            </thead>
            <tr>
                <th>Введите имя автора: </th>
                <th><input type="text" placeholder="Автор" name="author"></th>
            </tr>
            <tr>
                <th>Введите название произведени: </th>
                <th><input type="text" placeholder="Название произведения" name="title"></th>
            </tr>
            <tr>
                <th>Тип книги (бумажный или электронный): </th>
                <th><input type="text" placeholder="Тип книги" name="type"></th>
            </tr>
            <tfoot align="center">
                <tr>
                    <th colspan="2"><input type="submit" VALUE="Добавить книгу"></th>
                </tr>
            </tfoot>
        </table>
        </table>
    </form>
</div>
<br>
<form action="${pageContext.request.contextPath}/ActionBook">
    <table border="1">
        <thead>
        <tr>
            <th colspan="2">Удаление книги по id</th>
        </tr>
        </thead>
        <tr>
            <th><input type="number" placeholder="Введите id" name="bookId"></th>
            <th><input type="submit" value="Удалить" name="delete"></th>
        </tr>
    </table>
</form>
<br>
<form action="${pageContext.request.contextPath}/ActionBook" method="post">
        <table border="1">
            <thead>
            <tr>
                <th colspan="2">Обновление книги по id</th>
            </tr>
            </thead>
            <tr>
                <th>Введите id книги: </th>
                <th><input type="number" placeholder="id" name="bookId"></th>
            </tr>
        <tr>
            <th>Введите имя автора: </th>
            <th><input type="text" placeholder="Автор" name="author"></th>
        </tr>
        <tr>
            <th>Введите название произведения: </th>
            <th><input type="text" placeholder="Название произведения" name="title"></th>
        </tr>
        <tr>
            <th>Тип книги (бумажный или электронный): </th>
            <th><input type="text" placeholder="Тип книги" name="type"></th>
        </tr>
        <tfoot>
        <tr>
            <th colspan="2"><input align="center" type="submit" VALUE="Обновить книгу"></th>
        </tr>
        </tfoot>
    </table>
</form>
<br>
<div align="center">
    <h1>${resultTask}</h1>
</div>
<%}%>
</body>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</html>
