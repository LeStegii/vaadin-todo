package io.github.lestegii.todo.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.lestegii.todo.component.EntryFormDialog;
import io.github.lestegii.todo.component.EntryGrid;
import io.github.lestegii.todo.component.Toolbar;
import io.github.lestegii.todo.data.entity.Entry;
import io.github.lestegii.todo.data.entity.Status;
import io.github.lestegii.todo.service.EntryService;

import java.util.Set;

/**
 * The main view contains a list of entries and a form for adding new entries.
 */
@PageTitle("List | Todo")
@Route("")
public class MainView extends VerticalLayout {

    private final Toolbar toolbar;
    private final EntryGrid grid;

    private final EntryService entryService;

    public MainView(EntryService entryService) {
        this.entryService = entryService;

        toolbar = new Toolbar();
        grid = new EntryGrid(entryService.findAll());

        addClassName("list-view");
        setSizeFull();

        init();
    }

    private void init() {
        grid.addEditEntryListener(event -> openForm(event.getEntry()));
        toolbar.addFilterUpdateListener(event -> refreshGrid(event.getFilter(), event.getStatuses()));
        toolbar.addEntryAddListener(event -> openForm(new Entry()));

        add(new H1(new Text("TODO")), toolbar, grid);

    }

    private void refreshGrid(String filter, Set<Status> statuses) {
        grid.setItems(entryService.findAll(filter, statuses));
    }

    private void openForm(Entry entry) {
        EntryFormDialog entryForm = new EntryFormDialog(entryService.findAllCategories());

        entryForm.addSaveListener(event -> {
            entryService.save(event.getEntry());
            entryForm.close();
            refreshGrid(toolbar.getFilter(), toolbar.getSelectedStatuses());
        });

        entryForm.addDeleteListener(event -> {
            entryService.delete(event.getEntry());
            entryForm.close();
            refreshGrid(toolbar.getFilter(), toolbar.getSelectedStatuses());
        });

        entryForm.addCancelListener(event -> entryForm.close());

        entryForm.setEntry(entry);
        // Add default theme to the form
        entryForm.getElement().setAttribute("theme", "default");
        entryForm.open();
    }

}
