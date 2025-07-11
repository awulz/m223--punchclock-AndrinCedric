package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import ch.zli.m223.model.Entry;
import ch.zli.m223.dto.EntryDto;
import ch.zli.m223.model.Category;
import ch.zli.m223.model.Tag;
import ch.zli.m223.model.ApplicationUser;
import jakarta.persistence.PersistenceContext;

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

    public EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();
        dto.id = entry.getId();
        dto.checkIn = entry.getCheckIn();
        dto.checkOut = entry.getCheckOut();
        dto.categoryId = entry.getCategory() != null ? entry.getCategory().getId() : null;
        dto.userId = entry.getUser() != null ? entry.getUser().getId() : null;
        if(entry.getTags() != null) {
            dto.tags = entry.getTags().stream().map(Tag::getTitle).toList();
        }
        return dto;
    }

    @Transactional
    public Entry fromDto(EntryDto dto) {
        Entry entry = new Entry();
        entry.setId(dto.id);
        entry.setCheckIn(dto.checkIn);
        entry.setCheckOut(dto.checkOut);
        if(dto.categoryId != null) {
            entry.setCategory(entityManager.find(Category.class, dto.categoryId));
        }
        if(dto.userId != null) {
            entry.setUser(entityManager.find(ApplicationUser.class, dto.userId));
        }
        if(dto.tags != null) {
            List<Tag> tags = dto.tags.stream().map(title -> {
                // Versuche Tag zu finden, sonst neu anlegen
                List<Tag> found = entityManager.createQuery("FROM Tag WHERE title = :title", Tag.class)
                    .setParameter("title", title)
                    .getResultList();
                if (!found.isEmpty()) {
                    return found.get(0);
                } else {
                    Tag newTag = new Tag();
                    newTag.setTitle(title);
                    entityManager.persist(newTag);
                    return newTag;
                }
            }).toList();
            entry.setTags(tags);
        }
        return entry;
    }
}
