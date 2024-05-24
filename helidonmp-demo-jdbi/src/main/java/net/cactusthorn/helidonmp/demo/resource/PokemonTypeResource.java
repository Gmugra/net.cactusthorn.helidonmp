package net.cactusthorn.helidonmp.demo.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.cactusthorn.helidonmp.demo.db.dao.PokemonTypeDao;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;

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

    private PokemonTypeDao pokemonTypeDao;

    @Inject
    public void setPokemonTypeRepository(PokemonTypeDao dao) {
      this.pokemonTypeDao = dao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PokemonType> getPokemonTypes() {
      return pokemonTypeDao.findAllOrderByNameAsc();
    }
}
