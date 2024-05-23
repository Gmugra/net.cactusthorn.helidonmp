package net.cactusthorn.helidonmp.demo.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(GlobalSingleInstanceEmbeddedPostgresExtension.class)
public @interface GlobalSingleInstanceEmbeddedPostgres {

  int port() default GlobalSingleInstanceEmbeddedPostgresExtension.DEFAULT_PORT;

  String flywayLocation() default GlobalSingleInstanceEmbeddedPostgresExtension.DEFAULT_FLYWAY_LOCATION;

}
