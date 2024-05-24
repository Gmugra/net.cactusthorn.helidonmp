package net.cactusthorn.helidonmp.demo.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;
import net.cactusthorn.helidonmp.demo.junit5.GlobalSingleInstanceEmbeddedPostgres;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GlobalSingleInstanceEmbeddedPostgres
@HelidonTest
public class PokemonDaoTest {

  @Inject
  private PokemonDao pokemonDao;

  @Test
  @Order(1)
  void getPokemons() {
    var result = pokemonDao.findAll();
    Assertions.assertTrue(result.size() > 0);
  }

  @Test
  @Order(2)
  void getPokemon() {
    var result = pokemonDao.findByName("Bulbasaur");
    Assertions.assertEquals(1, result.get().id());
  }

  @Test
  @Order(3)
  void addPokemon() {
    var pokemon = new Pokemon(100, "Test New Pokemon", 1);
    pokemonDao.add(pokemon);
  }

  @Test
  @Order(4)
  void removePokemon() {
    var pokemon = pokemonDao.findBy(100);
    pokemon.ifPresent(p -> pokemonDao.remove(p));
  }

}
