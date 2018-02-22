package ru.otus.multithreadsort;

import java.util.Arrays;

class IntArraySortThread extends Thread {

    private int[] array;
    private boolean isSorted;

    IntArraySortThread(int[] arrayToSort) {
        this.array = arrayToSort;
    }

    @Override
    public void run() {
        Arrays.sort(array);
        this.isSorted = true;
    }

    int[] getResult() {
        return array;
    }
}
