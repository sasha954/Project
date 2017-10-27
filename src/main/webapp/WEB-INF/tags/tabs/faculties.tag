<%@tag body-content="empty" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>First Subject</th>
            <th>Second Subject</th>
            <th>Third Subject</th>
            <th>Count State Funded Place</th>
            <th>Count All Place</th>
            <th colspan="2">
                <button data-toggle="modal" data-target="#addFacultyModal" class="btn btn-danger btn-sm add-btn"><span
                        class="glyphicon glyphicon-plus"></span> Add
                </button>
            </th>
        </tr>
        </thead>
        <tbody id="faculty-list">
        <c:if test="${not empty facultyList}">
            <c:forEach var="faculty" items="${facultyList}">
                <tr id="row-${faculty.id}">
                    <td>${faculty.name}</td>
                    <c:forEach var="subjects" items="${faculty.subjectList}">
                        <c:if test="${subjects.name != 'Certificate'}">
                        <td>${subjects.name}</td>
                        </c:if>

                    </c:forEach>
                    <td>${faculty.countStateFundedPlace}</td>
                    <td>${faculty.allPlace}</td>
                    <td>
                        <a class="link" href="/updateFaculty.do?faculty-id=${faculty.id}" id="update-${faculty.id}"
                                value="${faculty.id}"><span class="glyphicon glyphicon-edit"></span></a>
                    </td>
                    <td><a class="link" href="#" id="delete-${faculty.id}" value="${faculty.id}"><span
                            class="glyphicon glyphicon-trash"></span></a></td>

                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <div class="modal fade" id="addFacultyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <my:addFacultyForm/>
            </div>
        </div>
    </div>
    <div id="myModal" class="myModal fadeOut">
        <div class="modal-dialog" role="document">
            <div class="modal-content fadeOutUp">
                <span class="close"></span>
                <my:editFacultyForm/>
            </div>
        </div>

    </div>
</div>