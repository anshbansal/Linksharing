<%@ page import="com.ttd.linksharing.enums.Visibility" %>

<g:applyLayout name="container" params="[title: 'Create Topic']">
    <g:form action="create" class="lines-container tooltipster-form" name="create_topic_form">
        <label for="topicName">Name</label>
        <g:textField name="topicName" required="true"/>

        <label for="topicVisibility">Visibility</label>
        <g:select name="topicVisibility" from="${Visibility}"/>

        <span></span>
        <span>
            <g:submitButton name="save"/>
            <g:submitButton name="cancel" class="cancelButton"/>
        </span>
    </g:form>
</g:applyLayout>
<asset:javascript src="create_topic.js"/>
