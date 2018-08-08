package ru.otus.L141;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Merge {
    private Queue<int[]> queue;

    public Merge(List<int[]> list) {
        this.queue = new LinkedList<>(list);
    }

    public int[] getResult() {
        while (queue.size() != 1) {
            queue.add(sort(queue.poll(), queue.poll()));
        }
        return queue.poll();
    }

    public static int[] sort(int[] arr1, int[] arr2) {
        int len1 = arr1.length,
                len2 = arr2.length;
        int a = 0, b = 0,
                len = len1 + len2;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            if (b < len2 && a < len1) {
                if (arr1[a] > arr2[b]) result[i] = arr2[b++];
                else result[i] = arr1[a++];
            } else if (b < len2) {
                result[i] = arr2[b++];
            } else {
                result[i] = arr1[a++];
            }
        }
        return result;
    }
}
