<g:applyLayout name="container" params="[title: 'Share Link']">
    <g:form action="create" class="lines-container tooltipster-form" name="create_linkresource_form">
        <label for="linkUrl">Link</label>
        <g:textField name="linkUrl" required="true"/>

        <label for="linkDescription">Description</label>
        <g:textArea name="linkDescription"/>

        <label for="linkTopics">Topic</label>
        <ls:getTopicsForUser name="linkTopics"/>

        <span></span>
        <span>
            <g:submitButton name="save"/>
            <g:submitButton name="cancel" class="cancelButton"/>
        </span>
    </g:form>
</g:applyLayout>
<asset:javascript src="create_linkresource.js"/>