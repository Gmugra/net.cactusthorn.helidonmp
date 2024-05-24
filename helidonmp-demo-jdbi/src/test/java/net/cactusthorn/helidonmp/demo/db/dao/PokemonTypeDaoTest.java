package net.cactusthorn.helidonmp.demo.db.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import net.cactusthorn.helidonmp.demo.junit5.GlobalSingleInstanceEmbeddedPostgres;

@GlobalSingleInstanceEmbeddedPostgres
@HelidonTest
class PokemonTypeDaoTest {

  @Inject
  private PokemonTypeDao pokemonTypeDao;

  @Test
  void getPokemonTypes() {
    var result = pokemonTypeDao.findAllOrderByNameAsc();
    Assertions.assertEquals("Bug", result.get(0).name());
  }

  @Test
  void getPokemonTypeById() {
    var result = pokemonTypeDao.findById(6);
    Assertions.assertEquals("Rock", result.get().name());
  }

  @Test
  void getPokemonTypeByIdEmpty() {
    var result = pokemonTypeDao.findById(77);
    Assertions.assertFalse(result.isPresent());
  }
}
