<html>
	<head>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	</head>
<body>

	<div class="img">
		<img src="css/images/Welcome-Image-1.png"/>
	</div>
   

	<form action="ControllerServlet" method="POST" class="form">
		<input type="hidden" name="command" value="LOG_IN"/>
		Email : <input type="email" name="username" placeholder="Enter email id"/>
   <br/>Password  : <input type="password" name="password" placeholder="Enter Password"/>
   <br/><button type="submit" value="submit">Login</button>
	</form>
	
	<div class="form">
		Dont have an account...<a href="add-author.html">sign up</a>
		<br/>${msg}
	</div>
</body>
</html>