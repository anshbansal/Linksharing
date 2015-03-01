<%@ page import="com.ttd.linksharing.enums.Visibility" %>
<asset:stylesheet src="application.css"/>

<g:applyLayout name="container" params="[title: title]">
    <g:form action="login" class="lines-container">
        <label for="topicName">Name</label>
        <g:textField name="topicName" required="true"/>

        <label for="topicVisibility">Password</label>
        <g:select name="topicVisibility" from="${Visibility}"/>

        <span></span>
        <span>
            <g:submitButton name="save"/>
            <g:submitButton name="cancel"/>
        </span>
    </g:form>
</g:applyLayout>
