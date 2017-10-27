<%@include file="/WEB-INF/jspf/taglib.jspf" %>
<%@include file="/WEB-INF/jspf/page.jspf" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>
<fmt:bundle basename="i18n">
    <html>
    <head>
        <c:set var="pageTitle" value="title.personalCanitet" scope="page"/>
        <%@include file="/WEB-INF/jspf/head.jspf" %>
    </head>
    <body>
    <div class="container-fluid">
        <my:header/>
        <section class="main-content row">
            <div class="container">
                <div class="row" style="background: #fff">
                    <div class="col-md-12">
                        <my:tabSwitcher/>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
    </html>
</fmt:bundle>