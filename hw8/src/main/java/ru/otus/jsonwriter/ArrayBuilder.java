package ru.otus.jsonwriter;

import java.util.*;

public class ArrayBuilder implements GenericBuilder {
    private List<Object> list = new LinkedList<>();

    public void add(Object value) {
        list.add(value);
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        Iterator<Object> iter = list.iterator();

        while (iter.hasNext()) {
            Object object = iter.next();

            if (Utils.isString(object) || Utils.isNumber(object)) {
                if (iter.hasNext()) {
                    sb.append("\"" + object + "\",");
                } else {
                    sb.append("\"" + object + "\"");
                }
            } else if (Utils.isGenericBuilder(object)) {
                if (iter.hasNext()) {
                    sb.append(((GenericBuilder) object).build() + ",");
                } else {
                    sb.append(((GenericBuilder) object).build());
                }
            }
        }
        return "[" + sb.toString() + "]";
    }
}