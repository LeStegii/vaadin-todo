package io.github.lestegii.todo.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an entry in the todo list.
 * <p>
 * An entry has a title, a description, a priority and a status.
 * An entry might have a short description allowing to display a short version of the description.
 */
@Entity
public class Entry {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @NotEmpty
    private String title;
    private String shortDescription;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    private Priority priority;
    private String customPriority;
    @NotNull
    private Status status;
    private String category;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime updated;
    private LocalDateTime dueDate;
    @NotNull
    private UUID owner;

    /**
     * Default constructor for JPA.
     *
     * @deprecated for JPA only
     */
    @Deprecated
    public Entry() {
    }

    /**
     * Creates a new entry with the given title, short description, description, priority and status.
     *
     * @param title            the title
     * @param shortDescription the short description
     * @param description      the description
     * @param priority         the priority
     * @param status           the status
     */
    public Entry(@NotNull String title, @Nullable String shortDescription, @NotNull String description, @NotNull Priority priority, @NotNull Status status, @Nullable String category, @Nullable LocalDateTime dueDate, @NotNull UUID owner) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
        this.category = category;
        this.dueDate = dueDate;
        this.owner = owner;
    }

    /**
     * Creates a new entry with the given title, description, priority and status.
     *
     * @param title       the title
     * @param description the description
     * @param priority    the priority
     * @param status      the status
     * @param owner       the owner
     */
    public Entry(@NotNull String title, @NotNull String description, @NotNull Priority priority, @NotNull Status status, @NotNull UUID owner) {
        this(title, null, description, priority, status, null, null, owner);
    }

    /**
     * Creates a new entry with the given title, description, priority, status, category and due date.
     *
     * @param title       the title
     * @param description the description
     * @param priority    the priority
     * @param status      the status
     * @param category    the category
     * @param dueDate     the due date
     */
    public Entry(@NotNull String title, @NotNull String description, @NotNull Priority priority, @NotNull Status status, @Nullable String category, @Nullable LocalDateTime dueDate, @NotNull UUID owner) {
        this(title, null, description, priority, status, category, dueDate, owner);
    }

    public @Nullable String customPriority() {
        return this.customPriority;
    }

    public void customPriority(@Nullable String customPriority) {
        this.customPriority = customPriority;
    }

    public void created(@NotNull LocalDateTime created) {
        Objects.requireNonNull(created);
        this.created = created;
    }

    public @NotNull LocalDateTime created() {
        return this.created;
    }

    public @NotNull LocalDateTime updated() {
        return this.updated;
    }

    public void updated(@NotNull LocalDateTime updated) {
        Objects.requireNonNull(updated);
        this.updated = updated;
    }

    public @Nullable LocalDateTime dueDate() {
        return this.dueDate;
    }

    public void dueDate(@Nullable LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public @Nullable String category() {
        return this.category;
    }

    public void category(@Nullable String category) {
        this.category = category;
    }

    public @NotNull String title() {
        return this.title;
    }

    public void title(@NotNull String title) {
        Objects.requireNonNull(title);
        this.title = title;
    }

    public @Nullable String shortDescription() {
        return this.shortDescription;
    }

    public void shortDescription(@Nullable String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public @NotNull String description() {
        return this.description;
    }

    public void description(@NotNull String description) {
        Objects.requireNonNull(description);
        this.description = description;
    }

    public @NotNull Priority priority() {
        return this.priority;
    }

    public void priority(@NotNull Priority priority) {
        Objects.requireNonNull(priority);
        this.priority = priority;
    }

    public @NotNull Status status() {
        return this.status;
    }

    public void status(@NotNull Status status) {
        this.status = status;
    }

    public void id(long id) {
        this.id = id;
    }

    public long id() {
        return this.id;
    }

    public @NotNull UUID owner() {
        return owner;
    }

    public void owner(@NotNull UUID owner) {
        this.owner = owner;
    }
}
