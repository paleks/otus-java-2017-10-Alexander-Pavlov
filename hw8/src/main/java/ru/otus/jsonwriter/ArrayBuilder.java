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
                sb.append("\"" + object + "\"");
                if (iter.hasNext()) {
                    sb.append(",");
                }
            } else if (Utils.isGenericBuilder(object)) {
                sb.append(((GenericBuilder) object).build());
                if (iter.hasNext()) {
                    sb.append(",");
                }
            } else {
                throw new UnsupportedOperationException("At this moment type is not supported");
            }
        }
        return "[" + sb.toString() + "]";
    }
}