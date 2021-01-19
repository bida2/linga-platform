<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Вход - LinGA</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<div class="container">
<div class="row text-center mt-4">
	<div class="col">
		<form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Вход в системата</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <input name="username" type="text" class="mt-2 form-control" placeholder="Потребителско име"
                   autofocus="true"/>
            <br>
            <input name="password" type="password" class="mt-2 form-control" placeholder="Парола"/>
            <br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-lg btn-primary mt-2" type="submit">Вход</button>
            <p class="mt-2 text-success">${message}</p>
             <div id="statusReport" class="d-none mt-2 text-center">
         <div id="modalId" class="modal" tabindex="-1" role="dialog">
  			<div class="modal-dialog" role="document">
   				 <div class="modal-content">
      				<div class="modal-header">
        				<h5 class="modal-title"><spring:message code="lang.modal.title.login" text="default" /></h5>
        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          					<span aria-hidden="true">&times;</span>
        				</button>
      				</div>
      			<div class="modal-body">
        <p id="statusMessage">
        	${errorMessage}
         	${successMessage}
         </p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="close" text="default" /></button>
      </div>
    </div>
  </div>
</div>
         </div>
            <p class="mt-2 text-danger">${error}</p>
            <h5>Нямате регистрация? <br>Може да се регистрирате тук или да се върнете на сайта</h5>
            <p class="text-center mt-3"><a class="btn btn-primary" href="${contextPath}/signup">Регистрация</a><a class="ml-2 btn btn-primary" href="/">Начало</a></p>
        </div>

    </form>
	</div>
</div> 
</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="js/forgetPass.js"></script>
</body>

</html>