<g:applyLayout name="container" params="[title: 'Share Document']">
    <g:form action="create" class="lines-container tooltipster-form" name="create_documentResource_form">

        <label for="documentDescription">Description</label>
        <g:textArea name="documentDescription"/>

        %{--TODO Add validation that file is actually uploaded--}%
        <label for="document">Document</label>
        <input type="file" name="document" id="document"/>

        <label for="documentTopic.id">Topic</label>
        <ls:getTopicsForUser name="documentTopic.id"/>

        <span></span>
        <span>
            <g:submitButton name="Share"/>
            <g:submitButton name="cancel" class="cancelButton"/>
        </span>
    </g:form>
</g:applyLayout>
<asset:javascript src="forms/create_documentResource.js"/>