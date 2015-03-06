<header class="header">
    <g:link uri="/">Link Sharing</g:link>
    <g:if test="${loggedUser?.username != null}">
        <a href="#" onclick="createPopup(createTopicTemplateURL)">Create Topic</a>

        <span id="popup" hidden="hidden">
        </span>

        <nav>
            <div id="currentUserName">${loggedUser?.username}</div>
            <ul id="nav-menu">
                <li>
                    <a href="${createLink(controller: "user", action: "profile")}">Profile</a>
                </li>
                <g:if test="${loggedUser.admin}">
                    <li>Users</li>
                    <li>Topics</li>
                    <li>Posts</li>
                </g:if>
                <li>
                    <a href="${createLink(controller: "login", action: "logout")}">Logout</a>
                </li>
            </ul>
        </nav>
    </g:if>
    <g:render template="/templates/commons/search"/>
</header>
