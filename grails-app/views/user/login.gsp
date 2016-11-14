<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>login</title>
</head>

<body>
<g:form url="${webRequest.contextPath}/login/authenticate">
    <input name="username" type="text" value="usera" required/>
    <input name="password" type="password" value="usera" required/>
    <input type="submit" value="submit">
</g:form>
</body>
</html>