class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
		
		"/"(view:"/index")
		"/index"(view:"/index")
        "/greet"(view:"/includes/greet")
        "500"(view:'/error')
	}
}
