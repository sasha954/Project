<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="i18n">
<html>
<head>
    <c:set var="pageTitle" value="title.login" scope="page"/>
    <%@include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
    <div class="container-fluid">
        <my:header/>
        <div class="main-content row">
            <div class="container">
                <div class="row">
                    <my:loginFrom/>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
</fmt:bundle>