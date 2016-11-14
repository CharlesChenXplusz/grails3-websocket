<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>

    <asset:javascript src="jquery"/>
    <asset:javascript src="spring-websocket"/>

    <script type="text/javascript">
        $(function () {
            var socket = new SockJS("${createLink(uri: '/stomp')}");
            var client = Stomp.over(socket);

            client.connect({}, function () {
                client.subscribe("/topic/hello", function (message) {
                    print(JSON.parse(message.body));
                });

                client.subscribe("/app/reply");
            });

            $("#helloButton").click(function () {
                client.send("/app/hello", {}, JSON.stringify($('#msg').val()));
            });

            $("#singleButton").click(function () {
                client.send("/app/reply", {}, JSON.stringify($('#msg').val()));

            });

            function print(msg) {
                var div = $("#helloDiv");
                div.append(msg);
                div.append('<br>');
            }
        });
    </script>
</head>

<body>
<input type="text" name="msg" id="msg">
<button id="helloButton">hello</button>
<button id="singleButton">single</button>

<div id="helloDiv"></div>
</body>
</html>