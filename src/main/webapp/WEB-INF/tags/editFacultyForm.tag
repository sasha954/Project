<%@include file="/WEB-INF/jspf/taglib.jspf"%>
<%@tag body-content="empty" pageEncoding="UTF-8" language="java" %>
<div class="add-faculty-form">
    <form action="updateFaculty.do" method="post" id="editFacultyForm">
        <div class="form-group">
            <input type="text" name="name" class="form-control" placeholder="Name"/>
        </div>
        <div class="form-group">
            <select name="first_subject">
                <c:if test="${not empty subjectsList}">
                    <c:forEach var="subject" items="${subjectsList}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="form-group">
            <select name="second_subject">
                <c:if test="${not empty subjectsList}">
                    <c:forEach var="subject" items="${subjectsList}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="form-group">
            <select name="third_subject">
                <c:if test="${not empty subjectsList}">
                    <c:forEach var="subject" items="${subjectsList}">
                        <option value="${subject.id}">${subject.name}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div class="form-group">
            <input type="text" name="count_state_funded_place" class="form-control" placeholder="Count state funded place"/>
        </div>
        <div class="form-group">
            <input type="text" name="count_all_place" class="form-control" placeholder="Count all place"/>
        </div>
        <div class="form-group">
            <input type="hidden" name="id" class="form-control" placeholder="Count all place"/>
        </div>
        <div class="form-group">
            <input type="submit" class="sign-in btn btn-danger" id="addFaculty" value="Update">
        </div>
    </form>
</div>