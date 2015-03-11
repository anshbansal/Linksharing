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
        <ls:posts type="postsFor" userId="${user.id}"/>
    </section>

    <section class="minor-part left-part" id="topicsFor">
        <ls:topics type="topicsFor" userId="${user.id}"/>
    </section>
</body>
</html>