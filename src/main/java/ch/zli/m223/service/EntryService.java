package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }

    @Transactional
    public Entry updateEntry(Long id, Entry updatedEntry) {
        Entry entry = entityManager.find(Entry.class, id);
        if (entry != null) {
            entry.setCheckIn(updatedEntry.getCheckIn());
            entry.setCheckOut(updatedEntry.getCheckOut());
            entry.setCategory(updatedEntry.getCategory());
            entry.setTags(updatedEntry.getTags());
            return entry;
        }
        return null;
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entityManager.find(Entry.class, id);
        if (entry != null) {
            entityManager.remove(entry);
        }
    }
}
