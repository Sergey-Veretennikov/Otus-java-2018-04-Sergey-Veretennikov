package ru.otus.L051;

import ru.otus.L051.annotations.After;
import ru.otus.L051.annotations.Before;
import ru.otus.L051.annotations.Test;

public class TestsWithBeforeAndAfter {

    private int field;

    @Before
    public void beforeTest() {
        System.out.println("@Before");
        this.field = 100;
    }

    @Test
    public void test() {
        Assert.isEquals(100, this.field);
    }

    @After
    public void afterTest() {
        System.out.println("@After");
    }
}
