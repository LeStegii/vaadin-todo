package io.github.lestegii.todo.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import io.github.lestegii.todo.data.entity.Status;

import java.util.Set;

public class EntryFilter extends HorizontalLayout {

    private final TextField filterTextField = new TextField();
    private final Button refreshButton = new Button(VaadinIcon.REFRESH.create());
    private final CheckboxGroup<Status> statusFilterGroup = new CheckboxGroup<>();

    public EntryFilter() {
        addClassName("entry-filter");
        setAlignItems(Alignment.BASELINE);

        init();
    }

    private void init() {
        // Search field
        filterTextField.setLabel("Filter for title");
        filterTextField.setPlaceholder("Search for title...");
        filterTextField.setClearButtonVisible(true);
        filterTextField.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextField.addValueChangeListener(click -> fireFilterUpdateEvent());

        // Refresh Button
        refreshButton.setTooltipText("Refresh");
        refreshButton.addClickListener(click -> fireFilterUpdateEvent());

        // Status Filter
        statusFilterGroup.setItems(Status.values());
        statusFilterGroup.addClassName("status-filter");
        statusFilterGroup.setLabel("Filter for status");
        statusFilterGroup.setRequired(false);
        statusFilterGroup.select(Status.values());
        statusFilterGroup.addValueChangeListener(click -> fireFilterUpdateEvent());

        add(filterTextField, statusFilterGroup, refreshButton);
    }

    public Registration addUpdateListener(ComponentEventListener<FilterUpdateEvent> listener) {
        return addListener(FilterUpdateEvent.class, listener);
    }

    private void fireFilterUpdateEvent() {
        fireEvent(new FilterUpdateEvent(this, filterTextField.getValue(), statusFilterGroup.getValue()));
    }

    public Set<Status> getSelectedStatuses() {
        return statusFilterGroup.getValue();
    }

    public String getFilterText() {
        return filterTextField.getValue();
    }

    public static class FilterUpdateEvent extends ComponentEvent<EntryFilter> {


        private final String filter;
        private final Set<Status> statuses;


        public FilterUpdateEvent(EntryFilter source, String filter, Set<Status> statuses) {
            super(source, false);
            this.filter = filter;
            this.statuses = statuses;
        }

        public String getFilter() {
            return filter;
        }

        public Set<Status> getStatuses() {
            return statuses;
        }
    }

}
