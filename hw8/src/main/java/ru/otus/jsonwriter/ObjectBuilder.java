package ru.otus.jsonwriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectBuilder implements GenericBuilder {
    private Map<String, Object> map = new HashMap<>();

    public void add(String name, Object value) {
        map.put(name, value);
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            Object val = entry.getValue();

            if (Utils.isString(val) || Utils.isNumber(val)) {
                sb.append("\"" + key).append("\":").append("\"" + val).append("\"");
                if (iter.hasNext()) {
                    sb.append(",");
                }
            } else if (Utils.isGenericBuilder(val)) {
                sb.append("\"" + key).append("\":").append(((GenericBuilder) val).build());
                if (iter.hasNext()) {
                    sb.append(",");
                }
            }
        }
        return "{" + sb.toString() + "}";
    }
}
