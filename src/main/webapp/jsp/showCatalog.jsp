<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.training.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Просмотр каталога</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<% if(user == null) {
	response.sendRedirect("index.jsp");
} else {
%>
<div align="center"><h1>Просмотр каталога</h1></div>
	<form action="${pageContext.request.contextPath}/ShowCatalog">
		<div align="center"><input type="submit" value="Постраничный просмотр каталога" name="start"></div>
		<table border="1" align="center">
			<tr>
				<th>#</th>
				<th>Author</th>
				<th>Title</th>
				<th>Type</th>
			</tr>
			<c:forEach var="bookList" items="${bookResult}">
				<tr>
					<td>${bookList.bookId}</td>
					<td>${bookList.author}</td>
					<td>${bookList.title}</td>
					<td>${bookList.type}</td>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="4">
					<p align="center">Page ${page}</p>
				</th>
			</tr>
			<tfoot>
				<tr>
					<th align="center">
						<input type="submit" value="Предыдущая страница" name="back">
					</th>
					<th colspan="2" align="center">
						<input type="number" name="jump"><input type="submit" value="Перейти">
					</th>
					<th align="center">
						<input type="submit" value="Следующая страница" name="next"/>
					</th>
				</tr>
			</tfoot>
		</table>
	</form>
<br><br><br><br>
<form action="${pageContext.request.contextPath}/ShowAllCatalog">
	<div align="center"><input type="submit" value="Показать весь каталог" name="showAll"></div>
	<table border="1" align="center">
		<tr>
			<th>#</th>
			<th>Author</th>
			<th>Title</th>
			<th>Type</th>
		</tr>
		<c:forEach var="AllBookList" items="${AllBookResult}">
			<tr>
				<td>${AllBookList.bookId}</td>
				<td>${AllBookList.author}</td>
				<td>${AllBookList.title}</td>
				<td>${AllBookList.type}</td>
			</tr>
		</c:forEach>
	</table>
</form>

<%}%>
</body>
<footer>
	<jsp:include page="footer.jsp"/>
</footer>
</html>