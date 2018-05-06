package ru.otus.l031;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyIterator<T> implements Iterator<T>, ListIterator<T> {
    private int current = -1;
    private List<T> list;

    public MyIterator(List<T> list) {
        this.list = list;
    }

    public MyIterator(int current, List<T> list) {
        this.current = current;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return current < list.size() - 1;
    }

    @Override
    public T next() {
        if (hasNext()) current++;
        return list.get(current);
    }

    @Override
    public boolean hasPrevious() {
        return current > 0 && list.size() > 0;
    }

    @Override
    public T previous() {
        if (hasPrevious()) current--;
        return list.get(current);
    }

    @Override
    public int nextIndex() {
        if (hasNext()) return (current + 1);
        return -1;
    }

    @Override
    public int previousIndex() {
        if (hasPrevious()) return (current - 1);
        return -1;
    }

    @Override
    public void remove() {
        list.remove(current);
    }

    @Override
    public void set(T t) {
        list.set(current, t);
    }

    @Override
    public void add(T t) {
        list.set(current, t);
    }
}
