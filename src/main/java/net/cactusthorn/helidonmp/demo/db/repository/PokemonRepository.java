package net.cactusthorn.helidonmp.demo.db.repository;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerConfig;

import jakarta.persistence.FlushModeType;

import net.cactusthorn.helidonmp.demo.db.deltaspike.DemoDBEntityManagerResolver;
import net.cactusthorn.helidonmp.demo.db.model.Pokemon;

@Repository(forEntity = Pokemon.class)
@EntityManagerConfig(entityManagerResolver = DemoDBEntityManagerResolver.class, flushMode = FlushModeType.COMMIT)
public interface PokemonRepository extends EntityRepository<Pokemon, Integer> {

  Optional<Pokemon> findByName(String Name);
}
