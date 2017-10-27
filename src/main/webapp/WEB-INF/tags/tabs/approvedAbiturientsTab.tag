<%@tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<div>
    <c:if test="${not empty approvedAbiturients}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Full name</th>
                <th>Faculty</th>
                <th>Sum marks</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${approvedAbiturients}" var="complaint">
                <tr>
                    <td>${complaint.user.lastName} ${complaint.user.firstName} ${complaint.user.middleName}</td>
                    <td>${complaint.faculty.name}</td>
                    <td>${complaint.summaryMark}
                        <p>Subjects : </p>
                        <c:forEach var="mark" items="${complaint.marksList}">
                            <p>${mark.subject.name} : ${mark.value}</p>
                        </c:forEach>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>