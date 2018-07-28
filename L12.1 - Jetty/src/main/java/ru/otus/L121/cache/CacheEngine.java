package ru.otus.L121.cache;

public interface CacheEngine<K, V> {

    void put(K key, CacheElement<V> cacheElement);

    CacheElement<V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
