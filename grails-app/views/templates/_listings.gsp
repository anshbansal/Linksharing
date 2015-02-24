<g:applyLayout name="container" params="[title: title]">
    <g:render template="${renderTemplate}"
              var="listing" collection="${listings}"
              model="[user: user]" />
</g:applyLayout>