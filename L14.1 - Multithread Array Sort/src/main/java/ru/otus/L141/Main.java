package ru.otus.L141;

public class Main {
    public static void main(String args[]) {
        int[] test1 = new int[]{1, 2, 4, 3, 10, 0, -1, 8, 6, 33, 1, 20, 5, 10, 45};
        Main.printArray(new SortArray(test1, 4).sort());
    }

    private static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }

}
