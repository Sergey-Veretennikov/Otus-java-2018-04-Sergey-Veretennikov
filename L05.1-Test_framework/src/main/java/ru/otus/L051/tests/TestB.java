package ru.otus.L051.tests;

import ru.otus.L051.Assert;
import ru.otus.L051.annotations.Test;

public class TestB {

    @Test
    public void okAgain() {
        Assert.isTrue(true);
    }

    @Test
    public void failedAgain() {
        Assert.isTrue(false);
    }
}
