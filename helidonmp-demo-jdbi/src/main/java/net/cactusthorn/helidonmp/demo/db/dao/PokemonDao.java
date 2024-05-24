package net.cactusthorn.helidonmp.demo.db.dao;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import net.cactusthorn.helidonmp.demo.db.DemoDB;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;

@Dependent
public final class PokemonDao {

  private final Jdbi jdbi;

  @Inject
  public PokemonDao(@DemoDB Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public List<Pokemon> findAll() {
    // @formatter:off
    return jdbi.withHandle(h -> h.createQuery("select * from POKEMON").mapTo(Pokemon.class).list());
    // @formatter:on 
  }

  public Optional<Pokemon> findByName(String name) {
    // @formatter:off
    return jdbi
      .withHandle(h -> h.createQuery("select * from POKEMON where NAME = :name").bind("name", name).mapTo(Pokemon.class).findOne());
    // @formatter:on 
  }

  public Optional<Pokemon> findBy(int id) {
    // @formatter:off
    return jdbi
      .withHandle(h -> h.createQuery("select * from POKEMON where ID = :id").bind("id", id).mapTo(Pokemon.class).findOne());
    // @formatter:on
  }

  public void add(Pokemon pokemon) {
    // @formatter:off
    jdbi.useTransaction(h -> h.createUpdate("insert into POKEMON (ID, NAME, POKEMONTYPE_ID) values (:id, :name, :type)")
        .bindMethods(pokemon)
        .execute());
    // @formatter:on
  }

  public void remove(Pokemon pokemon) {
    // @formatter:off
    jdbi.useTransaction(h -> h.createUpdate("delete from POKEMON where ID = :id").bind("id", pokemon.id()).execute());
    // @formatter:on
  }

}
