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
                    print(message.body);
                });

                client.subscribe("/app/reply", function (message) {
                    print(message.body);
                });

                client.subscribe("/user/queue/replyToYourself", function (message) {
                    print(message.body);
                });

                client.subscribe("/user/queue/exchangePrivate", function (message) {
                    print(message.body);
                });
            });

            $("#helloButton").click(function () {
                client.send("/app/hello", {}, JSON.stringify($('#msg').val()));
            });

            $("#singleButton").click(function () {
                client.send("/app/sendToUserPrivate-userb", {}, JSON.stringify($('#msg').val()));

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

<br>

<button id="singleButton">send to user b</button>

<div id="helloDiv"></div>
</body>
</html>