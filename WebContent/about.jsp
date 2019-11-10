<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/main.css">

<title>P3 EJZ360 FOAMS: Error 404</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<a class="navbar-brand" href="FoamServlet">FOAMS</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="FoamServlet">Home
				</a></li>
				<li class="nav-item "><a class="nav-link" href="add.jsp">Add
						athletes</a></li>
				<li class="nav-item active"><a class="nav-link" href="about.jsp">About</a><span class="sr-only">(current)</span></li>
			</ul>
		</div>
	</nav>

	<div class="container">
		<h2>Freedonia welcomes you!</h2>
		
		<div class="card">
			<div class="card-header">Registration is open!</div>
			<div class="card-body">
				<h5 class="card-title">See yourself at the opening ceremonies</h5>
				<p class="card-text">Anyone 16 years of age or older on July 24, 2020 is eligible.</p>
				<a href="FoamServlet" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-home"></span>Register today!</a>
			</div>
		</div>
	</div>
</body>
</html>