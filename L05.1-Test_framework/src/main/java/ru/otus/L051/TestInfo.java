package ru.otus.L051;

import ru.otus.L051.annotations.After;
import ru.otus.L051.annotations.Before;
import ru.otus.L051.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestInfo {
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final Class clazz;

    public TestInfo(Class clazz) {
        this.clazz = clazz;
        creationTestInfo();
    }

    public void creationTestInfo() {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method met : methods) {
            if (met.isAnnotationPresent(Before.class)) this.beforeMethods.add(met);
            if (met.isAnnotationPresent(Test.class)) this.testMethods.add(met);
            if (met.isAnnotationPresent(After.class)) this.afterMethods.add(met);
        }
    }

    public List<Method> getBeforeMethods() {
        return Collections.unmodifiableList(beforeMethods);
    }

    public List<Method> getTestMethods() {
        return Collections.unmodifiableList(testMethods);
    }

    public List<Method> getAfterMethods() {
        return Collections.unmodifiableList(afterMethods);
    }

    public Class getClazz() {
        return clazz;
    }
}
