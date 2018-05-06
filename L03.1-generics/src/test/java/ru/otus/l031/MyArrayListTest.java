package ru.otus.l031;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class MyArrayListTest {

    @Test
    public void collections_addAll() {
        MyArrayList<String> array = new MyArrayList<>();
        Collections.addAll(array, "Item1", "Item2", "Item3");
        assertArrayEquals(new String[]{"Item1", "Item2", "Item3"}, array.toArray());
    }

    @Test
    public void collections_copy() {
        MyArrayList<String> src = new MyArrayList<>();
        Collections.addAll(src, "Item1", "Item2", "Item3");

        List<String> tmp = Arrays.asList(new String[src.size()]);

        MyArrayList<String> dest = new MyArrayList<>(tmp);
        Collections.copy(dest, src);

        assertArrayEquals(new String[]{"Item1", "Item2", "Item3"}, dest.toArray());
    }

    @Test
    public void collections_sort() {
        MyArrayList<String> src = new MyArrayList<>();
        Collections.addAll(src, "Item2", "Item3", "Item4", "Item1");

        Collections.sort(src);

        assertArrayEquals(new String[]{"Item1", "Item2", "Item3", "Item4"}, src.toArray());
    }
}
