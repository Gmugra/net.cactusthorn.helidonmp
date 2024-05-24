package net.cactusthorn.helidonmp.demo.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;
import net.cactusthorn.helidonmp.demo.db.repository.PokemonRepository;
import net.cactusthorn.helidonmp.demo.db.repository.PokemonTypeRepository;

import java.util.List;

/**
 * This class implements REST endpoints to interact with Pokemons. The following
 * operations are supported:
 *
 * <ul>
 * <li>GET /pokemon: Retrieve list of all pokemons</li>
 * <li>GET /pokemon/{id}: Retrieve single pokemon by ID</li>
 * <li>GET /pokemon/name/{name}: Retrieve single pokemon by name</li>
 * <li>DELETE /pokemon/{id}: Delete a pokemon by ID</li>
 * <li>POST /pokemon: Create a new pokemon</li>
 * </ul>
 *
 * Pokemon, and Pokemon character names are trademarks of Nintendo.
 */
@Path("pokemon")
public class PokemonResource {

  private PokemonRepository pokemonRepository;
  private PokemonTypeRepository pokemonTypeRepository;

  @Inject
  public void setPokemonTypeRepository(PokemonRepository repository) {
    this.pokemonRepository = repository;
  }

  @Inject
  public void setPokemonTypeRepository(PokemonTypeRepository repository) {
    this.pokemonTypeRepository = repository;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Pokemon> getPokemons() {
    return pokemonRepository.findAll();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Pokemon getPokemonById(@PathParam("id") String id) {
    var pokemon = pokemonRepository.findBy(Integer.valueOf(id));
    if (pokemon == null) {
      throw new NotFoundException("Unable to find pokemon with ID " + id);
    }
    return pokemon;
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional(Transactional.TxType.REQUIRED)
  public void deletePokemon(@PathParam("id") String id) {
    var pokemon = pokemonRepository.findBy(Integer.valueOf(id));
    pokemonRepository.remove(pokemon);
  }

  @GET
  @Path("name/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Pokemon getPokemonByName(@PathParam("name") String name) {
    return pokemonRepository.findByName(name).orElseThrow(() -> new NotFoundException("Unable to find pokemon with name " + name));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional(Transactional.TxType.REQUIRED)
  public void createPokemon(Pokemon pokemon) {
    try {
      var pokemonType = pokemonTypeRepository.findById(pokemon.getType());
      pokemon.setPokemonType(pokemonType.get());
      pokemonRepository.save(pokemon);
    } catch (Exception e) {
      throw new BadRequestException("Unable to create pokemon with ID " + pokemon.getId());
    }
  }
}
