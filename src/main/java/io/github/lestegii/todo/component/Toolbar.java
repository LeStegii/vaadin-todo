package io.github.lestegii.todo.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import io.github.lestegii.todo.data.entity.Status;

import java.util.Set;

/**
 * The toolbar contains buttons for adding new entries and filtering the list of entries.
 * The filter is placed on the left side and the add button on the right side.
 */
public class Toolbar extends HorizontalLayout {

    private final EntryFilter entryFilter = new EntryFilter();
    private final Button addEntryButton = new Button("Add entry", VaadinIcon.PLUS_CIRCLE.create());

    public Toolbar() {
        addClassName("toolbar");
        setWidthFull();

        init();
    }

    private void init() {
        addEntryButton.setTooltipText("Add new entry");

        setPadding(true);
        setAlignItems(Alignment.BASELINE);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        addEntryButton.addClickListener(click -> fireEvent(new AddEntryEvent(this)));

        add(this.entryFilter, this.addEntryButton);
    }

    public Registration addEntryAddListener(ComponentEventListener<AddEntryEvent> listener) {
        return addListener(AddEntryEvent.class, listener);
    }

    public Registration addFilterUpdateListener(ComponentEventListener<EntryFilter.FilterUpdateEvent> listener) {
        return entryFilter.addUpdateListener(listener);
    }


    // Event that is fired when the add entry button is clicked
    public static class AddEntryEvent extends ComponentEvent<Toolbar> {

        public AddEntryEvent(Toolbar source) {
            super(source, false);
        }

    }

    public String getFilter() {
        return entryFilter.getFilterText();
    }

    public Set<Status> getSelectedStatuses() {
        return entryFilter.getSelectedStatuses();
    }

}
