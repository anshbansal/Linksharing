<g:applyLayout name="container" params="[title: 'Send Invitation']">
    <g:form action="create" class="lines-container tooltipster-form" name="send_invite_form">
        <label for="emailOfUser">Email</label>
        <g:textField name="emailOfUser" required="true"/>

        <label for="inviteTopic.id">Topic</label>
        <ls:getTopicsForUser name="inviteTopic.id"/>

        <span></span>
        <span>
            <g:submitButton name="Invite"/>
            <g:submitButton name="cancel" class="cancelButton"/>
        </span>
    </g:form>
</g:applyLayout>
<asset:javascript src="send_invite.js"/>