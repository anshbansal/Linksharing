<html>
<head>
    <meta name="layout" content="main">
    <title>Edit Profile</title>
</head>

<body>
    <section class="minor-part left-part">
        <ls:user user="${session?.loggedUser}"/>
    </section>

    <section class="major-part right-part">
        <tmpl:edit_profile />
    </section>

    <section class="minor-part left-part" id="subscriptions">
        <ls:topics type="subscriptions" title="Topics" searchEnable="true"/>
    </section>

    <section class="major-part right-part">
        <tmpl:update_password />
    </section>

</body>
</html>