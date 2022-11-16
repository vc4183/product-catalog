package services.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class PersistenceProducer {
    @PersistenceUnit(unitName = "product-catalog-jpa") //persistence.xml se ta string uporablja
    private EntityManagerFactory emf;

    @Produces
    @ApplicationScoped
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void disposeEntityManager(@Disposes EntityManager entityManager) {

        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
