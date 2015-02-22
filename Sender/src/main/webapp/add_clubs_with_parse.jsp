<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    <title>Parser/kluby</title>
</head>
<body>
	parser dla klubów :
	<form action="/add/clubs" method="post">
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
		<div><textarea name="content" rows="30" cols="100"></textarea></div>
	    <div><input type="submit" value="Wyślij"/></div>
	</form>
</body>
</html>