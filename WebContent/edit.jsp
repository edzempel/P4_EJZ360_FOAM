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
				<li class="nav-item"><a class="nav-link" href="add.jsp">Add
						athletes</a></li>
				<li class="nav-item"><a class="nav-link" href="about.jsp">About</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">

		<h2>Edit athlete information</h2>

		<form class="form" name="form-new" id="form-new" action="edit"
			method="post">
			
			<div>
				<input type="hidden" name="action" value="create-new">
				<input type="hidden" name="mode" value="${addEnabled == true ? 'add' : 'edit'}">
			</div>
			
			<div class="form-group">
				<label for="nationalId">National ID</label> <input id="nationalId"
					class="form-control ${errId == null ? null: errId ? 'is-invalid' : 'is-valid'}"
					name="newId" readonly pattern="[A-Za-z0-9-.]+"
					required type="text" value="<c:out value='${empty param.newId ? athlete.nationalID : param.newId}'/>" />
				<div class="${!errId ? 'valid-feedback' : 'invalid-feedback'}">${feedbackIdMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-lastName">Last name</label> <input
					id="form-new-lastName"
					class="form-control ${errLast == null ? null: errLast ? 'is-invalid' : 'is-valid'}"
					name="newLast" pattern=".+" required
					type="text" value="<c:out value='${empty param.newLast ? athlete.lastName : param.newLast}' />" />
				<div class="${!errLast ? 'valid-feedback' : 'invalid-feedback'}">${feedbackLastMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-firstName">First name</label><input
					id="form-new-firstName"
					class="form-control ${errFirst == null ? null: errFirst ? 'is-invalid' : 'is-valid'}"
					name="newFirst" placeholder="fist name" pattern=".+" required
					type="text" value="<c:out value='${empty param.newFirst ? athlete.firstName : param.newFirst}' />" />
				<div class="${!errFirst ? 'valid-feedback' : 'invalid-feedback'}">${feedbackFirstMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-dob">Date of birth</label> <input
					class="form-control ${errDob == null ? null: errDob ? 'is-invalid' : 'is-valid'}"
					type="date" id="form-new-dob" name="newDob"
					value="<c:out value='${empty param.newDob ? athlete.dateOfBirth : param.newDob}'/>" min="1900-01-01"
					max="2020-07-25">
				<div class="${!errDob ? 'valid-feedback' : 'invalid-feedback'}">${feedbackDobMessage}</div>
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
						type="button">Cancel</button></a> <input class="btn btn-primary"
					type="${updateDisabled == true ? 'button' : 'submit'}" ${updateDisabled == true ? "disabled" : ""} value="Update athlete" id="submit-update" />
					<input class="btn btn-primary"
					type="${addEnabled == true ? 'submit' : 'hidden'}" value="Add athlete" id="submit-new" />
					
			</div>

		</form>

	</div>


</body>
</html>