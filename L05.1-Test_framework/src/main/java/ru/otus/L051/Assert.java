package ru.otus.L051;

public class Assert {
    public static void fail(final String msg) {
        System.out.println(msg);
    }

    public static void isNull(final Object object) {
        if (object == null) System.out.println("NULL  --  TEST PASSED");
        else
            System.out.println("NOT NULL  --  TEST FAILED");
    }

    public static void isNotNull(final Object object) {
        if (object != null) System.out.println("NOT NULL  --  TEST PASSED");
        else
            System.out.println("NULL  --  TEST FAILED");
    }

    public static void isTrue(final boolean value) {
        if (value) System.out.println("TRUE  --  TEST PASSED");
        else
            System.out.println("FALSE  --  TEST FAILED");
    }

    public static void isEquals(final Object expected, final Object actual) {
        if (expected.equals(actual)) System.out.println("EQUAL  --  TEST PASSED");
        else
            System.out.println("NOT EQUAL  --  TEST FAILED");
    }
}
