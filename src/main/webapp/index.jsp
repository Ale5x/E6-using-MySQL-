<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Добро пожаловать в библиотеку</title>
   </head>
 <body background="images/PIXNIO-1326294 1500х1000.jpg">
<br><br>
<form action="${pageContext.request.contextPath}/Index" width="400" height="300" align="right" method="POST" name="user">
    <p><input type="text" placeholder="Введите E-mail" name="email"></p>
    <p><input type="password" placeholder="Введите пароль" name="password"></p>
    <p><input type="submit" value="Войти"></p>
</form>
<p><div align="right">
    <a href="jsp/registration.jsp"><input type="submit" value="Регистрация"></a>
</div></p>
 </body>
</html>