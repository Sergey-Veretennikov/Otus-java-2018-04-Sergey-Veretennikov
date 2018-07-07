package ru.otus.L111.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheEngineImplTest {
    @Test
    public void put_simple() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());

        cacheEngine.put(key, new CacheElement<>(obj));
        assertEquals(1, ((CacheEngineImpl) cacheEngine).size());
    }

    @Test
    public void put_twoObjects() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj1 = new String("string value");
        String key1 = String.valueOf(obj1.hashCode());
        Object obj2 = new String("string value2");
        String key2 = String.valueOf(obj2.hashCode());

        cacheEngine.put(key1, new CacheElement<>(obj1));
        cacheEngine.put(key2, new CacheElement<>(obj2));
        assertEquals(2, ((CacheEngineImpl) cacheEngine).size());
    }

    @Test
    public void put_twoTimes() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());

        cacheEngine.put(key, new CacheElement<>(obj));
        cacheEngine.put(key, new CacheElement<>(obj));
        assertEquals(1, ((CacheEngineImpl) cacheEngine).size());
    }

    @Test
    public void get_object() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());
        cacheEngine.put(key, new CacheElement<>(obj));
        Object fromCache = cacheEngine.get(key);

        assertEquals("string value", ((CacheElement) fromCache).getValue());
    }

    @Test
    public void get_checkCreationTime() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());
        cacheEngine.put(key, new CacheElement<>(obj));
        Object fromCache = cacheEngine.get(key);

        assertTrue(System.currentTimeMillis() - ((CacheElement) fromCache).getCreationTime() < 500);
    }

    @Test
    public void get_increaseCacheHitRatio() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());
        cacheEngine.put(key, new CacheElement<>(obj));

        Object cachedItem = cacheEngine.get(key);
        assertEquals(1, cacheEngine.getHitCount());
        cachedItem = cacheEngine.get(key);
        assertEquals(2, cacheEngine.getHitCount());
    }

    @Test
    public void get_Object_missedIncrease() {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 5000, 3000, false);
        Object obj = new String("string value");
        String key = String.valueOf(obj.hashCode());
        cacheEngine.put(key, new CacheElement<>(obj));

        Object cachedItem = cacheEngine.get("some_key");
        assertEquals(1, cacheEngine.getMissCount());
        cachedItem = cacheEngine.get("some_key");
        assertEquals(2, cacheEngine.getMissCount());
    }

    @Test
    public void evict_maxLifeTime_sameForAllElements() throws InterruptedException {
        CacheEngine cacheEngine = new CacheEngineImpl(50, 1000, 500, false);
        for (int i = 0; i < 5; i++) {
            cacheEngine.put(String.valueOf(i), new CacheElement<>("item"));
        }

        assertEquals(5, ((CacheEngineImpl) cacheEngine).size());
        Thread.sleep(2000);
        assertEquals(0, ((CacheEngineImpl) cacheEngine).size());
    }

}