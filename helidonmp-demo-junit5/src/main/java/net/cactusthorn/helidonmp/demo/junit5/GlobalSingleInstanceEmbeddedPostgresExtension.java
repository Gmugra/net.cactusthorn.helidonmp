package net.cactusthorn.helidonmp.demo.junit5;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.commons.support.SearchOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import io.zonky.test.db.postgres.embedded.FlywayPreparer;

//FYI https://stackoverflow.com/questions/43282798/in-junit-5-how-to-run-code-before-all-tests
public class GlobalSingleInstanceEmbeddedPostgresExtension
    implements BeforeAllCallback, ExtensionContext.Store.CloseableResource, ParameterResolver {

  public static final int DEFAULT_PORT = 60736;

  public static final String DEFAULT_FLYWAY_LOCATION = "db/flyway/postgres";

  private static final Logger LOG = LoggerFactory.getLogger(GlobalSingleInstanceEmbeddedPostgresExtension.class);

  private static final Lock LOCK = new ReentrantLock();

  private static volatile EmbeddedPostgres embeddedPostgres;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    // need lock to correctly support JUnit parallel
    // (junit.jupiter.execution.parallel.enabled = true)
    LOCK.lock();
    try {
      var value = context.getRoot().getStore(GLOBAL)
          .get(GlobalSingleInstanceEmbeddedPostgresExtension.class.getSimpleName());
      if (value == null) {
        context.getRoot().getStore(GLOBAL).put(GlobalSingleInstanceEmbeddedPostgresExtension.class.getSimpleName(),
            this);
        var testClass = context.getTestClass().orElseThrow(() -> new UnsupportedOperationException());
        AnnotationSupport.findAnnotation(testClass, GlobalSingleInstanceEmbeddedPostgres.class, SearchOption.DEFAULT)
            .ifPresentOrElse(a -> startup(a.port(), a.flywayLocation()),
                () -> startup(DEFAULT_PORT, DEFAULT_FLYWAY_LOCATION));
      }
    } finally {
      LOCK.unlock();
    }

  }

  @Override
  public void close() throws Throwable {
    if (embeddedPostgres != null) {
      embeddedPostgres.close();
      LOG.info("Embedded Postgres closed.");
    }
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().getType().equals(EmbeddedPostgres.class);
  }

  @Override
  public EmbeddedPostgres resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return embeddedPostgres;
  }

  private void startup(int port, String classpathLocation) {
    try {
      LOG.info("Embedded Postgres starting: port={}, flyWayClasspathLocation={}", port, classpathLocation);
      embeddedPostgres = EmbeddedPostgres.builder().setPort(port).start();
      FlywayPreparer.forClasspathLocation(classpathLocation).prepare(embeddedPostgres.getPostgresDatabase());
      LOG.info("Embedded Postgres started!");
    } catch (IOException | SQLException e) {
      embeddedPostgres = null;
      LOG.error("", e);
    }
  }

}
