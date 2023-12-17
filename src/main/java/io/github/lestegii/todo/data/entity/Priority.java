package io.github.lestegii.todo.data.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Represents the priority of an entry.
 */
public enum Priority {

    VERY_HIGH("Very high"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
    VERY_LOW("Very low"),
    OTHER();

    private final @Nullable String priority;

    Priority(@Nullable String priority) {
        this.priority = priority;
    }

    Priority() {
        this(null);
    }

    /**
     * Returns the priority as a string or null if the priority is a custom priority.
     *
     * @return the priority as a string
     */
    public @Nullable String priority() {
        return this.priority;
    }

    /**
     * Returns true if this priority is higher than the other priority.
     *
     * @param other the other priority
     * @return true if this priority is higher than the other priority
     */
    public boolean isHigherThan(@Nonnull Priority other) {
        return ordinal() > other.ordinal();
    }

    /**
     * Returns true if this priority is lower than the other priority.
     *
     * @param other the other priority
     * @return true if this priority is lower than the other priority
     */
    public boolean isLowerThan(@Nonnull Priority other) {
        return ordinal() < other.ordinal();
    }

    @Override
    public @Nonnull String toString() {
        return this.priority == null ? name() : this.priority;
    }

}
