dataSource {
  pooled = true
  driverClassName = "org.h2.Driver"
  username = "sa"
  password = ""
}
hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = false
  cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
  //    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
environments {
  development {
    dataSource {
      dbCreate = "update"
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQLDialect
      url = "jdbc:postgresql://localhost:5432/imperil"
      username = "imperil"
      password = "password"
      loggingSql = false
    }
  }
  test {
    dataSource {
      dbCreate = "create"
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQLDialect
      url = "jdbc:postgresql://localhost:5432/imperilTest"
      username = "imperilTest"
      password = "password"
      loggingSql = false
    }
    //    dataSource {
    //      dbCreate = "create-drop"
    //      url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
    //    }
  }
  production {
    dataSource {
      dbCreate = "create"
      driverClassName = "org.postgresql.Driver"
      dialect = org.hibernate.dialect.PostgreSQLDialect
      uri = new URI(System.env.DATABASE_URL?:"postgres://imperil:password@localhost/imperil")
      url = "jdbc:postgresql://"+uri.host+uri.path
      username = uri.userInfo.split(":")[0]
      password = uri.userInfo.split(":")[1]
    }
  }
}
