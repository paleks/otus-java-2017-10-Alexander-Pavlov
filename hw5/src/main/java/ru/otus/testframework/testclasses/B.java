package ru.otus.testframework.testclasses;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;

public class B {
    @Before
    public void testBefore() {
        System.out.println("=======>>> " + this + " | testBefore");
    }

    @Test
    public void test1() {
        System.out.println("=======>>> " + this + " | test1B");
    }

    @Test
    public void test2() {
        System.out.println("=======>>> " + this + " | test2B");
    }

    @Test
    public void test3() {
        System.out.println("=======>>> " + this + " | test3B");
    }

    @After
    public void testAfter() {
        System.out.println("=======>>> " + this + " | testAfter");
    }
}
