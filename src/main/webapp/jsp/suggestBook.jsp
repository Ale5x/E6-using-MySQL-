<%@ page import="com.epam.training.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Предложить книгу</title>
</head>
<body background="Images/1534527007_maxresdefault.jpg">
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
	response.sendRedirect("index.jsp");
} else {
%>
<br><br><br>
<form action="${pageContext.request.contextPath}/SuggestBook">
	<table>
		<tr>
			<td>Введите автора произведения</td>
			<td><input type="text" name="author"></td>
		</tr>
		<tr>
			<td>Введите название произведения</td>
			<td><input type="text" name="title"></td>
		</tr>
		<tr>
			<td>Тип книги</td>
			<td><input type="text" name="type"></td>
		</tr>
	</table>
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