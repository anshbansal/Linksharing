<g:applyLayout name="container" params="[title: title]">
    <g:render template="/templates/post"
              var="post" collection="${posts}"
              model="[user: user]"  />
</g:applyLayout>