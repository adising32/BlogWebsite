<html>
<head>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
	
	<form action="ControllerServlet2" method="GET" class="form">
		
		<input type="hidden" name="command" value="SAVE_INFO"/>
		
		Name:<input type="text" name="author_name" value="${THE_AUTHOR.getAuthor_name()}"/><br/><br/>
		Email:<input type="email" name="author_email" value="${THE_AUTHOR.getAuthor_email()}"/><br/><br/>
		Password<input type="password" name="author_password" value="${THE_AUTHOR.getAuthor_password()}"/><br/><br/>
				
		<button type="submit" value="submit">SAVE</button>
	
	</form>

</body>

</html>