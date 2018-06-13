

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
		<link type="text/css" rel="stylesheet" href="css/style.css"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	</head>
<body>
	<jsp:include page = "header.jsp"/>
	<c:forEach var="x" items="${POSTS}">
		<div class="thought-div">
		<div class="thought-author">
			<img src="${x.getA().getAuthor_image()}"><br/><br/><br/><br/><br/><p>${x.getA().getAuthor_name()}</p>    
		</div>
		<div class="thought">
		<img  src="${x.getT().getImgUrl()}">		
			<div id="content">
				<h3>${x.getT().getTitle()}</h3>  
				<br/>
			</div>
		</div>
		</div>
	</c:forEach>	
</body>
</html>