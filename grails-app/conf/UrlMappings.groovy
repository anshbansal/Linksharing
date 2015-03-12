class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/listingsPost/$postType" {
            controller = 'util'
            action = 'renderPost'
        }

        "/listingsTopic/$topicType" {
            controller = 'util'
            action = 'renderTopic'
        }

        "/listingsUser/$topicType" {
            controller = 'util'
            action = 'renderUser'
        }

        "/"(controller: "home", action: "home")
        "500"(view:'/error')
	}
}
