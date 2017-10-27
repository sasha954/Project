<%@tag body-content="empty" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Full name</th>
            <th>Faculty</th>
            <th>Summary Mark</th>
            <th>Expression Date</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty usersOnFaculty}">
            <c:forEach var="complaint" items="${usersOnFaculty}">
                <c:if test="${complaint.status eq 'registered'}">
                    <tr>
                        <td>${complaint.user.lastName} ${complaint.user.firstName} ${complaint.user.middleName}</td>
                        <td>${complaint.faculty.name}</td>
                        <td>${complaint.summaryMark}
                          <%--  <c:forEach var="mark" items="${complaint.marksList}">
                                <p>${mark.subject.name} : ${mark.value}</p>
                            </c:forEach>--%>
                        </td>
                        <td>${complaint.expressionDate}</td>
                        <td><a class="btn btn-xs btn-danger"
                               id="accept-${complaint.user.id}${complaint.faculty.id}"
                               href="acceptAbiturient.do?userId=${complaint.user.id}&facultyId=${complaint.faculty.id}">Accept</a>
                        </td>
                        <td><a class="link" href="blockUser.do?userId=${complaint.user.id}"><span
                                class="glyphicon glyphicon-ban-circle"></span></a></td>
                    </tr>
                </c:if>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <c:if test="${not empty acceptedError}">
        <div class="notification">
            <c:choose>
                <c:when test="${not empty ecceptedError.acceptError}">
                    <p class="notification-message">${acceptedError.acceptError}</p>
                </c:when>
                <c:when test="${not empty ecceptedError.updateStatusError}">
                    <p class="notification-message">${acceptedError.updateStatusError}</p>
                </c:when>
                <c:when test="${not empty ecceptedError.acceptUserError}">
                    <p class="notification-message">${acceptedError.acceptUserError}</p>
                </c:when>
            </c:choose>
        </div>
    </c:if>
</div>