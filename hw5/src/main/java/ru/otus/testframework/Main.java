package ru.otus.testframework;

import ru.otus.testframework.framework.TestFramework;
import ru.otus.testframework.testclasses.A;
import ru.otus.testframework.testclasses.B;
import ru.otus.testframework.testclasses.C;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        TestFramework.test(A.class);
        TestFramework.test("ru.otus.testframework.testclasses");
    }
}
