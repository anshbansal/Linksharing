<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Login</title>
</head>
<body>
    <g:form action="login">
        Email<g:textField name="email"/>
        Password<g:passwordField name="password"/>
        <g:submitButton name="Submit"/>
    </g:form>
</body>
</html>