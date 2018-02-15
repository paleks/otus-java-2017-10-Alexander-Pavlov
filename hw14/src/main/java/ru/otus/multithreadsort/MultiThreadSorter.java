package ru.otus.multithreadsort;

import java.util.Arrays;

public class MultiThreadSorter {

    private int[] source;
    private SortThread[] threads;
    private int size;

    public MultiThreadSorter(int[] source, int threadsQuantity) {
        if (source.length / threadsQuantity < 1) {
            throw new RuntimeException();
        }
        this.size = source.length / threadsQuantity;

        this.source = source;
        this.threads = new SortThread[threadsQuantity];
    }

    public int[] sort() throws InterruptedException {
        int lo = 0;
        int hi = this.size;
        for (int i = 0; i < threads.length; i++) {
            this.threads[i] = new SortThread(Arrays.copyOfRange(this.source, lo, hi));
            lo = hi;
            hi += this.size;
        }

        Arrays.stream(this.threads).forEach(thread -> thread.start());
        for (SortThread thread : this.threads) {
            thread.join();
        }

        return merge();
        //return merge(this.threads[0].getResult(), this.threads[1].getResult());
    }

    private int[] merge() {
        int[] arr = merge(this.threads[0].getResult(), this.threads[1].getResult());
        for (int i = 2; i < this.threads.length; i++) {
            arr = merge(arr, this.threads[i].getResult());
        }
        return arr;
    }

    private int[] merge(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length + arr2.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (i > arr1.length - 1) {
                arr[k] = arr2[j];
                j++;
            } else if (j > arr2.length - 1) {
                arr[k] = arr1[i];
                i++;
            } else if (arr2[j] < arr1[i]) {
                arr[k] = arr2[j];
                j++;
            } else {
                arr[k] = arr1[i];
                i++;
            }
        }
        return arr;
    }
}
