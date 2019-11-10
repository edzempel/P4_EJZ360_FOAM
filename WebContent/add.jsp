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
		<a tabindex="1" class="navbar-brand" href="FoamServlet">FOAMS</a>
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

		<h2>Add an athlete</h2>

		<form class="form" name="form-new" id="form-new" action="add"
			method="post">
			<div class="form-group">
				<label for="nationalId">National ID</label> <input tabindex="2" id="nationalId"
					class="form-control ${errId == null ? null: errId ? 'is-invalid' : 'is-valid'}"
					name="newId" placeholder="national ID" pattern="[A-Za-z0-9-.]+"
					required type="text" value="<c:out value='${param.newId}'/>" />
				<div class="${!errId ? 'valid-feedback' : 'invalid-feedback'}">${feedbackIdMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-lastName">Last name</label> <input tabindex="3"
					id="form-new-lastName"
					class="form-control ${errLast == null ? null: errLast ? 'is-invalid' : 'is-valid'}"
					name="newLast" placeholder="last name" pattern=".+" required
					type="text" value="<c:out value='${param.newLast}' />" />
				<div class="${!errLast ? 'valid-feedback' : 'invalid-feedback'}">${feedbackLastMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-firstName">First name</label><input tabindex="4"
					id="form-new-firstName"
					class="form-control ${errFirst == null ? null: errFirst ? 'is-invalid' : 'is-valid'}"
					name="newFirst" placeholder="fist name" pattern=".+" required
					type="text" value="<c:out value='${param.newFirst}' />" />
				<div class="${!errFirst ? 'valid-feedback' : 'invalid-feedback'}">${feedbackFirstMessage}</div>
			</div>
			<div class="form-group">
				<label for="form-new-dob">Date of birth</label> <input tabindex="5"
					class="form-control ${errDob == null ? null: errDob ? 'is-invalid' : 'is-valid'}"
					type="date" id="form-new-dob" name="newDob"
					value="<c:out value='${param.newDob}'/>" min="1900-01-01"
					max="2020-07-25">
				<div class="${!errDob ? 'valid-feedback' : 'invalid-feedback'}">${feedbackDobMessage}</div>
			</div>
			<div>
				<input type="hidden" name="action" value="create-new">
				<input type="hidden" name="mode" value="add">
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
				<button tabindex="8" class="btn btn-secondary" type="reset">Clear form</button>
				<a href="FoamServlet"><button tabindex="7" class="btn btn-danger"
						type="button">Cancel</button></a> <input tabindex="6" class="btn btn-primary"
					type="submit" value="Add athlete" id="submit-new" />
			</div>

		</form>

	</div>


</body>
</html>