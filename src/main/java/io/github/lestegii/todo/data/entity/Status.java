package io.github.lestegii.todo.data.entity;

import jakarta.annotation.Nonnull;

/**
 * Represents the status of an entry.
 */
public enum Status {

    DONE("Done"),
    WORK_IN_PROGRESS("Work in progress"),
    TODO("To do");

    private final @Nonnull String status;

    Status(@Nonnull String status) {
        this.status = status;
    }

    /**
     * Returns the status as a string.
     *
     * @return the status as a string
     */
    public @Nonnull String status() {
        return this.status;
    }

    /**
     * Returns true if this status is done.
     *
     * @return true if this status is done
     */
    public boolean isDone() {
        return this == DONE;
    }

    @Override
    public @Nonnull String toString() {
        return this.status;
    }

}
