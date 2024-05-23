package net.cactusthorn.helidonmp.demo.db.deltaspike;

import org.apache.deltaspike.jpa.api.entitymanager.EntityManagerResolver;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import net.cactusthorn.helidonmp.demo.db.DemoDB;

/* 
 * This class we need to assign @DemoDB annotated EntityManager to the deltaspike-data repositories
 */
@Dependent
public final class DemoDBEntityManagerResolver implements EntityManagerResolver {

  private final EntityManager entityManager;

  @Inject
  public DemoDBEntityManagerResolver(@DemoDB EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public EntityManager resolveEntityManager() {
    return entityManager;
  }
}
