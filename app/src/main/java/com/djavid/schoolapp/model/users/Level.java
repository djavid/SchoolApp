package com.djavid.schoolapp.model.users;

/**
 * @author Andrei Kolomiets
 */
public enum Level {
    None,
    Student,
    Teacher,
    Admin;

    private static Level[] values = Level.values();

    public static Level valueOf(int level) {
        if (level >= 0 && level < values.length) {
            return values[level];
        } else {
            return None;
        }
    }
}
