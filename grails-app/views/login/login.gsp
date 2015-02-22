<html>
<head>
    <meta name="layout" content="main">
    <title>Login</title>
</head>
<body>
    <div class="major-part">
        <ls:predefinedPosts postsType="recentShares"/>
    </div>

    <div class="minor-part">
        <g:applyLayout name="container" params="[title: 'Login']">
            <g:form action="login">
                Email<g:textField name="email"/><br/>
                Password<g:passwordField name="password"/><br/>
                <g:submitButton name="Login"/>
            </g:form>
        </g:applyLayout>
        <br/>

        <g:applyLayout name="container" params="[title: 'Register']">
            <g:form action="login">
                First Name <g:textField name="firstName"/><br/>
                Last Name <g:textField name="lastName"/><br/>
            </g:form>
        </g:applyLayout>
    </div>

</body>
</html>