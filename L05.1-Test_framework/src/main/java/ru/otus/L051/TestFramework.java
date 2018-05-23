package ru.otus.L051;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {

    private static List<TestInfo> testInfoArrayList = new ArrayList<>();
    private static Object Object;


    public TestFramework(Class[] classes) throws IllegalAccessException {
        if (classes == null || classes.length == 0)
            throw new IllegalAccessException("Argument classesWithTests cannot be empty");
        this.testInfoArrayList = getTestMethods(classes);
    }

    public TestFramework(String path)
            throws IllegalAccessException, IOException, ClassNotFoundException, URISyntaxException {

        if (path == null || path.isEmpty())
            throw new IllegalAccessException("argument packageName cannot be empty");
        this.testInfoArrayList = getTestMethodsFromPath(path);
    }

    private List<TestInfo> getTestMethodsFromPath(String path)
            throws IOException, ClassNotFoundException, URISyntaxException {
        ArrayList<Class> classes = ReflectionHelper.getClassesFromPackage(path);
        Class[] result = classes.toArray(new Class[classes.size()]);
        return getTestMethods(result);
    }

    private ArrayList<TestInfo> getTestMethods(Class[] classes) {
        ArrayList<TestInfo> testInfos = new ArrayList<>();
        for (Class clazz : classes) {
            TestInfo testInfo = new TestInfo(clazz);
            testInfos.add(testInfo);
        }
        return testInfos;
    }


    public static void run() {
        for (TestInfo testIn : testInfoArrayList) {
            try {
                Object = testIn.getClazz().newInstance();
                for (Method met : testIn.getBeforeMethods()) met.invoke(Object);
                for (Method met : testIn.getTestMethods()) met.invoke(Object);
                for (Method met : testIn.getAfterMethods()) met.invoke(Object);
                System.out.println("------------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
