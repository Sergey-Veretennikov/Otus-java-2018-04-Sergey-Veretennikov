package ru.otus.L051;

import ru.otus.L051.annotations.After;
import ru.otus.L051.annotations.Before;
import ru.otus.L051.annotations.Test;

public class Tests {

    public void somethingWithoutTestAnnotation() {
        System.out.println("------");
    }

    @After
    public void after() {
        System.out.println("@After");
    }

    @Before
    public void before() {
        System.out.println("@Before");
    }

    @Test
    public void fail() {
        Assert.fail("failed test");
    }

    @Test
    public void isNull_ok() {
        Assert.isNull(null);
    }

    @Test
    public void isNull_failed() {
        Assert.isNull("asd");
    }

    @Test
    public void isNotNull_ok() {
        Assert.isNotNull("asd");
    }

    @Test
    public void isNotNull_failed() {
        Assert.isNotNull(null);
    }

    @Test
    public void isTrue_ok() {
        Assert.isTrue(true);
    }

    @Test
    public void isTrue_failed() {
        Assert.isTrue(false);
    }

    @Test
    public void isEquals_ok() {
        Assert.isEquals("asd", "asd");
    }

    @Test
    public void isEquals_failed() {
        Assert.isEquals("asd", "qwe");
    }
}
