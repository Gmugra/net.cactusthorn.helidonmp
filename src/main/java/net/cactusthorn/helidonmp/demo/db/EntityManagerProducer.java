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

  @PersistenceContext(unitName = "demo")
  private EntityManager entityManager;

  /*private EntityManagerFactory entityManagerFactory;

  @PostConstruct
  public void postConstruct() {
    entityManagerFactory = Persistence.createEntityManagerFactory("demo");
  }*/

  @Produces
  @DemoDB
  protected EntityManager createEntityManager() {
    //var em = entityManagerFactory.createEntityManager();
    //System.out.println("Created: " + entityManager.toString());
    return entityManager;
  }

  /*public void closeEntityManager(@Disposes @DemoDB EntityManager entityManager) {
    if (entityManager.isOpen()) {
      try {
        var em = entityManager.toString(); 
        entityManager.close();
        System.out.println("Closed: " + em);
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
  }*/

}
