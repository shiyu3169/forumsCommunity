<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERROR 404</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./css/fresh-bootstrap-table.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css"
	rel="stylesheet" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
</head>
<body background="">
	<header> <nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div id="navbar" class="collapse navbar-collapse" aria-expanded="true">
			<form class="navbar-form navbar-left" method="get" action="home">
				<div class="form-group">
					<input type="text" name="search" value="${forumName }"
						class="form-control" placeholder="Seaching for forum ...">
				</div>
				<button class="btn btn-primary" type="submit">Go!</button>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
				<li><a href="<%=request.getContextPath()%>/login">Log out</a></li>
			</ul>
		</div>
	</div>
	</nav> </header>
	<br />
	<br />
	<br />
	<br />
	<div class="container">
		<div class="text-center">
			<img src="./image/404.png" alt="404">
		</div>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div align="center">
					<h1>Oops!</h1>
					<%if (request.getAttribute("message") != null) { %>
					<p align="middle"><%=request.getAttribute("message") %></p>
					<%} %>
					<div align="center">
						<a href="home" class="btn btn-primary btn-lg"><span
							class="glyphicon glyphicon-home"></span> Take Me Home </a>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>