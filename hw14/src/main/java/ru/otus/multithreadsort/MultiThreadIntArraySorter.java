package ru.otus.multithreadsort;

import java.util.Arrays;

class MultiThreadIntArraySorter {

    private int partSize;
    private int remainderSize;
    private int[] source;
    private IntArraySortThread[] threads;

    MultiThreadIntArraySorter(int[] source, int threadsQuantity) {
        if (source == null || source.length == 0 || threadsQuantity == 0) {
            throw new RuntimeException("Incorrect input data");
        }
        if (source.length / threadsQuantity < 1) {
            threadsQuantity = 1;
        }
        this.source = source;
        this.partSize = source.length / threadsQuantity;
        this.remainderSize = source.length % threadsQuantity;
        this.threads = new IntArraySortThread[threadsQuantity];
    }

    int[] sort() throws InterruptedException {
        int lo = 0;
        int hi = this.partSize + this.remainderSize;
        for (int i = 0; i < this.threads.length; i++) {
            this.threads[i] = new IntArraySortThread(Arrays.copyOfRange(this.source, lo, hi));
            lo = hi;
            hi += this.partSize;
        }
        // all threads starting
        Arrays.stream(this.threads).forEach(Thread::start);
        // waiting for ending of all threads execution
        for (IntArraySortThread thread : this.threads) {
            thread.join();
        }
        // merging into single array
        return merge();
    }

    private int[] merge() {
        if (this.threads.length == 1) {
            return this.threads[0].getResult();
        }
        int[] arr = merge(this.threads[0].getResult(), this.threads[1].getResult());
        for (int i = 2; i < this.threads.length; i++) {
            arr = merge(arr, this.threads[i].getResult());
        }
        return arr;
    }

    private int[] merge(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length + arr2.length];
        int firstArrIndex = 0;
        int secondArrIndex = 0;
        int resultArrIndex = 0;
        for (; resultArrIndex < arr.length; resultArrIndex++) {
            if (firstArrIndex > arr1.length - 1) {
                arr[resultArrIndex] = arr2[secondArrIndex++];
            } else if (secondArrIndex > arr2.length - 1) {
                arr[resultArrIndex] = arr1[firstArrIndex++];
            } else if (arr2[secondArrIndex] < arr1[firstArrIndex]) {
                arr[resultArrIndex] = arr2[secondArrIndex++];
            } else {
                arr[resultArrIndex] = arr1[firstArrIndex++];
            }
        }
        return arr;
    }
}
