<%@include file="/WEB-INF/jspf/taglib.jspf"%>
<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<div class="add-faculty-form">
    <form action="addFaculty.do" method="post" id="addFacultyForm">
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
            <input type="submit" class="sign-in btn btn-danger" id="addFaculty" value="Add faculty">
        </div>
    </form>
</div>