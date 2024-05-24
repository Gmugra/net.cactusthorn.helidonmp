package net.cactusthorn.helidonmp.demo;

//import io.helidon.common.config.spi.ConfigProvider;
import io.helidon.microprofile.server.Server;

import org.eclipse.microprofile.config.ConfigProvider;
import org.flywaydb.core.Flyway;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Main {

  private Main() {
    throw new UnsupportedOperationException("No chance even with reflection.");
  }

  public static void main(final String... args) {

    //e.g. Deltaspike Data or Jersey are using JUL, to "bridge" it to Slf4j we need next code.
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    var server = Server.create();

    flyway();

    server.start();
  }

  static void flyway () {
    var config = ConfigProvider.getConfig();
    var location = config.getValue("flyway.location", String.class);
    var url = config.getValue("javax.sql.DataSource.dsdemo.dataSource.url", String.class);
    var user = config.getValue("javax.sql.DataSource.dsdemo.dataSource.user", String.class);
    var password = config.getOptionalValue("javax.sql.DataSource.dsdemo.dataSource.password", String.class).orElse("");
    var flyway = Flyway.configure().locations(location).dataSource(url, user, password).load();
    flyway.migrate();
  }
}
