package ru.otus.L081;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;


import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class JsonSimpleObject {
    private Json json;
    private Gson gson;

    @Before
    public void createTest() {
        json = new Json();
        gson = new Gson();
    }

    @Test
    public void nullObject() throws IllegalAccessException {
        Object object = null;
        String js = json.execJcon(object);
        Object gs = gson.fromJson(js, Object.class);

        assertEquals(object, gs);
    }

    @Test
    public void testArray() throws IllegalAccessException {
        Integer[] arr = new Integer[]{1, 2, 3, 4};
        String js = json.execJcon(arr);
        Integer[] gs = gson.fromJson(js, Integer[].class);

        assertEquals(arr, gs);
    }

    @Test
    public void jsonTest() throws IllegalAccessException {
        Set<SimpleObject> set = new HashSet<>(Arrays.asList(new SimpleObject(), new SimpleObject()));
        String js = json.execJcon(set);
        Type type = new TypeToken<Set<SimpleObject>>() {}.getType();
        Set<SimpleObject> gs = gson.fromJson(js, type);

        assertEquals(set, gs);
    }

    @Test
    public void jsonTest2() throws IllegalAccessException {
        SimpleObject item = new SimpleObject();
        String js = json.execJcon(item);
        SimpleObject gs = gson.fromJson(js, SimpleObject.class);

        assertEquals(item, gs);
    }
}