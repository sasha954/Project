<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs" %>
<c:choose>
    <c:when test="${sessionUser.role.name == 'Abiturient'}">
        <ul>
            <c:forEach var="tab" items="${tabs}">
                <li><a href="personalCabinet.do?tab=${tab}" id="${tab}">${tab}</a></li>
            </c:forEach>
        </ul>
        <c:choose>
            <c:when test="${tab == 'profile'}">
                <tabs:profileTab/>
            </c:when>
            <c:when test="${tab == 'my_faculties'}">
                <tabs:myFacultiesTab/>
            </c:when>
        </c:choose>
    </c:when>
    <c:when test="${sessionUser.role.name == 'Administrator'}">
        <ul>
            <c:forEach var="category" items="${categories}">
                <li><a href="personalCabinet.do?category=${category}" id="${category}">${category}</a></li>
            </c:forEach>
        </ul>
        <c:choose>
            <c:when test="${category == 'abiturients'}">
                <ul>
                    <c:forEach var="tab" items="${tabsUser}">
                        <li><a href="personalCabinet.do?category=${category}&tab=${tab}" id="${tab}">${tab}</a></li>
                    </c:forEach>
                </ul>
                <div>
                    <form action="personalCabinet.do?category=abiturients&tab=request" method="get">
                        <c:if test="${not empty facultyList}">
                            <select name="faculty-id">
                                <c:forEach var="subject" items="${facultyList}">
                                    <option value="${subject.id}">${subject.name}</option>
                                </c:forEach>
                            </select>
                            <input type="submit" value="Find">
                        </c:if>
                    </form>
                </div>
                <c:choose>
                    <c:when test="${tab == 'request'}">
                        <tabs:registeredOnFacultyUsers/>
                    </c:when>
                    <c:when test="${tab == 'enrolled'}">
                        <tabs:approvedAbiturientsTab/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${category == 'faculties'}">
                <tabs:faculties/>
            </c:when>
        </c:choose>
    </c:when>

</c:choose>