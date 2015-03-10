<g:applyLayout name="container" params="[title: 'Login']">
    <ls:flash type="loginMessage"/>
    <g:form controller="login" class="lines-container tooltipster-form" name="login_form">
        <label for="uniqueIdentifier">Email/Username</label>
        <g:textField name="uniqueIdentifier" required="true"/>

        <label for="loginPassword">Password</label>
        <g:passwordField name="loginPassword" required="true"/>

        <span id="forgot_password">Forgot Password</span>
        <g:submitButton name="Login"/>
    </g:form>
</g:applyLayout>