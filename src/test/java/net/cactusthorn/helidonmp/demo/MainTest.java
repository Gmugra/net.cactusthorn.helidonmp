package net.cactusthorn.helidonmp.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;

import net.cactusthorn.helidonmp.demo.junit5.GlobalSingleInstanceEmbeddedPostgres;
import net.cactusthorn.helidonmp.demo.resource.Message;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;

import org.eclipse.microprofile.metrics.MetricRegistry;
import jakarta.json.JsonArray;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.client.Entity;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import io.helidon.metrics.api.MetricsFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Assertions;

@GlobalSingleInstanceEmbeddedPostgres
@HelidonTest
class MainTest {

  @Inject
  private MetricRegistry registry;

  @Inject
  private WebTarget target;

  @AfterAll
  static void afterAll() {
    MetricsFactory.closeAll();
  }

  @Test
  void testHealth() {
    var response = target.path("health").request().get();
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void testMicroprofileMetrics() {
    var message = target.path("simple-greet/Joe").request().get(Message.class);

    Assertions.assertEquals("Hello Joe", message.message());
    var counter = registry.counter("personalizedGets");
    var before = counter.getCount();

    message = target.path("simple-greet/Eric").request().get(Message.class);

    Assertions.assertEquals("Hello Eric", message.message());
    var after = counter.getCount();
    Assertions.assertEquals(1d, after - before, "Difference in personalized greeting counter between successive calls");
  }

  @Test
  void testPokemonTypes() {
    var types = target.path("type").request().get(JsonArray.class);
    Assertions.assertEquals(18, types.size());
  }

  @Test
  void testPokemon() {
    Assertions.assertEquals(6, getPokemonCount());

    var pokemon = target.path("pokemon/1").request().get(Pokemon.class);
    Assertions.assertEquals("Bulbasaur", pokemon.getName());

    pokemon = target.path("pokemon/name/Charmander").request().get(Pokemon.class);
    Assertions.assertEquals(10, pokemon.getType());

    var response = target.path("pokemon/1").request().get();
    Assertions.assertEquals(200, response.getStatus());

    var test = new Pokemon();
    test.setType(1);
    test.setId(100);
    test.setName("Test");
    response = target.path("pokemon").request().post(Entity.entity(test, MediaType.APPLICATION_JSON));
    Assertions.assertEquals(204, response.getStatus());
    Assertions.assertEquals(7, getPokemonCount());

    response = target.path("pokemon/100").request().delete();
    Assertions.assertEquals(204, response.getStatus());
    Assertions.assertEquals(6, getPokemonCount());
  }

  private int getPokemonCount() {
    var pokemons = target.path("pokemon").request().get(JsonArray.class);
    return pokemons.size();
  }

  @Test
  void testGreet() {
    var message = target.path("simple-greet").request().get(Message.class);
    Assertions.assertEquals("Test Hello World!", message.message());
  }
}
