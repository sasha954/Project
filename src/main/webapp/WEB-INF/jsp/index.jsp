<%@include file="/WEB-INF/jspf/page.jspf" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="i18n">
<html>
<head>
    <c:set var="pageTitle" value="title.index" scope="page"/>
    <%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div class="container-fluid">
    <my:header/>
    <section class="main-content row">
        <div class="container">
            <div class="row">

                <!-- Single button -->
                <div class="btn-group col-md-12">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        Sort <span class="glyphicon glyphicon-sort"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="sort-btn" href="sort.do?orderField=name&isDesc=false">Sort by name A-Z</a></li>
                        <li><a class="sort-btn" href="sort.do?orderField=name&isDesc=true">Sort by name Z-A</a></li>
                        <li><a class="sort-btn" href="sort.do?orderField=allPlace&isDesc=false">Sort by all places
                            from min to max</a></li>
                        <li><a class="sort-btn" href="sort.do?orderField=allPlace&isDesc=true">Sort by all places
                            from max to min</a></li>
                        <li><a class="sort-btn" href="sort.do?orderField=stateFunded&isDesc=false">Sort by funded state
                            places min-max</a></li>
                        <li><a class="sort-btn" href="sort.do?orderField=stateFunded&isDesc=true">Sort by funded state
                            places max-mim</a></li>
                        <%--<li role="separator" class="divider"></li>--%>
                    </ul>
                </div>
                <section id="faculties">
                    <c:if test="${not empty facultyList}">
                        <c:forEach var="faculty" items="${facultyList}">
                            <div class="col-md-4 faculty-container">
                                <header class="faculty-title">
                                    <h4>${faculty.name}</h4>
                                    <hr/>
                                </header>
                                <section class="faculty-info">
                                    <ul>
                                        <li>
                                            Subjects:
                                            <ul>
                                                <c:forEach items="${faculty.subjectList}" var="subject">
                                                    <li id="subject-${subject.id}">${subject.name}</li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                        <li>Funded state place: ${faculty.countStateFundedPlace}</li>
                                        <li>All place: ${faculty.allPlace}</li>
                                    </ul>
                                </section>
                                <c:if test="${not empty sessionScope.sessionUser and sessionScope.sessionUser.role.name != 'Administrator'}">
                                    <div class="register-button">
                                        <button class="btn btn-danger faculty-reg" data-toggle="modal"
                                                data-target="#regFacultyModal" value="${faculty.id}"
                                                <c:if test="${fn:contains(userFacultyList, faculty)}">disabled</c:if>>
                                            Register
                                        </button>
                                    </div>
                                    <div class="modal fade" id="regFacultyModal" tabindex="-1" role="dialog"
                                         aria-labelledby="myModalLabel">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="registration-form-container">
                                                    <form action="/registrationInFaculty.do" id="reg-faculty-form"
                                                          method="post">
                                                        <c:forEach var="subject" items="${faculty.subjectList}">
                                                            <div class="form-group">
                                                                <label for="subj-mark-${subject.id}"
                                                                       id="subj-mark-${subject.id}">${subject.name}</label>
                                                                <input type="text" name="${subject.name}"
                                                                       id="subj-mark-${subject.id}"
                                                                       class="form-control">
                                                            </div>
                                                        </c:forEach>

                                                        <div class="form-group">
                                                            <input type="hidden" name="faculty-id" id="faculty-id"
                                                                   value="${faculty.id}"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <input type="submit" value="Register"
                                                                   class="btn btn-danger">
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:if>
                </section>

                <%--<div class="pagination">
                    <nav aria-label="Page navigation">
                        <ul class="pagination">
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>--%>
            </div>
        </div>


    </section>
</div>
<%@include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
</fmt:bundle>