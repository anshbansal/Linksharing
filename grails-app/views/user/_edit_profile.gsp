<g:applyLayout name="container" params="[title: 'Profile']">
    <g:form controller="user" action="updateDetails" enctype="multipart/form-data" class="lines-container tooltipster-form"
            name="details-update-form">
        <label for="firstName">First Name</label>
        <g:textField name="firstName" required="true" value="${loggedUser.firstName}"/>

        <label for="lastName">Last Name</label>
        <g:textField name="lastName" required="true" value="${loggedUser.lastName}"/>

        <label for="username">Username</label>
        <g:textField name="username" required="true" value="${loggedUser.username}"/>

        <label for="photo">Photo</label>
        <input type="file" name="photo" id="photo"/>

        <span></span>
        <g:submitButton name="Update"/>
    </g:form>
</g:applyLayout>