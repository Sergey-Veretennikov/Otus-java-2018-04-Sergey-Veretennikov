package ru.otus.l0211;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

/**
 * -XX:-UseCompressedOops -Xmx64m -Xms64m -XX:+PrintGCDetails
 */

public class Main {

    public static final int SIZE = 100_000;

    public static void main(String[] args) {
        printUsage(() -> new Object(), "Object");
        printUsage(() -> new MyClass(), "myclass");
        printUsage(() -> new Integer(1), "1");
        printUsage(() -> new String(new char[]{'s', 't', 'r', 'i', 'n', 'g'}), "String \"string\"");
        printUsage(String::new, "String \" \"");
        printUsage(() -> Calendar.getInstance(), "Calendar");
        printUsage(() -> new BigDecimal("999999999999999.999"), "BigDecimal 999999999999999.999");
        printUsage(() -> new ArrayList<String>(), "ArrayList<String>");
        printUsage(() -> new Integer[100], "Integer[100]");

    }

    /**
     * @param supplier - генерирует значения для заполнения массива.
     * @param comment  - описание теста
     * @return Object чтобы JVM не съела ссылку. Или можно завести массив как поле класса
     */
    private static <T> Object printUsage(@NotNull Supplier<T> supplier, @NotNull String comment) {
        final Object[] objs = new Object[SIZE];
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        for (int i = 0; i < SIZE; i++) {
            objs[i] = supplier.get();
        }

        runtime.gc();
        long memAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println(String.format("\n%-20s\tSIZEOF(): %d bytes\n", comment, Math.round((double) (memAfter - memBefore) / SIZE)));

        return objs;
    }

    private static class MyClass {
        private int i = 0;
        private long l = 1;
    }
}
