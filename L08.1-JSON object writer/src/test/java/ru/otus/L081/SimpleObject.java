package ru.otus.L081;

import java.util.*;

public class SimpleObject {

    private String str = "String";
    private Integer age = 1;
    private Long weight = 3L;
    private short height = 7;
    private int[] params = new int[]{1, 2, 3, 4};
    private String[] strings = new String[]{"a", "b", "c"};
    char[] chars = new char[]{'a', 'b', 'c'};
    private List<Integer> list = new ArrayList<>(Arrays.asList(100, 200, 300));
    private Set<Long> set = new LinkedHashSet<>(Arrays.asList(3L, 6L));
    private SubItem subItem = new SubItem();
    private SubItem[] subItems = new SubItem[]{new SubItem()};
    private Set<SuperSubItem> superSubItems = new LinkedHashSet<>
            (Arrays.asList(new SuperSubItem(), new SuperSubItem()));


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleObject that = (SimpleObject) o;

        if (height != that.height) return false;
        if (str != null ? !str.equals(that.str) : that.str != null) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (!Arrays.equals(params, that.params)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(strings, that.strings)) return false;
        if (list != null ? !list.equals(that.list) : that.list != null) return false;
        return set != null ? set.equals(that.set) : that.set == null;
    }

    @Override
    public int hashCode() {
        int result = str != null ? str.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (int) height;
        result = 31 * result + Arrays.hashCode(params);
        result = 31 * result + Arrays.hashCode(strings);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        result = 31 * result + (set != null ? set.hashCode() : 0);
        return result;
    }

    class SubItem {
        private int a = new Random().nextInt();
        private Long b = new Random().nextLong();
    }

    class SuperSubItem {
        private int c = new Random().nextInt();
        private SubItem subItem = new SubItem();
    }
}
