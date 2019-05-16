<html>
<body>
<h2>Hello World!!</h2>
<span id="clock"></span>

<script language="JavaScript">
    (function () {

        var clockElement = document.getElementById( "clock" );

        function updateClock ( clock ) {
            clock.innerHTML = new Date().toLocaleTimeString();
        }

        setInterval(function () {
            updateClock( clockElement );
        }, 1000);

    }());
</script>
</body>
</html>
