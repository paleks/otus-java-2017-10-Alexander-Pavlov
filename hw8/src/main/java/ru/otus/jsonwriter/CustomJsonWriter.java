package ru.otus.jsonwriter;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

public class CustomJsonWriter {

    public static String write(Object object) {
        if (object == null) {
            return "null";
        } else if (isString(object) || isCharacter(object)) {
            return "\"" + object.toString() + "\"";
        } else if (isBoolean(object) || isNumber(object)) {
            return object.toString();
        } else if (isCollection(object) || isArray(object)) {
            return createJsonArrayTree(object).build().toString();
        } else {
            return createJsonObjectTree(object).build().toString();
        }
    }

    private static JsonArrayBuilder createJsonArrayTree(Object object) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        if (isCollection(object)) {
            for (Object element : (Collection) object) {
                if (isNumber(element) || isString(element) || isCharacter(element) || isBoolean(element)) {
                    jsonArrayBuilder.add(element.toString());
                } else {
                    jsonArrayBuilder.add(createJsonObjectTree(element));
                }
            }
        } else if (isArray(object)) {
            if (Array.getLength(object) > 0) {
                Object zeroElement = Array.get(object, 0);
                if (isArray(zeroElement)) {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object array = Array.get(object, i);
                        if (array != null) {
                            jsonArrayBuilder.add(createJsonArrayTree(array));
                        };
                    }
                } else if (isNumber(zeroElement) || isString(zeroElement) || isCharacter(zeroElement) || isBoolean(zeroElement)) {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object element = Array.get(object, i);
                        if (element != null) {
                            jsonArrayBuilder.add(element.toString());
                        };
                    }
                } else {
                    for (int i = 0; i < Array.getLength(object); i ++) {
                        Object element = Array.get(object, i);
                        if (element != null) {
                            jsonArrayBuilder.add(createJsonObjectTree(element));
                        };
                    }
                }
            }
        }
        return jsonArrayBuilder;
    }

    private static JsonObjectBuilder createJsonObjectTree(Object object) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    if (field.getType().isPrimitive() || isString(value)) {
                        jsonObjectBuilder.add(field.getName(), value.toString());
                        continue;
                    } else if (isArray(value)) {
                        jsonObjectBuilder.add(field.getName(), createJsonArrayTree(value));
                        continue;
                    } else {
                        jsonObjectBuilder.add(field.getName(), createJsonObjectTree(value));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder;
    }

    private static boolean isString(Object object) {
        return object instanceof String;
    }

    private static boolean isCharacter(Object object) {
        return object instanceof Character;
    }

    private static boolean isBoolean(Object object) {
        return object instanceof Boolean;
    }

    private static boolean isNumber(Object object) {
        return object instanceof Number;
    }

    private static boolean isArray(Object object) {
        return object.getClass().isArray();
    }

    private static boolean isCollection(Object object) {
        return object instanceof Collection;
    }
}
