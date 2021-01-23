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
    <title>ЛинГА</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
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
                    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalTitle" aria-hidden="true">
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
                    <c:if test="${not empty param.message}">
                        <div id="change-pass" class="alert alert-info alert-dismissible init-height-alert alert-transition" role="alert">
                            <strong><i class="mr-1 feather-info" data-feather="info"></i>${param.message}</strong>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                    </c:if>
                    <form method="POST" action="/" class="input-group">
                        <input type="text" class="ml-xl-auto mx-xl-0 mx-auto form-control" name="wordSearch" placeholder="Въведете думата си тук..."> <hr class="d-block d-xl-none force-break"> <input type="submit" class="ml-xl-2 mr-xl-auto mx-xl-0 mt-2 mt-xl-0 mx-auto btn btn-primary" name="wordSubmit" value="Търси"> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                    <c:if test="${!words.isEmpty()}">
                            <h3 class="${empty isRandom ? 'd-none' : ''} mt-3 mb-3 text-center">Случайни думи</h3>
                            <div class="${not empty words ? 'mt-4' : '' } alert alert-warning alert-dismissible init-height-alert alert-transition" role="alert">
                                <strong>Все още се възпроизвежда звук - моля, изчакайте!</strong>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        
                        <!-- <div class="row mt-4 border border-primary round-border-headers bg-primary text-gray-white">
                            <div class="col border  font-weight-bold align-center-vertically">
                                Дума, изписана на български</div>
                            <div class="col border  font-weight-bold align-center-vertically">
                                Значение на думата</div>
                            <div class="col border  font-weight-bold align-center-vertically">
                                Произношение и оригинално изписване</div>
                            <div class="col border font-weight-bold align-center-vertically">
                                Пример с изречение</div>
                        </div> -->
                        <c:if test="${not empty words}">
                        <div class="${not empty param.wordSubmit ? 'mt-4' : ''} card">
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
                                        <c:forEach var="word" items="${words}">
                                            <tr>
                                                <td>
                                                    ${word.getWord()}<sup><i class="ml-1 popovers" data-feather="help-circle" data-placement="bottom" data-toggle="popover" tabindex="0" role="button" data-trigger="focus" data-content="${word.getAltSpellings().isEmpty() == false ? String.join(',', word.getAltSpellings()) : 'Няма други изписвания засега!'}" data-title="Други изписвания"></i></sup>
                                                </td>
                                                <td>
                                                    ${word.getMeaning()}
                                                </td>
                                                <td class="align-middle">
                                                    <span class="d-none spelling">${word.getSpelling()}</span> <span class="pronounce btn btn-outline-primary btn-outline-trans pointer"><i data-feather="mic"></i> ${word.getSpelling()}</span>
                                                </td>
                                                <td>
                                                    ${word.getExampleSent()}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                         </table>
                                        </div>
                                        </div>
                                    </c:if>
             
                    </c:if>
                    <c:if test="${not empty noWordsFound}">
                    	<p class="font-weight-bold text-danger">${noWordsFound}</p>
                    </c:if>
                    <!-- Place a condition here, that will render a list of random word suggestions
				on page load  -->
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
    <script src="js/voiceSynth.js"></script>
    <script src="js/miscFunctions.js"></script>
    <script>popoverBehavior();</script>
    <c:if test="${not empty param.message}">
        <script type="text/javascript">
        showAndHideAlert();
        </script>
    </c:if>
</body>

</html>