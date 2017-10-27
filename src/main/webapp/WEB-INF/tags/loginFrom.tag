<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<%@tag body-content="empty" language="java" pageEncoding="UTF-8" %>

<div class="login-form">
    <form action="signIn.do" id="loginForm" method="post">
        <div class="form-group">
            <input type="email" name="email" class="form-control" placeholder="Email"/>
            <c:if test="${not empty authorizationError.email}">
                <div class="error-message">
                    <p id="error-email">${authorizationError.email}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <input type="password" name="password" class="form-control" placeholder="Password">
            <c:if test="${not empty authorizationError.password}">
                <div class="error-message">
                    <p id="error-password">${authorizationError.password}</p>
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <button type="submit" class="sign-in btn btn-danger"><fmt:message key="button.login"/> </button>
        </div>
        <c:if test="${not empty authorizationError.authorizationError}">
            <div class="error-message">
                <p id="error-auth">${authorizationError.authorizationError}</p>
            </div>
        </c:if>
    </form>

    <div class="links">
        <a href="registration.do" class="link"><fmt:message key="button.registration"/> </a>
    </div>
</div>
