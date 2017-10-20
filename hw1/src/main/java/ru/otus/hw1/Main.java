package ru.otus.hw1;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] strings) {
        List<String> list = Arrays.asList("a", "b", "C", "D", "E", "f");

        new Main().print(list);
    }

    private void print(List<String> list) {
        Lists.reverse(list).forEach(System.out::println);
    }
}
