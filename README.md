# net.cactusthorn.helidonmp

The simple Demo applications done with [Helidon MP 4.x](https://helidon.io/docs/v4/mp/introduction)  
It's based on Helidon MP's "Pokemon" demo app, but the original demo has been expanded to make it more "realistic".  
(however REST API are 100% same)  
  
## Differences from the original demo application

###  Database (JPA sub-module)

1.  [HikariCP](https://github.com/brettwooldridge/HikariCP) + JPA + **JTA** + EclipseLink
2.  More "clean" **persistence.xml**
    -   exclude-unlisted-classes=true
    -   shared-cache-mode=ENABLE_SELECTIVE
    -   eclipselink.weaving=static
    -   eclipselink.target-database dropped
3.  Produces and use qualified EntityManager (not default)
4.  Used [Apache DeltaSpike Data](https://deltaspike.apache.org/documentation/data.html)
    -   Yes, this is "Spring-style" Repository interfaces
    -   with ContainerManagedTransactionStrategy
    -   with EntityManagerResolver for qualified EntityManager

###  Database (JDBI sub-module)

1.  HikariCP + [Jdbi 3](https://jdbi.org)

###  Logging

[SLF4J](https://www.slf4j.org) + [Logback](https://logback.qos.ch) instead of JUL
1.  **persistence.xml**: eclipselink.logging.logger=org.eclipse.persistence.logging.slf4j.SLF4JLogger
    -   This logger is taken from the **org.eclipse.persistence:org.eclipse.persistence.extension** library
2.  JUL -> SLF4JBridge (Apache DeltaSpike Data; Jersey; etc. are using JUL, so it must be "redirected" to SLF4J)
    -   for Unit tests: **src/test/resources/logging.properties**: handlers=org.slf4j.bridge.SLF4JBridgeHandler
    -   the **Main** class setup JUL -> SLF4JBridge programmatically
3.  logback.xml & logback-text.xml

###  Tesing

1.  Avoid to use hamcrest Library (not necessary at all for the demo application)
2.  **src/test/resources/META-INF/microprofile-config.properties**: config_ordinal=1000 (to override "main" configuation file)
3.  Unit tests are using [Zonkyio Embedded Postgres](https://github.com/zonkyio/embedded-postgres)
    -   with [Flyway](https://flywaydb.org)
    -   Made a custom JUnit5 Extension to run a single Embedded Postgres instance for all test classes
    -   So, just for demo the application is using Embedded Postgres for unit-tests, but H2 for "productive" run :)

###  Main class

Original demo app do not have "Main" class but here we have one
-   To setup JUL -> SLF4JBridge
-   To run Flyway before server start

###  Build with Maven

1.  Set finalName=${project.artifactId}-${project.version}
2.  Added **.mvn** folder
3.  Set mainClass=net.cactusthorn.helidonmp.demo.Main
4.  Use **maven-assembly-plugin** to generat ZIP artifact with all Lib and the application
    -   target/helidonmp-demo-1.0.0-full.zip 
    -   A simple way to create the atifact that contains everything an application needs at runtime and can be placed in, for example, an Artifactory

###  Miscellaneous

1.  Use [Flyway](https://flywaydb.org) for DB setup
2.  Original **Message** *class* converted to *record*
3.  More clean *.gitattributes* & *.gitignore*