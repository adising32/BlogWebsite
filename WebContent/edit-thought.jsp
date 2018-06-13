<html>
<body>
	<form action="ControllerServlet2" method="POST">
	
		<input type="hidden" name="command" value="SAVE_EDITED_THOUGHT"/>
		<input type="hidden" name="id" value="${THE_THOUGHT.getId()}"/>
		
	
		Post title<input type="text" name="title" value="${THE_THOUGHT.getTitle()}"/>
		Post body<textarea  name="text">${THE_THOUGHT.getText()}</textarea>
		<input type="submit" value="submit"/>
		
	</form>
</body>
</html>