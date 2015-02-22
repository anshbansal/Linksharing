<html>
<head>
    <meta name="layout" content="main">
    <title>Login</title>
</head>
<body>
    <g:applyLayout name="container" params="[title: 'Login']">
        <g:form action="login">
            Email<g:textField name="email"/>
            Password<g:passwordField name="password"/>
            <g:submitButton name="Submit"/>
        </g:form>
    </g:applyLayout>

</body>
</html>