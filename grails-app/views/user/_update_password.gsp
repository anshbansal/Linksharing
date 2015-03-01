<g:applyLayout name="container" params="[title: 'Change Password']">
    <g:if test="${flash.message}">
        ${flash.message}
    </g:if>

    <g:form controller="login" action="updatePassword" class="lines-container tooltipster-form" method="post"
            name="password-update-form">
        <label for="password">Password</label>
        <g:passwordField name="password" required="true"/>

        <label for="rePassword">Confirm Password</label>
        <g:passwordField name="rePassword" required="true"/>

        <span></span>
        <g:submitButton name="Update"/>
    </g:form>
</g:applyLayout>