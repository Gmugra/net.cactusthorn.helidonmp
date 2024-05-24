package net.cactusthorn.helidonmp.demo.db.repository;

import java.util.List;
import java.util.Optional;

import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;

import jakarta.persistence.FlushModeType;
import net.cactusthorn.helidonmp.demo.db.deltaspike.DemoDBEntityManagerResolver;
import net.cactusthorn.helidonmp.demo.db.model.PokemonType;

@Repository(forEntity = PokemonType.class)
@EntityManagerConfig(entityManagerResolver = DemoDBEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface PokemonTypeRepository {

  List<PokemonType> findAllOrderByNameAsc();

  Optional<PokemonType> findById(int id);
}
