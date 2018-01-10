package ru.otus.jsonwriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

public class MyJsonWriter {
    public static String write(Object object) throws IllegalAccessException {
        if (object == null) {
            return "null";
        } else if (Utils.isString(object)) {
            return "\"" + object.toString() + "\"";
        } else if (Utils.isNumber(object)) {
            return object.toString();
        } else if (Utils.isCollection(object) || Utils.isArray(object)) {
            return createJsonArrayTree(object).build();
        } else {
            return createJsonObjectTree(object).build();
        }
    }

    private static GenericBuilder createJsonArrayTree(Object object) throws IllegalAccessException {
        ArrayBuilder ab = new ArrayBuilder();

        if (Utils.isCollection(object)) {
            for (Object element : (Collection) object) {
                if (Utils.isNumber(element) || Utils.isString(element)) {
                    ab.add(element);
                } else {
                    ab.add(createJsonObjectTree(element));
                }
            }
        } else if (Utils.isArray(object)) {
            if (Array.getLength(object) > 0) {
                Object zeroElement = Array.get(object, 0);

                if (Utils.isArray(zeroElement)) {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object array = Array.get(object, i);
                        if (array != null) {
                            ab.add(createJsonArrayTree(array));
                        };
                    }
                } else if (Utils.isNumber(zeroElement) || Utils.isString(zeroElement)) {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object element = Array.get(object, i);
                        if (element != null) {
                            ab.add(element);
                        };
                    }
                } else {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object element = Array.get(object, i);
                        if (element != null) {
                            ab.add(createJsonObjectTree(element));
                        };
                    }
                }
            }
        }
        return ab;
    }

    private static GenericBuilder createJsonObjectTree(Object object) throws IllegalAccessException {
        ObjectBuilder ob = new ObjectBuilder();

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(object);

            if (value != null) {
                if (Utils.isString(value) || Utils.isNumber(value)) {
                    ob.add(field.getName(), value.toString());
                    continue;
                } else if (Utils.isArray(value)) {
                    ob.add(field.getName(), createJsonArrayTree(value));
                } else {
                    ob.add(field.getName(), createJsonObjectTree(value));
                }
            }
        }

        return ob;
    }
}
