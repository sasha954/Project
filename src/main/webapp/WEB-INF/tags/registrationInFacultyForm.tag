<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<%@tag body-content="empty" language="java" pageEncoding="UTF-8" %>
<div class="registration-form-container">
    <form action="/registrationInFaculty.do" id="reg-faculty-form" method="post">
        <c:forEach var="subject" items="${faculty.subjectList}">
        <div class="form-group">
            <label for="subj-mark-${subject.id}" id="first-subj-mark-id">${subject.name}</label>
            <input type="text" name="${subject.name}" id="subj-mark-${subject.id}" class="form-control">
        </div>
        </c:forEach>

        <div class="form-group">
            <input type="hidden" name="faculty-id" id="faculty-id" value=""/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-danger"><fmt:message key="button.registration"/> </button>
        </div>
    </form>
</div>