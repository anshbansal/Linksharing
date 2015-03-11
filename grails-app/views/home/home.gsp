<html>
<head>
    <meta name="layout" content="main">
    <title>Login</title>
</head>
<body>
    <section class="major-part left-part" id="recentShares">
        <ls:posts type="recentShares"/>
    </section>

    <section class="minor-part right-part">
        <tmpl:login/>
    </section>

    <section class="minor-part right-part">
        <tmpl:registration/>
    </section>

    <section class="major-part left-part">
        <ls:posts type="topPosts"/>
    </section>
</body>
</html>