<html>
<head>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
	<form action="ControllerServlet2" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="file-action" value="update-author-image"/>
		<input type="file" accept="image/*" name="file"/>
		<input type="submit" value="submit"/>
		
	</form>
</body>
</html>