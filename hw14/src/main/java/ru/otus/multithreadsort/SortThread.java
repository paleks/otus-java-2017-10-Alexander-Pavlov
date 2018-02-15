package ru.otus.multithreadsort;

import java.util.Arrays;

public class SortThread extends Thread {

    private int[] arrayToSort;

    public SortThread(int[] arrayToSort) {
        this.arrayToSort = arrayToSort;
    }

    @Override
    public void run() {
        Arrays.sort(arrayToSort);
    }

    public int[] getResult() {
        return arrayToSort;
    }
}
