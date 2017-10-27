<%@tag body-content="empty" pageEncoding="UTF-8" language="java" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<c:if test="${not empty complaintsList}">
    <c:forEach var="complaint" items="${complaintsList}">
        <div>
            <p>${complaint.faculty.name}</p>
            <p>Subjects : </p>
            <c:forEach var="mark" items="${complaint.marksList}">
                <p>${mark.subject.name} : ${mark.value}</p>
            </c:forEach>
            <br>
        </div>
    </c:forEach>
</c:if>