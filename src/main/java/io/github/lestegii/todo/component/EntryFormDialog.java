package io.github.lestegii.todo.component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import io.github.lestegii.todo.data.entity.Entry;
import io.github.lestegii.todo.data.entity.Priority;
import io.github.lestegii.todo.data.entity.Status;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class EntryFormDialog extends Dialog {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JavaTimeModule());

    final TextField title = new TextField("Title");
    final TextField shortDescription = new TextField("Summary");
    final TextArea description = new TextArea("Description");
    final ComboBox<Status> status = new ComboBox<>("Status");
    final ComboBox<Priority> priority = new ComboBox<>("Priority");
    final ComboBox<String> category = new ComboBox<>("Category");
    final DateTimePicker dueDate = new DateTimePicker("Due date");

    private final FormLayout formLayout = new FormLayout(
            title, shortDescription, description, status, priority, category, dueDate
    );

    private final Button saveButton = new Button("Save");
    private final Button cancelButton = new Button("Cancel");
    private final Button deleteButton = new Button("Delete");
    private final Button downloadButton = new Button("Download");

    private final BeanValidationBinder<Entry> binder = new BeanValidationBinder<>(Entry.class);
    private final List<String> categories;

    private final MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    private final Upload upload = new Upload(buffer);

    private Entry entry;

    public EntryFormDialog(List<String> categories) {
        addClassName("entry-form");

        this.categories = categories;

        init();
    }

    private void init() {

        // Only allow json
        upload.setAcceptedFileTypes(".json");
        upload.addFailedListener(event -> Notification.show("Upload failed", 3000, Notification.Position.TOP_END));
        upload.setMaxFiles(1);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            upload.clearFileList();
            processJson(inputStream);
        });

        // Download
        FileDownloadWrapper downloader = new FileDownloadWrapper(new StreamResource("entry.json", () -> {
            try {
                return new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsString(binder.getBean().anonymousCopy()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                Notification.show("Unable to download file", 3000, Notification.Position.TOP_END);
            }
            return new ByteArrayInputStream(new byte[0]);
        }));
        downloader.wrapComponent(downloadButton);

        // Text
        title.setRequired(true);
        title.setMinLength(1);
        shortDescription.setRequired(false);
        description.setRequired(true);
        description.setHeight("10em");
        description.setMaxHeight("10em");

        // Status
        status.setItems(Status.values());
        status.setAllowCustomValue(false);
        status.setRequired(true);

        // Priority
        List<Priority> validPriorities = Arrays.stream(Priority.values()).toList();
        priority.setItems(validPriorities);
        priority.setAllowCustomValue(false);
        priority.setRequired(true);

        // Category
        category.setItems(categories);
        category.setAllowCustomValue(true);
        category.addCustomValueSetListener(event -> {
            categories.add(event.getDetail());
            category.setItems(categories);
            category.setValue(event.getDetail());
        });
        category.setRequired(false);

        // Due date
        dueDate.setRequiredIndicatorVisible(true);
        dueDate.setValue(LocalDateTime.now().plusDays(1));
        dueDate.setMin(LocalDateTime.now());

        // Binding fields automatically doesn't work in this case because of the custom priority field and errors
        binder.forField(title).asRequired("Please enter a title.").bind(Entry::title, Entry::title);
        binder.forField(shortDescription).withValidator(
                new StringLengthValidator("Your summary should at most 50 characters long.", null, 50)
        ).bind(Entry::shortDescription, Entry::shortDescription);
        binder.forField(description).asRequired("Please enter a description.").bind(Entry::description, Entry::description);
        binder.forField(status).asRequired("Please select a status.").bind(Entry::status, Entry::status);
        binder.forField(priority).asRequired("Please select a priority.").bind(Entry::priority, Entry::priority);
        binder.bind(category, Entry::category, Entry::category);
        binder.bind(dueDate, Entry::dueDate, Entry::dueDate);

        // Buttons
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addClickListener(event -> validateAndSave());
        deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancelButton.addClickListener(event -> fireEvent(new CancelEvent(this)));
        saveButton.addClickShortcut(Key.ENTER);
        cancelButton.addClickShortcut(Key.ESCAPE);

        setHeaderTitle(this.entry == null ? "New entry" : "Edit entry");
        add(formLayout);
        upload.setSizeUndefined();
        upload.setAutoUpload(true);
        getFooter().add(upload, new HorizontalLayout(saveButton, deleteButton, cancelButton, downloader));
    }

    private void processJson(InputStream inputStream) {
        try {
            String content = new String(inputStream.readAllBytes());
            Entry readEntry = OBJECT_MAPPER.readValue(content, Entry.class);
            binder.readBean(readEntry);
        } catch (IOException e) {
            e.printStackTrace();
            Notification.show("Unable to read file", 3000, Notification.Position.TOP_END);
        }
    }

    private void validateAndSave() {
        if (!binder.validate().hasErrors()) {
            try {
                if (entry.created() == null) {
                    entry.created(LocalDateTime.now());
                    entry.owner(UI.getCurrent().getSession().getAttribute("username").toString());
                }
                binder.writeBean(entry);
                entry.updated(LocalDateTime.now());
                fireEvent(new SaveEvent(this, binder.getBean()));
            } catch (Exception e) {
                Notification.show("Unable to save entry", 3000, Notification.Position.TOP_END);
            }
        } else {
            Notification.show("Invalid!", 3000, Notification.Position.TOP_END);
        }
    }

    public void setEntry(Entry toDisplay) {
        this.entry = toDisplay;
        binder.setBean(toDisplay);
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
        return addListener(CancelEvent.class, listener);
    }

    // Events
    public static abstract class EntryFormEvent extends ComponentEvent<EntryFormDialog> {

        private final Entry entry;

        protected EntryFormEvent(EntryFormDialog source, Entry entry) {
            super(source, false);
            this.entry = entry;
        }

        public Entry getEntry() {
            return entry;
        }
    }

    public static class SaveEvent extends EntryFormEvent {
        SaveEvent(EntryFormDialog source, Entry entry) {
            super(source, entry);
        }
    }

    public static class DeleteEvent extends EntryFormEvent {
        DeleteEvent(EntryFormDialog source, Entry entry) {
            super(source, entry);
        }

    }

    public static class CancelEvent extends EntryFormEvent {
        CancelEvent(EntryFormDialog source) {
            super(source, null);
        }
    }

}
