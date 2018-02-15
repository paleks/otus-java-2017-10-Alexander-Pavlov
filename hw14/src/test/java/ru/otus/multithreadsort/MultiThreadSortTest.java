package ru.otus.multithreadsort;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class MultiThreadSortTest {

    @Test
    public void sortTest() throws InterruptedException {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9});
        MultiThreadSorter sorter = new MultiThreadSorter(source, 3);
        int[] result = sorter.sort();
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, result);
    }

    private int[] shuffle(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            int index = random.nextInt(array.length);
            array[i] = array[index];
            array[index] = temp;
        }
        return array;
    }

    @Test
    public void testik() {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9,10});
        for (int i : source) {
            System.out.print(i + " ");
        }
        System.out.println();
        Arrays.sort(source, 0, 6);
        for (int i : source) {
            System.out.print(i + " ");
        }
    }
}