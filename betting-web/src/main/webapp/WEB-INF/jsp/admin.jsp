<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Administration panel</title>
</head>
<body>

<h1>Application log files</h1>

<ul>
    <c:forEach items="${logFileNames}" var="logFileName">
        <li><A HREF="admin/download?filename=${logFileName}">${logFileName}</a><br></li>
    </c:forEach>
</ul>

</body>
</html>
