<header class="header">
    <g:set var="loggedUser" value="${session?.loggedUser}"/>

    <g:link uri="/">Link Sharing</g:link>
    <g:if test="${loggedUser?.username != null}">
        <a href="#" onclick="createPopup(createTopicTemplateURL)">Create Topic</a>
        <a href="#" onclick="createPopup(createLinkResourceTemplateURL)">Create Link</a>

        <span id="popup" hidden="hidden">
        </span>

        <nav>
            <div id="currentUserName">${loggedUser?.username}</div>
            <ul id="nav-menu">
                <li>
                    <g:link controller="user" action="profile">Profile</g:link>
                </li>
                <g:if test="${loggedUser.admin}">
                    <li>
                        <g:link controller="user" action="users">Users</g:link>
                    </li>
                    <li>Topics</li>
                    <li>Posts</li>
                </g:if>
                <li>
                    <g:link controller="login" action="logout">Logout</g:link>
                </li>
            </ul>
        </nav>
    </g:if>
    <span class="searchBox">
        <ls:searchBox/>
    </span>
</header>
