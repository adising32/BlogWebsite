<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
	<head>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	</head>

<body>
	<div class="nav">
		<ul>
			<li>
				<div id="profile">
					<img src="${IMG}">
					<a href="update-image.jsp">Update Photo</a>
				</div>
			</li>
			<li><br/></li>
			<li><a href="ControllerServlet2?command=LIST_THOUGHTS" class="active">THOUGHTS</a></li>
			<li><a href="ControllerServlet2?command=UPDATE_PROFILE">UPDATE PROFILE</a></li>
			<li><a href="ControllerServlet2?command=NEW_POST">WRITE SOMETHING</a></li>
			<li><a href="ControllerServlet2?command=LOGOUT">LOGOUT</a></li>
		</ul>
	</div>
	
	<c:forEach var="x" items="${THOUGHTS}">
		<div class="thought-div">
		<div class="thought">
			<img  src="${x.getImgUrl()}">
			<div id="options">
				<a href="ControllerServlet2?command=EDIT_THOUGHT&id=${x.getId()}">Edit</a>
				<a href="ControllerServlet2?command=DELETE_THOUGHT&id=${x.getId()}">Delete</a>
				<a href="ControllerServlet2?command=CHANGE_PICTURE&id=${x.getId()}">Picture</a>		
			</div>
			
			<div id="content">
				<h3>${x.getTitle()}</h3>  
				<br/>
			</div>
		</div>
		</div>
	</c:forEach>	
	
</body>
	
</html>