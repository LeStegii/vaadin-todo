package io.github.lestegii.todo.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;
import io.github.lestegii.todo.data.entity.Entry;
import io.github.lestegii.todo.util.Util;

import java.util.List;

import static io.github.lestegii.todo.util.Util.nullOrEmpty;

public class EntryGrid extends Grid<Entry> {

    public EntryGrid(List<Entry> initialEntries) {
        super(Entry.class);

        setItems(initialEntries);

        addClassName("entry-grid");
        setSizeFull();

        init();
    }

    private void init() {

        setSelectionMode(SelectionMode.NONE);

        addColumn(Entry::title).setHeader("Title");
        addColumn(entry -> nullOrEmpty(entry.shortDescription()) ? "-" : entry.shortDescription()).setHeader("Summary");
        addColumn(Entry::status).setHeader("Status");
        addColumn(Entry::priority).setHeader("Priority");
        addColumn(entry -> Util.date(entry.dueDate())).setHeader("Due date");
        addComponentColumn(entry -> {
            Button button = new Button("View", VaadinIcon.EYE.create());
            return button;
        });
        addComponentColumn(entry -> {
            Button button = new Button("Edit", VaadinIcon.PENCIL.create());
            button.addClickListener(click -> {
                fireEvent(new EditEntryEvent(this, entry));
            });
            return button;
        });
    }

    public static class EditEntryEvent extends ComponentEvent<EntryGrid> {

        private final Entry entry;

        public EditEntryEvent(EntryGrid source, Entry entry) {
            super(source, false);
            this.entry = entry;
        }

        public Entry getEntry() {
            return entry;
        }

    }

    public Registration addEditEntryListener(ComponentEventListener<EditEntryEvent> listener) {
        return addListener(EditEntryEvent.class, listener);
    }

}
