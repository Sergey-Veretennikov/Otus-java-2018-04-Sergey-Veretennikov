package ru.otus.L051.tests;

import ru.otus.L051.Assert;
import ru.otus.L051.annotations.Test;

public class TestA {

    @Test
    public void ok() {
        Assert.isTrue(true);
    }

    @Test
    public void failed() {
        Assert.isTrue(false);
    }
}
