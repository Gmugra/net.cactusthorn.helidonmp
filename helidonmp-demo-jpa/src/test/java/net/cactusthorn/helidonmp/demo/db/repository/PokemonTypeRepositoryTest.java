package net.cactusthorn.helidonmp.demo.db.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import net.cactusthorn.helidonmp.demo.junit5.GlobalSingleInstanceEmbeddedPostgresExtension;

@ExtendWith(GlobalSingleInstanceEmbeddedPostgresExtension.class)
@HelidonTest
class PokemonTypeRepositoryTest {

  @Inject
  private PokemonTypeRepository pokemonTypeRepository;

  @Test
  void getPokemonTypes() {
    var result = pokemonTypeRepository.findAllOrderByNameAsc();
    Assertions.assertEquals("Bug", result.get(0).getName());
  }

  @Test
  void getPokemonTypeById() {
    var result = pokemonTypeRepository.findById(6);
    Assertions.assertEquals("Rock", result.get().getName());
  }
}
