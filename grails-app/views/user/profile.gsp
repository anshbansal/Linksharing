<html>
<head>
    <meta name="layout" content="main">
    <title>Edit Profile</title>
</head>

<body>
    <section class="minor-part left-part">
        <section class="single-container">
            <ls:user user="${session?.loggedUser}"/>
        </section>
    </section>

    <section class="major-part right-part">
        <tmpl:edit_profile />
    </section>

    <section class="minor-part left-part" id="subscriptions">
        <ls:topics type="subscriptions" title="Topics" searchEnable="true" max="10"/>
    </section>

    <section class="major-part right-part">
        <tmpl:update_password />
    </section>

</body>
</html>