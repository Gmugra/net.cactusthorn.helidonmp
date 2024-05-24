package net.cactusthorn.helidonmp.demo.db;

//import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

  private EntityManager entityManager;

  @PersistenceContext(unitName = "demo")
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Produces
  @DemoDB
  public EntityManager createEntityManager() {
    return entityManager;
  }

}
