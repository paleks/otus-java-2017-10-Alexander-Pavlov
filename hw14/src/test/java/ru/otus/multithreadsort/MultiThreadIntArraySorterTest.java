package ru.otus.multithreadsort;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class MultiThreadIntArraySorterTest {

    @Test
    public void sortTest0() throws InterruptedException {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9});
        MultiThreadIntArraySorter sorter = new MultiThreadIntArraySorter(source, 1);
        int[] result = sorter.sort();
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, result);
    }

    @Test
    public void sortTest1() throws InterruptedException {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9});
        MultiThreadIntArraySorter sorter = new MultiThreadIntArraySorter(source, 3);
        int[] result = sorter.sort();
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, result);
    }

    @Test
    public void sortTest2() throws InterruptedException {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9});
        MultiThreadIntArraySorter sorter = new MultiThreadIntArraySorter(source, 5);
        int[] result = sorter.sort();
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, result);
    }

    @Test
    public void sortTest3() throws InterruptedException {
        int[] source = shuffle(new int[]{1,2,3,4,5,6,7,8,9});
        MultiThreadIntArraySorter sorter = new MultiThreadIntArraySorter(source, 100);
        int[] result = sorter.sort();
        Assert.assertArrayEquals(new int[]{1,2,3,4,5,6,7,8,9}, result);
    }

    @Test
    public void sortTest4() throws InterruptedException {
        int[] source = new int[999];
        for (int i = 0; i < 999; i++) {
            source[i] = i;
        }
        shuffle(source);
        MultiThreadIntArraySorter sorter = new MultiThreadIntArraySorter(source, 99);
        int[] result = sorter.sort();
        Arrays.sort(source);
        Assert.assertArrayEquals(source, result);
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
}