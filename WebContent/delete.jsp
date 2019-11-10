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

<title>P3 EJZ360 FOAMS: add athlete</title>
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
				<li class="nav-item active"><a class="nav-link" href="add.jsp">Add
						athletes</a><span class="sr-only">(current)</span></li>
				<li class="nav-item"><a class="nav-link" href="about.jsp">About</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">

		<h2>Are you sure?</h2>

		<form class="form" name="form-delete" id="form-delete" action="delete"
			method="post">
			<div class="form-group">
				<label for="nationalId">National ID</label> <input id="nationalId"
					class="form-control ${errId == null ? null: errId ? 'is-invalid' : 'is-valid'}"
					readonly name="nationalId" type="text"
					value="<c:out value='${athlete.nationalID}'/>" />
				<div class="${!errId ? 'valid-feedback' : 'invalid-feedback'}">${feedbackIdMessage}</div>
			</div>
			<div class="form-group">
				<label for="lastName">Last name</label> <input id="lastName"
					class="form-control" readonly name="lastName" pattern=".+"
					type="text" value="<c:out value='${athlete.lastName}' />" />
			</div>
			<div class="form-group">
				<label for="firstName">First name</label><input id="firstName"
					class="form-control" readonly name="fistName" type="text"
					value="<c:out value='${athlete.firstName}' />" />
			</div>
			<div class="form-group">
				<label for="dob">Date of birth</label> <input class="form-control"
					type="date" id="dob" readonly name="dob"
					value="<c:out value='${athlete.dateOfBirth}'/>">

			</div>
			<div>
				<input type="hidden" name="action" value="delete">
			</div>
			<div class="${!empty errMsg ? 'alert alert-danger' : '' }"
				role="alert">
				<c:forEach var="err" items="${errMsg}">
					<p>
						<span><c:out value="${err.key}" /> :</span>
						<c:out value="${err.value}" />
					</p>
				</c:forEach>

			</div>
			<div>

				<a href="FoamServlet"><button class="btn btn-danger"
						type="button">Cancel</button></a> 
						<a
			href='delete?action=delete&id=<c:out value="${athlete.nationalID}" />'><button class="btn btn-primary"
					type="button">Delete</button></a>
			</div>

		</form>

	</div>


</body>
</html>