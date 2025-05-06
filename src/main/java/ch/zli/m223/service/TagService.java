package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Tag;

@ApplicationScoped
public class TagService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Tag createTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    public List<Tag> findAll() {
        return entityManager.createQuery("FROM Tag", Tag.class).getResultList();
    }

    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Transactional
    public Tag updateTag(Long id, Tag updatedTag) {
        Tag tag = entityManager.find(Tag.class, id);
        tag.setTitle(updatedTag.getTitle());
        return tag;
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
        }
    }
}
