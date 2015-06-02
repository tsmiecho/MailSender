<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
	<title>Dodaj odbiorce</title>
</head>
<body>
	<h2>Formularz dodawania klubu do listy odbiorców.</h2>
	<form action="/add/club" method="post">
		<div>
			<select name="language">
				<option value="english">angielski</option>
				<option value="german">niemiecki</option>
				<option value="french">francuski</option>
				<option value="polish">polski</option>
				<option value="spanish">hiszpański</option>
				<option value="portuguese">portugalski</option>
				<option value="russian">rosyjski</option>
			</select>
		</div>
		<div>
			Mail: <input type="text" name="mail"/>
		</div>
		<div>
			Nazwa: <input type="text" name="name"/>
		</div>
		<div>
			<input type="submit" value="Wyślij" disabled="disabled"/>
			Validation unhandled!
		</div>
	</form>
</body>
</html>