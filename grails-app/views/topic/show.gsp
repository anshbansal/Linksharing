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
        <ls:posts type="forTopic" topicId="${topic.id}" idToUpdate="postsForTopic" searchEnable="true"/>
    </section>
</body>
</html>