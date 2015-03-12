<g:link uri="/user/show/${user.id}">
    <g:if test="${user.photo == null}">
        <asset:image src="anonymous.jpg" width="40em" height="50em" alt="Anonymous"/>
    </g:if>
    <g:else>
        <img src="${createLink(controller: 'user', action: 'photo', id: user.id)}" style="width: 4em; height: 5em"/>
    </g:else>
</g:link>