<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <title>Dodaj treść</title>
</head>
<body>
	<h2>Dodawanie treści listu. Obsługuje parametr freemarkerowy {club}. 
		Aby go użyć trzeba przed wywołaniem postawić $.</h2>
	<form action="/add/adress" method="post">
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
		<div><textarea name="content" rows="30" cols="160"></textarea></div>
	    <div><input type="submit" value="Wyślij"/></div>
	</form>
</body>
</html>