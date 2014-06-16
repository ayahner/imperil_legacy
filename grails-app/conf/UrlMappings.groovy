class UrlMappings {

  static mappings = {
    "/$controller/$action?/$id?(.$format)?"{ constraints {
        // apply constraints here
      }  }
    //
    "/"(view:"/index")
    "/index"(view:"/index")
    "/sample"(view:"/sample")
    "/pages/home"(view:"/pages/home")
    "/pages/match"(view:"/pages/match")
    "/pages/settings"(view:"/pages/settings")
    "/pages/tools"(view:"/pages/tools")
    "/pages/boardmap"(view:"/pages/boardmap")
    "500"(view:'/error')
  }
}
