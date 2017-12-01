package ru.otus.testframework.testclasses;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;

public class A {
    @Before
    public void testBefore() {
        System.out.println("=======>>> " + this + " | testBefore");
    }

    @Test
    public void test1() {
        System.out.println("=======>>> " + this + " | test1A");
    }

    @Test
    public void test2() {
        System.out.println("=======>>> " + this + " | test2A");
    }

    @Test
    public void test3() {
        System.out.println("=======>>> " + this + " | test3A");
    }

    @After
    public void testAfter() {
        System.out.println("=======>>> " + this + " | testAfter");
    }
}
