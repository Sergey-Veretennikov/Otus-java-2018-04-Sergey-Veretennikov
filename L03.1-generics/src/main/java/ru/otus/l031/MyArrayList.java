package ru.otus.l031;

import java.util.*;
import java.util.Collections;


public class MyArrayList<T> implements List<T> {

    private Object[] elementData;
    private static final int DEFAULT_CAPACITY = 10;
    private int size = 0;

    public MyArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException();
        elementData = new Object[initialCapacity];
    }

    public MyArrayList(T[] elementData) {
        this.elementData = elementData;
        size = elementData.length;
    }

    public MyArrayList(Collection<T> c) {
        this.elementData = c.toArray();
        size = c.size();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public Iterator<T> iterator() {
        return new MyIterator<>(this);
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        if (size + 1 <= elementData.length) {
            elementData[size++] = t;
            return true;
        } else {
            expand();
            elementData[size++] = t;
            return false;
        }
    }

    private void expand() {
        int oldSize = elementData.length;
        if (oldSize == Integer.MAX_VALUE)
            throw new ArrayIndexOutOfBoundsException("Превышен допустимый размер списка.");
        int newSize = (oldSize * 3) / 2 + 1;
        if (newSize < 0) newSize = Integer.MAX_VALUE;
        elementData = Arrays.copyOf(elementData, newSize);
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        elementData = Arrays.copyOf(elementData, size + c.size());
        System.arraycopy(c.toArray(), 0, elementData, size, c.size());
        size = elementData.length;
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            for (int i = 0; i < elementData.length; ) {
                if (o.equals(elementData[i])) {
                    remove(i);
                } else i++;
            }
        }
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) elementData[index];
    }

    public T set(int index, T element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T currentValue = (T) elementData[index];
        elementData[index] = element;
        return currentValue;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        T oldValue = (T) elementData[index];
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null) return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i])) return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null) return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i])) return i;
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return new MyIterator<>(this);
    }

    public ListIterator<T> listIterator(int index) {
        return new MyIterator<>(index, this);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (!it.hasNext())
            return "[ ]";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (; ; ) {
            T t = it.next();
            stringBuilder.append(t == this ? "(this Collection)" : t);
            if (!it.hasNext())
                return stringBuilder.append("]").toString();
            stringBuilder.append(',').append(' ');
        }
    }

    public class MyIterator<T> implements ListIterator<T> {
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
}
