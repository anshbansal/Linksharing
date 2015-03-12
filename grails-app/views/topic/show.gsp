<html>
<head>
    <meta name="layout" content="main">
    <title>Topic show</title>
</head>

<body>
    <section class="major-part left-part">
        <g:applyLayout name="container">
                <content tag="title">
                    Topic: "${topic.name}"
                </content>
                <ls:topic topic="${topic}"/>
        </g:applyLayout>
    </section>
</body>
</html>