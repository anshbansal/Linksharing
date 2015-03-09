class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //Mapping for rendering Posts
        "/resource/recentShares"(controller: 'util', action: 'renderPost')
        "/readingItem/inbox"(controller: 'util', action: 'renderPost')

        "/"(controller: "home")
        "500"(view:'/error')
	}
}
