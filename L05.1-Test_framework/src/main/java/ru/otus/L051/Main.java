package ru.otus.L051;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args)
            throws IllegalAccessException, URISyntaxException, IOException, ClassNotFoundException {

        new TestFramework(new Class[]{Tests.class, TestsWithBeforeAndAfter.class}).run();
        new TestFramework("ru.otus.L051.tests").run();
    }
}
