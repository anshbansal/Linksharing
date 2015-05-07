<%@ page import="com.ttd.linksharing.domain.DocumentResource" %>
<g:if test="${post.class == DocumentResource.class}" >
    <g:link controller="documentResource" action="download" params="['documentResource.id', post.id]">
        Download
    </g:link>
</g:if>
<g:else>
<a href="${post.url}" target="_blank">View Full Site</a>
</g:else>
