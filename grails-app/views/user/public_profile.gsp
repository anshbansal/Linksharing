<html>
<head>
    <meta name="layout" content="main">
    <title>User</title>
</head>

<body>
    <section class="minor-part left-part">
        <ls:user user="${user}"/>
    </section>

    <section class="major-part right-part" id="postsFor">
        <ls:posts type="postsFor" userId="${user.id}" searchEnable="true"/>
    </section>

    <section class="minor-part left-part" id="topicsFor">
        <ls:topics type="topicsFor" userId="${user.id}" searchEnable="true"/>
    </section>
</body>
</html>