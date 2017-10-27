<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ tag body-content="empty" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="i18n">
    <header class="main-head row">
        <div class="title-container col-md-4 col-md-offset-1">
            <a href="/" class="header-link"><h2 class="header-title">My University</h2></a>
        </div>
        <c:if test="${not empty sessionScope.sessionUser}">
            <div class="user-container col-md-2 col-md-offset-4">
                <a href="#" class="user-button dropdown-toggle" data-toggle="dropdown"
                   aria-haspopup="true"
                   aria-expanded="false">${sessionScope.sessionUser.firstName} ${sessionScope.sessionUser.lastName}
                    <span class="glyphicon glyphicon-triangle-bottom"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="personalCabinet.do">Personal Cabinet</a></li>
                    <li><a href="logout.do">Logout</a></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${empty sessionScope.sessionUser}">
            <div class="sign-in-container col-md-2 col-md-offset-4">
                <a href="#" class="sign-in-button" data-toggle="modal" data-target="#singInModal"><span
                        class="glyphicon glyphicon-log-in"></span><fmt:message key="button.login"/> </a>
            </div>
            <div class="modal fade" id="singInModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <my:loginFrom/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="btn-group col-md-12">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                Sort <span class="glyphicon glyphicon-sort"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">EN </a></li>
                <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">RU</a></li>
                <li><a href="${requestScope['javax.servlet.forward.request_uri']}?lang=uk">UK</a></li>
            </ul>
        </div>

    </header>
</fmt:bundle>