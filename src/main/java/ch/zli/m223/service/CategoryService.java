package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Category;

@ApplicationScoped
public class CategoryService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Category createCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    public List<Category> findAll() {
        return entityManager.createQuery("FROM Category", Category.class).getResultList();
    }

    @Transactional
    public Category updateCategory(Long id, Category updatedCategory) {
        Category category = entityManager.find(Category.class, id);
        category.setTitle(updatedCategory.getTitle());
        return category;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = entityManager.find(Category.class, id);
        if (category != null) {
            entityManager.remove(category);
        }
    }
}
