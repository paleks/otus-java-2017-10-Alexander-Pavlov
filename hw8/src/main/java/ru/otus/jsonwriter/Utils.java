package ru.otus.jsonwriter;

import java.util.Collection;

public class Utils {
    public static boolean isString(Object object) {
        return object instanceof String;
    }

    public static boolean isNumber(Object object) {
        return object instanceof Number;
    }

    public static boolean isGenericBuilder(Object object) {
        return object instanceof GenericBuilder;
    }

    public static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    public static boolean isCollection(Object object) {
        return object instanceof Collection;
    }
}
