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

<title>P4 EJZ360 FOAMS</title>
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
				<li class="nav-item active"><a class="nav-link"
					href="FoamServlet">Home <span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="add.jsp">Add
						athletes</a></li>
				<li class="nav-item"><a class="nav-link" href="about.jsp">About</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">

		<h2>FOAMS athlete roster</h2>
		<p>${welcome}</p>
		<table class="table table-bordered table-striped table-hover">
			<thead class="thead-primary">
				<tr>
					<th>Eligible</th>
					<th>National ID</th>
					<th>Last Name</th>
					<th>First Name</th>
					<th>Date of Birth</th>
					<th>Age</th>
					<th colspan="2">Options</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="athlete" items="${roster}">
					<tr>
						<td>${!athlete.eligible ? '<img src="img/ineligible.png" alt="ineligible">': "" }</td>
						<td><c:out value="${athlete.nationalID}" /></td>
						<td><c:out value="${athlete.lastName}" /></td>
						<td><c:out value="${athlete.firstName}" /></td>
						<td ${empty athlete.dateOfBirth ? "class='table-danger'" : ''}><c:out
								value="${athlete.dateOfBirth}" /></td>
						<td ${athlete.eligible == "false" ? "class='table-danger'" : ''  }><c:out
								value="${athlete.age < 0 ? '' : athlete.age}" /></td>
						<td><a href='edit?action=edit-form&id=<c:out value="${athlete.nationalID}" />'>edit</a></td>
						<td><a
							href='delete?action=confirmDelete&id=<c:out value="${athlete.nationalID}" />'>delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="${!empty errMsg ? 'alert alert-danger' : '' }"
				role="alert">
				<c:forEach var="err" items="${errMsg}">
					<p>
						<span><c:out value="${err.key}" /> :</span>
						<c:out value="${err.value}" />
					</p>
				</c:forEach>

			</div>

		<form name="form-add" id="form-add" action="add.jsp" method="post">
			<input class="btn btn-primary" type="submit" value="Add athlete"
				id="submit-add" />

		</form>

	</div>

</body>
</html>