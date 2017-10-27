<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ tag body-content="empty" language="java" pageEncoding="UTF-8" %>
<div class="registration-form">
    <form action="registration.do" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <input type="email" name="email" class="form-control" placeholder="Email" value="${userDto.email}">
            <c:if test="${not empty regErrors.email}">
                <div class="error-message">
                    <p class="error">${regErrors.email}</p>
                </div>
            </c:if>

        </div>
        <div class="form-group">
            <input type="password" name="password" class="form-control" placeholder="Password">
            <c:if test="${not empty regErrors.password}">
                <div class="error-message">
                    <p class="error">${regErrors.password}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="password" name="repeat_password" class="form-control" placeholder="Repeat password">
            <c:if test="${not empty regErrors.repeat_password}">
                <div class="error-message">
                    <p class="error">${regErrors.repeat_password}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="text" name="first_name" class="form-control" placeholder="First name" value="${userDto.firstName}">
            <c:if test="${not empty regErrors.first_name}">
                <div class="error-message">
                    <p class="error">${regErrors.first_name}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="text" name="last_name" class="form-control" placeholder="Last name" value="${userDto.lastName}">
            <c:if test="${not empty regErrors.last_name}">
                <div class="error-message">
                    <p class="error">${regErrors.last_name}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="text" name="middle_name" class="form-control" placeholder="Middle name" value="${userDto.middleName}">
            <c:if test="${not empty regErrors.middle_name}">
                <div class="error-message">
                    <p class="error">${regErrors.middle_name}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="text" name="region" class="form-control" placeholder="Region" value="${userDto.region}">
        </div>
        <div class="form-group">
            <input type="text" name="city" class="form-control" placeholder="City" value="${userDto.city}">
        </div>
        <div class="form-group">
            <input type="text" name="school" class="form-control" placeholder="School" value="${userDto.school}">
        </div>
        <!--<div class="form-group">
            <input type="file" name="certificate" class="form-control" accept="image/*">
        </div>-->
        <div class="form-group">
            <button type="submit" class="btn-danger btn" ><fmt:message key="button.registration"/> </button>
        </div>
    </form>
</div>