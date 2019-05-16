<%--
  Created by IntelliJ IDEA.
  User: KK
  Date: 2017-07-31
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="common/imports.jsp" %>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/ProposedMatchMappings.js"></script>
    <title>Bettors Page</title>
</head>
<br>

<br/>

<table id="proposed-match-mappings-tbl"/>
<div id="proposed-match-mappings-pager"></div>
<br/>
<div>
    <input type="button" id="refresh-btn" value="Refresh match mappings">
    <input type="button" id="process-btn" value="Process selected match mappings">
</div>

<script type="text/javascript">
    ProposedMatchMappings.init();
</script>

</body>
</html>
