package net.cactusthorn.helidonmp.demo.db.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import net.cactusthorn.helidonmp.demo.db.DemoDB;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;

@Dependent
public final class PokemonTypeDao {

  private final Jdbi jdbi;

  @Inject
  public PokemonTypeDao(@DemoDB Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public List<PokemonType> findAllOrderByNameAsc() {
    // @formatter:off
    return jdbi.withHandle(h -> h.createQuery("select * from POKEMONTYPE order by NAME ASC").mapTo(PokemonType.class).list());
    // @formatter:on
  }

  public Optional<PokemonType> findById(int id) {
    // @formatter:off
    return jdbi
      .withHandle(h -> h.createQuery("select * from POKEMONTYPE WHERE ID = :id").bind("id", id).mapTo(PokemonType.class).findOne());
    // @formatter:on
  }

  public List<PokemonType> findById(Collection<Integer> ids) {
    // @formatter:off
    return jdbi
        .withHandle(h -> h.createQuery("select * from POKEMONTYPE WHERE ID in (<ids>)")
            .bindList("ids", ids).mapTo(PokemonType.class).list());
    // @formatter:on
  }
}
