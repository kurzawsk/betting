<%--
  Created by IntelliJ IDEA.
  User: KK
  Date: 2017-07-04
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <%@include file="common/imports.jsp" %>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/Bettors.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/canvasjs.min.js"></script>

    <title>Bettors Page</title>
</head>
<br>

<div>
    <input type="button" id="refresh-btn" value="Refresh">
</div>

<div id="bettor-table-div" style="position:relative; z-index: 1">
    <table id="bettors-tbl"/>
</div>
<div id="bettors-pager"></div>
<br/>


<div id="show-options-radio-div">
    <b>Show betting event types</b>
    <input type="hidden" id="selected-betting-event-types" value="ALL"/>
    <form action="">
        <input type="radio" name="betting-event-types" value="ALL" checked="true"> All<br>
        <input type="radio" name="betting-event-types" value="PENDING_MATCHES"> Pending matches<br>
        <input type="radio" name="betting-event-types" value="TOTAL_PROFIT_CHANGES"> Total profit changes<br>
    </form>
</div>
<div id="datepicker-div">
    <input type="checkbox" id="narrow-period" checked="true"/>
    <label for="narrow-period">Show events from between dates</label>
    <input type="text" id="from-date" name="from-date" style="position:relative; z-index: 2"/> -
    <input type="text" id="to-date" name="to-date" style="position:relative; z-index: 2">
    <input type="button" id="show-resources-chart-btn" value="Show Bettor Resources History Chart" disabled="true">
</div>
<table id="betting-events-tbl"/>
<div id="betting-events-pager"/>

<div id="bettor-resource-chart-div-container" >
    <div id="bettor-resource-chart-div" style="width:100%; height:600px;"/>
</div>
<script type="text/javascript">
    Bettors.init();
</script>

</body>
</html>
