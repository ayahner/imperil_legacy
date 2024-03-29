
// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.app.context = "/"
grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = [
  'Gecko',
  'WebKit',
  'Presto',
  'Trident'
]
grails.mime.types = [
  all:           '*/*',
  atom:          'application/atom+xml',
  css:           'text/css',
  csv:           'text/csv',
  form:          'application/x-www-form-urlencoded',
  html:          [
    'text/html',
    'application/xhtml+xml'
  ],
  js:            'text/javascript',
  json:          [
    'application/json',
    'text/json'
  ],
  multipartForm: 'multipart/form-data',
  rss:           'application/rss+xml',
  text:          'text/plain',
  hal:           [
    'application/hal+json',
    'application/hal+xml'
  ],
  xml:           [
    'text/xml',
    'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = [
  '/images/*',
  '/css/*',
  '/js/*',
  '/plugins/*',
]

grails.resources.adhoc.excludes = [
  '**/WEB-INF/**',
  '**/META-INF/**',
  '/semantic/**'
]

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
  views {
    gsp {
      encoding = 'UTF-8'
      htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
      codecs {
        expression = 'html' // escapes values inside ${}
        scriptlet = 'html' // escapes output from scriptlets in GSPs
        taglib = 'none' // escapes output from taglibs
        staticparts = 'none' // escapes output from static template parts
      }
    }
    // escapes all not-encoded output at final stage of outputting
    filteringCodecForContentType {
      //'text/html' = 'html'
    }
  }
}

grails.converters.encoding = "UTF-8"
// grails.converters.json.default.deep = true
grails.converters.default.pretty.print = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
  development {
    grails.logging.jul.usebridge = true
    grails.serverURL = "http://localhost:8080"
    oauth.providers.facebook.key = '1491726954381036'
    oauth.providers.facebook.secret = 'a6347303f9ce99bd202cc60d6c30c808'
    oauth.providers.twitter.key = 'UMrdC55Gn5Wmxni9Q5kNqQ'
    oauth.providers.twitter.secret = 'pRFEUUyMdVUTUmk07u5JlQ2wHtptUwAKn0PSqZdjA'
  }
  production {
    grails.logging.jul.usebridge = false
    grails.serverURL = "http://imperil.herokuapp.com"
    oauth.providers.facebook.key = '502118066561182'
    oauth.providers.facebook.secret = 'a51a5b380023bf4fbada9e9edc232a0a'
    oauth.providers.twitter.key = 'yLHdR6BjH4tlVCgNEEiJEA'
    oauth.providers.twitter.secret = 'NSIUUCm57aUhGVGRnfeK5JpV32eEBXl1tsitQzpGf4'
  }
}

// log4j configuration
log4j = {
  // Example of changing the log pattern for the default console appender:
  //
  //appenders {
  console name:'stdout', threshold:org.apache.log4j.Level.INFO, layout:pattern(conversionPattern: '%d{dd MMM yyyy HH:mm:ss} %p %c{1}(%L) %m%n')
  rollingFile name:"fileAppender", maxBackupIndex:10, fileName:"imperil.log"
  //}

  error  'org.codehaus.groovy.grails.web.servlet',        // controllers
      'org.codehaus.groovy.grails.web.pages',          // GSP
      'org.codehaus.groovy.grails.web.sitemesh',       // layouts
      'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
      'org.codehaus.groovy.grails.web.mapping',        // URL mapping
      'org.codehaus.groovy.grails.commons',            // core / classloading
      'org.codehaus.groovy.grails.plugins',            // plugins
      'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
      'org.springframework',
      'org.hibernate',
      'net.sf.ehcache.hibernate'

  // Set level for all application artifacts
  info 'grails.app'
  // Set for a specific controller in the default package

  trace 'grails.app.controllers',
      'grails.app.domain', 'com.imperil'

  root {
    info 'stdout'
    trace 'fileAppender'
    // warn 'stdout','file'
    additivity = true
  }
}


grails.plugin.springsecurity.password.algorithm='SHA-512'


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.authority.className = 'com.dynamix.role.Role'
grails.plugin.springsecurity.oauth.domainClass = 'com.dynamix.oauth.OAuthID'
grails.plugin.springsecurity.requestMap.className = 'com.dynamix.auth.Requestmap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'
grails.plugin.springsecurity.secureChannel.useHeaderCheckChannelSecurity = true
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.dynamix.user.AppUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.dynamix.usertorole.AppUserToRole'

grails.gorm.default.constraints = { '*'(nullable: true) }

springMobile { deviceResolver="wurfl" }

oauth {
  providers {
    facebook {
      api = org.scribe.builder.api.FacebookApi
      successUri = '/oauth/facebook/success'
      failureUri = '/oauth/facebook/error'
      callback = "${grails.serverURL}/oauth/facebook/callback"
    }
    google {
      api = org.grails.plugin.springsecurity.oauth.GoogleApi20
      key= '410621637819.apps.googleusercontent.com'
      secret= 'OZUFsa09TBje0CjcOCEKAsYn'
      successUri = '/oauth/google/success'
      failureUri = '/oauth/google/error'
      callback = "${grails.serverURL}/oauth/google/callback"
      scope = 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email'
    }
    twitter {
      api = org.scribe.builder.api.TwitterApi.SSL
      successUri = '/oauth/twitter/success'
      failureUri = '/oauth/twitter/error'
      callback = "${grails.serverURL}/oauth/twitter/callback"
    }
  }
}
