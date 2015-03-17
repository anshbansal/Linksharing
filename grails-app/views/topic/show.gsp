<html>
<head>
    <meta name="layout" content="main">
    <title>Topic show</title>
</head>

<body>
    <section class="minor-part left-part">
        <g:applyLayout name="container">
                <content tag="title">
                    Topic: "${topic.name}"
                </content>
                <ls:topic topic="${topic}"/>
        </g:applyLayout>
    </section>

    <section class="major-part right-part" id="postsForTopic">
        <ls:posts type="forTopic" topicId="${topic.id}" idToUpdate="postsForTopic" searchEnable="true" max="10"/>
    </section>

    <section class="minor-part left-part" id="usersForTopic">
        <ls:users type="forTopic" topicId="${topic.id}" idToUpdate="usersForTopic" max="10"/>
    </section>
</body>
</html>