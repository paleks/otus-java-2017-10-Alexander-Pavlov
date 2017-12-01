package ru.otus.testframework.testclasses;

import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;

public class C extends B {

    @Test
    public void test1() {
        System.out.println("=======>>> " + this + " | test1C");
    }

    public void notAnnotatedTest() {
        System.out.println("=======>>> " + this + " | notAnnotatedTest");
    }
}
