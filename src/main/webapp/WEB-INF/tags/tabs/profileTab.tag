<%@tag body-content="empty" pageEncoding="UTF-8" language="java" %>
<%@include file="/WEB-INF/jspf/taglib.jspf"%>

<c:if test="${not empty sessionUser}">
    <table>
        <tr>
            <td>Full name:</td>
            <td>${sessionUser.lastName} ${sessionUser.firstName} ${sessionUser.middleName}</td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>${sessionUser.email}</td>
        </tr>
        <tr>
            <td>Region:</td>
            <td>${sessionUser.region}</td>
        </tr>
        <tr>
            <td>City:</td>
            <td>${sessionUser.city}</td>
        </tr>
        <tr>
            <td>School:</td>
            <td>${sessionUser.school}</td>
        </tr>
    </table>


</c:if>
