package ru.otus.measuringstand.main;

import ru.otus.measuringstand.agent.MeasureAgent;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.openjdk.jol.info.*;

/*
 * $java -XX:-UseCompressedOops -javaagent:target\hw2.jar -jar target\hw2.jar
 */
public class MeasuringStand {

    private LinkedList<Object> stack = new LinkedList<Object>();
    private List<Object> visited = new ArrayList<Object>();

    public static void main(String[] args) {
        MeasuringStand stand = new MeasuringStand();
        stand.printSize(new Object());
        stand.printSize(new String());
        stand.printSize(new String("Hello World"));
        stand.printSize(new int[0]);
        stand.printSize(new int[100]);
        stand.printSize(new int[1000]);
        stand.printSize(new HashSet<>());
        stand.printSize(new HashMap<>());
        stand.printSize(new ConcurrentHashMap<>());
    }

    private void printSize(Object object) {
        System.out.println(String.format("%-30s(%-25s)\t %-10s\t%-10s\t%-10s\n",
                object.toString(),
                object.getClass(),
                this.measureObjectSize(object, false),
                this.measureObjectSize(object, true),
                GraphLayout.parseInstance(object).totalSize()
        ));
    }

    private long measureObjectSize(Object object, boolean isDeepMeasure) {
        if (isDeepMeasure) {
            stack.clear();
            visited.clear();
            stack.addFirst(object);
            long sum = 0;
            while (!stack.isEmpty()) {
                sum += deepSizeOf(stack.pop());
            }
            return sum;
        } else {
            return sizeOf(object);
        }
    }

    public long deepSizeOf(Object object) {
        if (object == null) {
            return 0;
        }
        if (!visited.contains(object)) {
            visited.add(object);
        } else {
            return 0;
        }

        Class<?> clazz = object.getClass();

        while (clazz != null) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> (!Modifier.isStatic(field.getModifiers())) && !field.getType().isPrimitive())
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object fieldObj = field.get(object);
                            if (!visited.contains(fieldObj)) {
                                stack.push(fieldObj);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
            clazz = clazz.getSuperclass();
        }
        return sizeOf(object);

    }

    private long sizeOf(Object object) {
        return MeasureAgent.getObjectSize(object);
    }

}
