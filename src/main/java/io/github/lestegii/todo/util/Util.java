package io.github.lestegii.todo.util;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns true if the given string is null or empty.
     *
     * @param s the string
     * @return true if the given string is null or empty
     */
    public static boolean nullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Returns a string representation of the given date and time.
     *
     * @param time the date and time
     * @return a string representation of the given instant
     */
    public static @NotNull String date(@Nullable LocalDateTime time) {
        return time == null ? "-" : time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

}
