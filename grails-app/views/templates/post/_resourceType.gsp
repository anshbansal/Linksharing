<%@ page import="com.ttd.linksharing.domain.DocumentResource" %>
<g:if test="${post.class == DocumentResource.class}" >
Download
</g:if>
<g:else>
<a href="${post.url}" target="_blank">View Full Site</a>
</g:else>