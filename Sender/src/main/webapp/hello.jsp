<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>
<body>
<!-- 	<form action="/send" method="post"> -->
<!-- 	    <div><input type="submit" value="Wyślij"/></div> -->
<!-- 	</form> -->
	parser dla klubów :
	<form action="/dodaj/klub" method="post">
		<div><textarea name="content" rows="3" cols="60"></textarea></div>
	    <div><input type="submit" value="Wyślij"/></div>
	</form>
</body>
</html>