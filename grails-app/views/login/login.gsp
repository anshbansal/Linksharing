<html>
<head>
    <meta name="layout" content="main">
    <title>Login</title>
</head>
<body>
    <section class="major-part left-part">
        <ls:predefinedPosts postsType="recentShares"/>
    </section>

    <section class="minor-part right-part">
        <g:applyLayout name="container" params="[title: 'Login']">
            <g:form action="login" class="lines-container">
                <span>Email *</span>
                <g:textField name="email" required="true"/>

                <span>Password *</span>
                <g:passwordField name="password" required="true"/>

                <span>Forgot Password</span>
                <g:submitButton name="Login"/>
            </g:form>
        </g:applyLayout>
    </section>

    <section class="minor-part right-part">
        <g:applyLayout name="container" params="[title: 'Register']">
            <g:form action="login" class="lines-container">
                <span>First Name *</span>
                <g:textField name="firstName" required="true"/>

                <span>Last Name *</span>
                <g:textField name="lastName" required="true"/>

                <span>Email *</span>
                <input type="email" name="email" required="true"/>

                <span>Username *</span>
                <g:textField name="username" required="true"/>

                <span>Password *</span>
                <g:passwordField name="password" required="true"/>

                <span>Confirm Password *</span>
                <g:passwordField name="rePassword" required="true"/>

                <span>Photo</span>
                <input type="file" name="photo"/>

                <span></span>
                <g:submitButton name="Register"/>
            </g:form>
        </g:applyLayout>
    </section>

</body>
</html>