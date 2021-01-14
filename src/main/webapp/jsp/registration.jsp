<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Регистрация нового пользователя</title>
	</head>
		
	<body background="images/PIXNIO-1326294 1500х1000.jpg" background-size="cover">
<br>
<br>
<div align="center">
<h1><em>Регистрация нового пользователя</em></h1>
	<form action="${pageContext.request.contextPath}/Registration" method="POST">
		<table>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>Login:</td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" /></td>
            </tr>
            <tr>
                <td colspan="2"> <i><p>* Пароль должен быть не меньше 6 символов,</p>
                                       минимум одна буква и одна цифра</i></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Отправить"></td>
            </tr>
         </table>
	</form>
    <h1>${resultTask}</h1>
</div>
	</body>
</html>