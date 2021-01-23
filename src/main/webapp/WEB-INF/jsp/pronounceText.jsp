<!DOCTYPE html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<sec:authorize var="isAdmin" access="hasRole('ADMIN')"></sec:authorize>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>ЛинГА - Изговори дума</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha512-MoRNloxbStBcD8z3M/2BmnT+rg4IsMxPkXaGh2zD6LGNNFE80W3onsAhRcMAMrSoyWL9xD7Ert0men7vR8LUZg==" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/main.css">
</head>

<body>
     <header>
        <nav class="navbar navbar-extra navbar-light bg-light">
            <a class="navbar-brand ml-1 mt-2 mx-auto" href="/"> <img class="img-fluid" src="images/logo-test.png">
            </a>
            <hr class="force-break">
            <div id="target" class="align-center-vertically mx-auto collapse navbar-collapse d-md-flex">
                <ul class="navbar-nav flex-column flex-md-row">
                    <li class="nav-item"><a class="nav-link" href="/wordUser">Добави дума <span class="sr-only">(current)</span></a>
                    </li>
                    <!-- Да се добави линк към администраторски добавяне на дума (без процес на одобряване) -->
                    <li class="nav-item ml-2"><a class="nav-link" href="/browseByLetter">Търсене по азбучен ред<span class="sr-only">(current)</span></a></li>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                    	<li class="nav-ite ml-2"><a class="nav-link" href="/signup">Регистрация <span class="sr-only">(current)</span></a>
                    </c:if>
                    <sec:authorize access="hasRole('ADMIN')">
                        <li class="nav-item ml-2"><a class="nav-link" href="/word">Добави
                                дума(администратор)<span class="sr-only">(current)</span>
                            </a></li>
                    </sec:authorize>
                </ul>
            </div>
            <div class="position-absolute position-absolute-left">
            		 <button class="btn btn-outline navbar-toggler d-md-none" data-toggle="collapse" data-target="#target">
                    	<i data-feather="menu"></i>
                    </button>
            	</div>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <div class="position-absolute position-absolute-right">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                        <i data-feather="log-in"></i><span class="d-none d-sm-inline">Вход</span>
                    </button>
                  
                </div>
                  <!-- Login through a modal - used to avoid resizing issues on mobile -->
                    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle">Вход в системата</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="${contextPath}/login" method="POST">
                                    <div class="modal-body">
                                        <div class="d-block text-center"><input class="form-control" type="text" name="username" placeholder="Потребителско име"> <input class="form-control" type="password" name="password" placeholder="Парола"> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="mr-auto"><input type="reset" class="btn btn-danger" value="Изчисти"></div>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Затвори</button>
                                        <button type="submit" class="btn btn-primary">Вход</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
            </c:if>
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <div class="position-absolute position-absolute-right">
                    <form id="logoutForm" method="POST" action="${contextPath}/logout">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                    <p class="d-none d-xl-inline-block">Здравейте,
                        ${pageContext.request.userPrincipal.name} |</p>
                    <a class="d-none d-xl-inline-block btn btn-danger text-white" onclick="document.forms['logoutForm'].submit()">Изход</a>
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="approve" class="d-none d-xl-inline-block btn btn-success text-white">Нови думи</a>
                        <a href="approve" class="d-block d-xl-none btn btn-success text-white move-loggedin-buttons">Нови думи</a>
                    </sec:authorize>
                    <a class="d-block d-xl-none btn btn-danger text-white mt-2 ${isAdmin ? 'move-loggedin-buttons' : 'move-loggedin-buttons-normal' }" onclick="document.forms['logoutForm'].submit()">Изход</a>
                </div>
            </c:if>
        </nav>
    </header>
    <!-- 
    Toast version of notifications that sound is still being processed - current iteration is through alerts - less intrusive to user experience
    <div aria-live="polite" aria-atomic="true" class="fixed-top">
        <div class="toast mx-auto " data-delay="2000" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header"><strong class="mr-auto">ЛинГА</strong><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>
            <div class="toast-body">Изчакайте да завърши възпроизвеждането на звук</div>
        </div>
    </div> -->
    <main>
        <div class="container">
            <div class="row text-center ">
                <div class="col mt-3">
                	<h3 class="mb-3">Изговори дума</h3>
    				<textarea class="form-control spelling" rows="3"></textarea>
    				<button class="buttonPron d-block mt-2 mx-auto btn btn-primary">Изговори</button>
                </div>
            </div>
        </div>
    </main>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha512-M5KW3ztuIICmVIhjSqXe01oV2bpe248gOxqmlcYrEzAvws7Pw3z6BK0iGbrwvdrUQUhi3eXgtxp5I8PDo9YfjQ==" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" integrity="sha512-7x3zila4t2qNycrtZ31HO0NnJr8kg2VI67YLoRSyi9hGhRN66FHYWr7Axa9Y1J9tGYHVBPqIjSE1ogHrJTz51g==" crossorigin="anonymous"></script>
    <script>
    feather.replace();
    </script>
    <script src="js/miscFunctions.js"></script>
    <c:if test="${not empty param.message}">
        <script type="text/javascript">
        showAndHideAlert();
        </script>
    </c:if>
</body>

</html>