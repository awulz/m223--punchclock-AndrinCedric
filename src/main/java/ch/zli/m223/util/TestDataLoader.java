package ch.zli.m223.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ch.zli.m223.model.Category;
import ch.zli.m223.model.Tag;

@ApplicationScoped
public class TestDataLoader {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void loadTestData() {
        // Beispiel-Testdaten
        Category work = new Category();
        work.setTitle("Work");
        entityManager.persist(work);

        Category personal = new Category();
        personal.setTitle("Personal");
        entityManager.persist(personal);

        Tag urgent = new Tag();
        urgent.setTitle("Urgent");
        entityManager.persist(urgent);

        Tag optional = new Tag();
        optional.setTitle("Optional");
        entityManager.persist(optional);

        System.out.println("Testdaten wurden erfolgreich geladen.");
    }
}
