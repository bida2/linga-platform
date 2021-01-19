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
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>LinGA</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
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
	<main>
	<div class="container">
		<div class="row text-center ">
			<div class="col mt-3">
			<h3>Думи, започващи с "${param.letter}"</h3>
				<div class="${not empty words ? 'mt-4' : '' } alert alert-warning alert-dismissible init-height-alert alert-transition" role="alert">
                                <strong>Все още се възпроизвежда звук - моля, изчакайте!</strong>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <c:if test="${not empty words}">
		                 <div class="table-responsive">
                                <table class="table table-hover table-bordered card-table-bottom-margin">
                                    <thead>
                                        <tr>
                                            <th class="th-min-width table-header-thickness bg-primary text-light align-middle">Дума, изписана на български</th>
                                            <th class="th-min-width table-header-thickness bg-primary text-light align-middle">Значение на думата</th>
                                            <th class="th-min-width table-header-thickness bg-primary text-light align-middle">Произношение и оригинално изписване</th>
                                            <th class="th-min-width table-header-thickness bg-primary text-light align-middle">Пример с изречение</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${not empty param.wordSubmit}">
                                        <c:forEach var="word" items="${words}">
                                            <tr>
                                                <td>
                                                     ${word.getWord()}<sup><i class="ml-1 popovers" data-feather="help-circle" data-placement="bottom" data-toggle="popover" tabindex="0" role="button" data-trigger="focus" data-content="${word.getAltSpellings().isEmpty() == false ? String.join(',', word.getAltSpellings()) : 'Няма други изписвания засега!'}" data-title="Други изписвания"></i></sup>
                                                </td>
                                                <td>
                                                    ${word.getMeaning()}
                                                </td>
                                                <td class="align-middle-cell align-top-cell">
                                                    <span class="d-none spelling">${word.getSpelling()}</span> <span class="pronounce btn btn-outline-primary btn-outline-trans pointer"><i data-feather="mic"></i> ${word.getSpelling()}</span>
                                                </td>
                                                <td>
                                                    ${word.getExampleSent()}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${words.isEmpty()}">
                                            <p class="mt-3">
                                                Не можахме да намерим '<span class="text-danger font-weight-bold">${param.wordSearch}</span>'!
                                            </p>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${!words.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="word" items="${words}">
                                                <!-- <div class="row border-left border-right border-bottom border-primary bg-light round-border-rows-bottom">
                                <div class="col border-right border-primary align-center-vertically pt-2 pb-2" >
                                    <p>${randWord.getWord()}<sup><i class="ml-1" data-feather="help-circle" data-toggle="popover" data-content="${randWord.getAltSpellings().isEmpty() == false ? String.join(',', randWord.getAltSpellings()) : 'Няма други изписвания засега!'}" data-title="Други изписвания" data-trigger="hover"></i></sup></p> 
                                   </div>
                                <div class="col border-right border-primary align-center-vertically pt-2 pb-2">
                                    ${randWord.getMeaning()}</div>
                                <div class="col border-right border-primary align-center-vertically pt-2 pb-2">
                                    <span class="d-none spelling">${randWord.getSpelling()}</span> <span class="pronounce btn btn-outline-primary btn-outline-trans pointer"><i data-feather="mic"></i> ${randWord.getSpelling()}</span>
                                </div>
                                <div class="col align-center-vertically pt-2 pb-2">${randWord.getExampleSent()}</div>
                            </div> -->
                                                <tr>
                                                    <td>
                                                         ${word.getWord()}<sup><i class="ml-1 popovers" data-feather="help-circle" data-placement="bottom" data-toggle="popover" tabindex="0" role="button" data-trigger="focus" data-content="${word.getAltSpellings().isEmpty() == false ? String.join(',', word.getAltSpellings()) : 'Няма други изписвания засега!'}" data-title="Други изписвания"></i></sup>
                                                    </td>
                                                    <td>
                                                        ${word.getMeaning()}
                                                    </td>
                                                    <td class="align-middle-cell align-top-cell">
                                                        <span class="d-none spelling">${word.getSpelling()}</span> <span class="pronounce btn btn-outline-primary btn-outline-trans pointer"><i data-feather="mic"></i> ${word.getSpelling()}</span>
                                                    </td>
                                                    <td>
                                                        ${word.getExampleSent()}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                </table>
                            </div>
                            </c:if>
                            </c:if>
			</div>
		</div>
	</div>
	</main>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" integrity="sha512-7x3zila4t2qNycrtZ31HO0NnJr8kg2VI67YLoRSyi9hGhRN66FHYWr7Axa9Y1J9tGYHVBPqIjSE1ogHrJTz51g==" crossorigin="anonymous"></script>
    <script>
    	feather.replace();
    </script>
	<script src="js/voiceSynth.js"></script>
	 <script src="js/miscFunctions.js"></script>
    <script>popoverBehavior();</script>
</body>
</html>