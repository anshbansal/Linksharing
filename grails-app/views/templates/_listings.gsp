<g:applyLayout name="container" params="[title: title]">
    <g:render template="${renderTemplate}" var="listing" collection="${listings}"
        model="['loggedUser': loggedUser]"/>
</g:applyLayout>