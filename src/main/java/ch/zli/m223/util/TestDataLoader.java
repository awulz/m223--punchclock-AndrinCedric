package ch.zli.m223.util;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
