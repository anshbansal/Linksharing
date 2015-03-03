<g:applyLayout name="container" params="[title: 'Profile']">
    <ls:flash type="editProfileMessage"/>
    <g:form controller="user" action="updateDetails" enctype="multipart/form-data" class="lines-container tooltipster-form"
            name="details_update_form">
        <label for="firstName">First Name</label>
        <g:textField name="firstName" required="true" value="${loggedUser.firstName}"/>

        <label for="lastName">Last Name</label>
        <g:textField name="lastName" required="true" value="${loggedUser.lastName}"/>

        <label for="email">Email</label>
        <g:textField name="email" required="true" value="${loggedUser.email}"/>

        <label for="photo">Photo</label>
        <input type="file" name="photo" id="photo"/>

        <span></span>
        <g:submitButton name="Update"/>
    </g:form>
</g:applyLayout>