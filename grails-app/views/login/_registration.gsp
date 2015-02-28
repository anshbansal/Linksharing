<g:applyLayout name="container" params="[title: 'Register']">
    <g:form controller="user" action="register" enctype="multipart/form-data" class="lines-container tooltipster-form" name="registration-form">
        <label for="firstName">First Name</label>
        <g:textField name="firstName" required="true"/>

        <label for="lastName">Last Name</label>
        <g:textField name="lastName" required="true"/>

        <label for="email">Email</label>
        <g:textField name="email" required="true"/>

        <label for="username">Username</label>
        <g:textField name="username" required="true"/>

        <label for="password">Password</label>
        <g:passwordField name="password" required="true"/>

        <label for="rePassword">Confirm Password</label>
        <g:passwordField name="rePassword" required="true"/>

        <label for="photo">Photo</label>
        <input type="file" name="photo" id="photo"/>

        <span></span>
        <g:submitButton name="Register"/>
    </g:form>
</g:applyLayout>