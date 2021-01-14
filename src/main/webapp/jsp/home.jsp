<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.epam.training.model.User" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
		<title>Home page</title>
	</head>
	<body background="images/biblioteka-kongressa.jpg">
    <% User user = (User) session.getAttribute("user"); %>
    <% long countBook = (long) session.getAttribute("countBook"); %>
    <% long countUser = (long) session.getAttribute("countUser"); %>
	<% if(user == null) {
		response.sendRedirect("index.jsp");
	} else {
	%>
		<br>
	<table border="1">
		<tr>
			<td>Id пользователя</td>
			<td align="center"><%= user.getUserId()%></td>
		</tr>
		<tr>
			<td>login</td>
			<td><%=user.getLogin()%></td>
		</tr>
		<tr>
			<td>email</td>
			<td><%=user.getEmail()%></td>
		</tr>
		<tr>
			<tfoot>
			<tr>
				<td colspan="2" align="center">
					<p><% if(user.getLogin().equals("Guest")) {%>
					Гость
					<%} else if(user.isAdminAccess()){%>
					Администратор
					<% }else { %>
					Пользователь
					<% } %></p>
				</td>
			</tr>
			</tfoot>
		</tr>
	</table>
	<br>
	<form action="Index" >
		<table>
			<tr>
				<td><i>Количество зарегистрированных пользователей</i></td>
				<td><%=countUser%></td>
			</tr>
			<tr>
				<td><i>Количество книг в библиотеке</i></td>
				<td><%=countBook%></td>
			</tr>
		</table>
	</form>
	<br><br><br>
			<div align="center">
				<table border="1" cellpadding="5">
					<tr>
						<td>Просмотр каталога</td>
						<td><a href="jsp/showCatalog.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<tr>
						<td>Поиск книги</td>
						<td><a href="jsp/searchBook.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<tr>
						<td>Предложить книгу</td>
						<td><a href="jsp/suggestBook.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<tr>
						<td>Написать сообщение администраторам</td>
						<td><a href="jsp/messageForAdmin.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<% if(user.getUserId() != 0) {%>
					<tr>
						<td>Изменить личные данные</td>
						<td><a href="jsp/updatePage.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<%}%>
					<%if(user.isAdminAccess()) {%>
					<tr>
						<td>Работа с пользователями</td>
						<td><a href="jsp/searchUser.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<tr>
						<td>Работа с каталогом</td>
						<td><a href="jsp/actionBook.jsp"><input type="submit" value="Перейти"></a></td>
					</tr>
					<%}%>
				</table>
			</div>
    <br><br>
    <div align="center">
        <form action="${pageContext.request.contextPath}/Home">
            <input type="submit" value="Выход"></a>
        </form>
    </div>

	<%}%>
	</body>
</html>