package io.github.lestegii.todo.service;

import com.vaadin.flow.component.notification.Notification;
import io.github.lestegii.todo.data.EntryRepository;
import io.github.lestegii.todo.data.entity.Entry;
import io.github.lestegii.todo.data.entity.Status;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public @NotNull EntryRepository repository() {
        return entryRepository;
    }

    public void save(Entry entry) {
        if (entry == null) {
            Notification.show("Tried to save a null entry", 3000, Notification.Position.TOP_END);
            return;
        }
        entryRepository.save(entry);
    }

    public void delete(Entry entry) {
        if (entry == null) {
            Notification.show("Tried to delete a null entry", 3000, Notification.Position.TOP_END);
            return;
        }
        entryRepository.delete(entry);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    public List<String> findAllCategories() {
        return entryRepository.findAllCategories();
    }

    public List<Entry> findAll(String user, String filter, Collection<Status> statuses) {
        return entryRepository.findAll(user, filter, statuses == null ? Arrays.asList(Status.values()) : statuses);
    }

}
