package ru.otus.measuringstand.agent;

import java.lang.instrument.Instrumentation;

public class MeasureAgent {
    private static Instrumentation globalInstrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        System.out.println("premain...");
        globalInstrumentation = inst;
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object object) {
        if (object == null) {
            return 0;
        }
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent is not initialized!");
        }

        return globalInstrumentation.getObjectSize(object);
    }
}
