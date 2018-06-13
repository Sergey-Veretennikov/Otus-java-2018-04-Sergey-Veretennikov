package ru.otus.L081;

import com.google.gson.Gson;

import java.util.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        new Main().run();
    }

    public void run() throws IllegalAccessException {
        Json json = new Json();
        Test2 test1 = new Test2();
        String result = json.execJcon(test1);
        System.out.println(result);
        Test2 jsTest = new Gson().fromJson(result, Test2.class);
        System.out.println(result);
        System.out.println(test1.equals(jsTest));

    }

    class Test1 {
        private String name = "Name";
        private Integer age = 1;
        private Long number = 10L;
    }

    class Test2 {
        private String name = "Name";
        private int age = 1;
        private long number = 10L;
        private Test1 test1 = new Test1();
        private Integer[] a = new Integer[]{1, 2, 3};

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ru.otus.L081.Main.Test2)) return false;

            ru.otus.L081.Main.Test2 test2 = (ru.otus.L081.Main.Test2) o;

            if (age != test2.age) return false;
            if (number != test2.number) return false;
            return name != null ? name.equals(test2.name) : test2.name == null;
        }
    }

    class Test3 {
        private Integer[] a = new Integer[]{1, 2, 3};
        private List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        private List<String> list2 = new ArrayList<>(Arrays.asList("1", "sdfa", "asf"));
        private List<String> list3 = new LinkedList<>(Arrays.asList("1", "sdfa", "asf"));
        private Set<String> set = new HashSet<>(Arrays.asList("1", "sdfa", "asf"));
    }

    class Test4 {
        private Map<Integer, Integer> map;

        public Test4() {
            map = new HashMap<>();
            map.put(1, 2);
            map.put(3, 3);
            map.put(5, 1);
        }
    }

    class Test5 {
        private Test2[] test3Arr = new Test2[]{new Test2(), new Test2(), new Test2()};
        private Set<Test1> set = new HashSet<>(Arrays.asList(new Test1(), new Test1()));
        private String str = "asdfa";
        private int[] arr = new int[]{1, 2, 3};
    }
}
