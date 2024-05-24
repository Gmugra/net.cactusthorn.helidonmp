package net.cactusthorn.helidonmp.demo.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;
import net.cactusthorn.helidonmp.demo.db.repository.PokemonTypeRepository;

/**
 * This class implements a REST endpoint to retrieve Pokemon types.
 *
 * <ul>
 * <li>GET /type: Retrieve list of all pokemon types</li>
 * </ul>
 *
 * Pokemon, and Pokemon character names are trademarks of Nintendo.
 */
@Path("type")
public class PokemonTypeResource {

    private PokemonTypeRepository pokemonTypeRepository;

    @Inject
    public void setPokemonTypeRepository(PokemonTypeRepository repository) {
      this.pokemonTypeRepository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PokemonType> getPokemonTypes() {
      return pokemonTypeRepository.findAllOrderByNameAsc();
    }
}
