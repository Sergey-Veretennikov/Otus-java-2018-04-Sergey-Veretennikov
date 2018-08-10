package ru.otus.L141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortArray {
    private final int[] array;
    private final int threadCount;
    private final List<int[]> arrays = new ArrayList<>();

    public SortArray(int[] array) {
        this.array = array;
        this.threadCount = 4;
    }

    public SortArray(int[] array, int threadCount) {
        this.array = array;
        this.threadCount = threadCount;
    }

    public int[] sort() {
        List<Thread> threads = new ArrayList<>();

        int pLength = array.length / threadCount;
        int begin = 0;

        pLength = (array.length % threadCount > 0) ? pLength + 1 : pLength;

        for (int i = 0; i < threadCount; i++) {
            arrays.add(new int[1]);

            pLength = ((begin + pLength) < array.length) ? pLength : (array.length - begin);

            Thread worker = getThread(i, Arrays.copyOfRange(array,  begin, begin + pLength));

            worker.start();
            threads.add(worker);

            begin += pLength;
        }

        try {
            for (Thread w : threads)
                w.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Merge(arrays).getResult();
    }

    private Thread getThread(int index, int [] arr) {
        return new Thread(() -> {
            Arrays.sort(arr);
            arrays.set(index, arr);
        });
    }
}