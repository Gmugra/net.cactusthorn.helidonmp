package net.cactusthorn.helidonmp.demo.db;

import java.util.Objects;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;

@ApplicationScoped
public class JdbiProducer {

  private DataSource dataSource;
  private Jdbi jdbi;

  @Inject
  public void setDataSource(@Named("dsdemo") final DataSource dataSource) {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  @PostConstruct
  public void postConstruct() {
    // @formatter:off
    jdbi = Jdbi
      .create(dataSource)
      .registerRowMapper(ConstructorMapper.factory(PokemonType.class))
      .registerRowMapper(ConstructorMapper.factory(Pokemon.class));
    // @formatter:on
  }

  @Produces
  @DemoDB
  public Jdbi getJdbi() {
    return jdbi;
  }
}
