package net.cactusthorn.helidonmp.demo.resource;

import jakarta.inject.Inject;
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
import net.cactusthorn.helidonmp.demo.db.dao.PokemonDao;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;

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

  private PokemonDao pokemonDao;

  @Inject
  public void setPokemonTypeRepository(PokemonDao dao) {
    this.pokemonDao = dao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Pokemon> getPokemons() {
    return pokemonDao.findAll();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Pokemon getPokemonById(@PathParam("id") String id) {
    return pokemonDao.findBy(Integer.valueOf(id)).orElseThrow(() -> new NotFoundException("Unable to find pokemon with ID " + id));
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public void deletePokemon(@PathParam("id") String id) {
    pokemonDao.findBy(Integer.valueOf(id)).ifPresent(p -> pokemonDao.remove(p));
  }

  @GET
  @Path("name/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Pokemon getPokemonByName(@PathParam("name") String name) {
    return pokemonDao.findByName(name).orElseThrow(() -> new NotFoundException("Unable to find pokemon with name " + name));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void createPokemon(Pokemon pokemon) {
    try {
      pokemonDao.add(pokemon);
    } catch (Exception e) {
      throw new BadRequestException("Unable to create pokemon with ID " + pokemon.id());
    }
  }
}
