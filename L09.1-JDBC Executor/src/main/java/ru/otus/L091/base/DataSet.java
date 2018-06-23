package ru.otus.L091.base;

public abstract class DataSet {
    private long id;

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "id=" + id +
                '}';
    }
}
