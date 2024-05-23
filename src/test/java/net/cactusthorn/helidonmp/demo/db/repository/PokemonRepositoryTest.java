package net.cactusthorn.helidonmp.demo.db.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;
import net.cactusthorn.helidonmp.demo.junit5.GlobalSingleInstanceEmbeddedPostgres;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@GlobalSingleInstanceEmbeddedPostgres
@HelidonTest
class PokemonRepositoryTest {

  @Inject
  private PokemonRepository pokemonRepository;

  @Test
  @Order(1)
  void getPokemons() {
    var result = pokemonRepository.findAll();
    Assertions.assertTrue(result.size() > 0);
  }

  @Test
  @Order(2)
  void getPokemon() {
    var result = pokemonRepository.findByName("Bulbasaur");
    Assertions.assertEquals(1, result.get().getId());
  }

  @Test
  @Order(3)
  @Transactional(Transactional.TxType.REQUIRED)
  void addPokemon() {
    var type = new PokemonType();
    type.setId(1);
    var pokemon = new Pokemon();
    pokemon.setId(100);
    pokemon.setName("Test New Pokemon");
    pokemon.setPokemonType(type);
    pokemonRepository.save(pokemon);
  }

  @Test
  @Order(4)
  @Transactional(Transactional.TxType.REQUIRED)
  void removePokemon() {
    var pokemon = pokemonRepository.findBy(100);
    pokemonRepository.remove(pokemon);
  }

}
