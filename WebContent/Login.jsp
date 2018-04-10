<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="./css/fresh-bootstrap-table.css">
		<link href="./css/Register.css" rel="stylesheet">
	</head>
	<body background="./image/background.jpg">
		<div>
	        <header>
	            <nav class="navbar navbar-inverse navbar-fixed-top" ng-controller="NavController">
	                <div class="container">
	                    <div id="navbar" class="collapse navbar-collapse" aria-expanded="true">
	                        <ul class="nav navbar-nav navbar-right">
	                            <li><a href="<%=request.getContextPath()%>/home">Home</a></li>
	                            <li><a href="<%=request.getContextPath()%>/register">Sign up</a></li>
	                        </ul>
	                    </div>
	                </div>
	            </nav>
	        </header>
	    </div>
		<div class="container">

	        <form  method="post" class="form-signin" action="login">
	            <h2 class="form-signin-heading"> 
	            	<font color="white">Please Login </font>
	            </h2>
	            <label for="inputUserName" class="sr-only">User Name</label>
	            <input type="text" name="username" id="Username" class="form-control" placeholder="UserName" value="${username}" required autofocus>
	            <br/>
	            <label for="inputPassword" class="sr-only">Password</label>   
	            <input type="password" name="password" id="Password" class="form-control" placeholder="Password" value="${password}"  required>
	            <div class="checkbox">
	                <label>
	                    <input type="checkbox" name="remember-me" id="remember" checked="true" >
	                    <font color="white">Remember me</font>
	                </label>
            	</div>
	            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	             <h3>
	            	<font color="red">${message }</font>
	            </h3>
	        </form>
	    </div>
	</body>
</html>