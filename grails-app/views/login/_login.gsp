<g:applyLayout name="container" params="[title: 'Login']">
    <g:form action="login" class="lines-container" name="login-form">
        <label for="uniqueIdentifier">Email/Username</label>
        <g:textField name="uniqueIdentifier" required="true"/>

        <label for="loginPassword">Password</label>
        <g:passwordField name="loginPassword" required="true"/>

        <span>Forgot Password</span>
        <g:submitButton name="Login"/>
    </g:form>
</g:applyLayout>