package ru.otus.L081;

import com.google.gson.Gson;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ObjectJsonTest {
    private Gson gson = new Gson();
    private Json j_son = new Json();

    @Test
    public void shouldTransformNull() throws IllegalAccessException {
        //given
        Object o = null;

        //when
        String json = j_son.execJcon(o);

        //then
        Assert.assertEquals(gson.toJson(o), json);
    }

    @Test
    public void shouldTransformPrimitive() throws IllegalAccessException {
        //given
        int i = 10;

        //when
        String json = j_son.execJcon(i);

        //then
        Assert.assertEquals(gson.toJson(i), json);
    }

    @Test
    public void shouldTransformPrimitiveChar() throws IllegalAccessException {
        //given
        char c = 'c';

        //when
        String json = j_son.execJcon(c);

        //then
        Assert.assertEquals(gson.toJson(c), json);
    }

    @Test
    public void shouldTransformWrapperChar() throws IllegalAccessException {
        //given
        Character c = 'c';

        //when
        String json = j_son.execJcon(c);

        //then
        Assert.assertEquals(gson.toJson(c), json);
    }

    @Test
    public void shouldTransformPrimitiveDouble() throws IllegalAccessException {
        //given
        double d = 1.05;

        //when
        String json = j_son.execJcon(d);

        //then
        Assert.assertEquals(gson.toJson(d), json);
    }

    @Test
    public void shouldTransformWrapperDouble() throws IllegalAccessException {
        //given
        Double d = 1.05;

        //when
        String json = j_son.execJcon(d);

        //then
        Assert.assertEquals(gson.toJson(d), json);
    }

    @Test
    public void shouldTransformPrimitiveBoolean() throws IllegalAccessException {
        //given
        boolean b = true;

        //when
        String json = j_son.execJcon(b);

        //then
        Assert.assertEquals(gson.toJson(b), json);
    }

    @Test
    public void shouldTransformWrapper() throws IllegalAccessException {
        //given
        String f = "false";

        //when
        String json = j_son.execJcon(Boolean.valueOf(f));

        //then
        Assert.assertEquals(gson.toJson(Boolean.valueOf(f)), json);
    }

    @Test
    public void shouldTransformString() throws IllegalAccessException {
        //given
        String s = "abc";

        //when
        String json = j_son.execJcon(s);

        //then
        Assert.assertEquals(gson.toJson(s), json);
    }

    @Test
    public void shouldTransformArrayOfPrimitives() throws IllegalAccessException {
        //given
        char[] chars = new char[]{'a', 'b', 'c'};

        //when
        String json = j_son.execJcon(chars);

        //then
        Assert.assertEquals(gson.toJson(chars), json);
    }

    @Test
    public void shouldTransformArrayOfWrappers() throws IllegalAccessException {
        //given
        int[] integers = new int[]{1, 2, 3};

        //when
        String json = j_son.execJcon(integers);

        //then
        Assert.assertEquals(gson.toJson(integers), json);
    }

    @Test
    public void shouldTransformCollectionOfStrings() throws IllegalAccessException {
        //given
        List<String> strings = Arrays.asList("abc", "bcd", "cde");

        //when
        String json = j_son.execJcon(strings);

        //then
        Assert.assertEquals(gson.toJson(strings), json);
    }

    @Test
    public void shouldTransformCollectionOfIntegers() throws IllegalAccessException {
        //given
        List<Integer> integers = Arrays.asList(1, 2, 3);

        //when
        String json = j_son.execJcon(integers);

        //then
        Assert.assertEquals(gson.toJson(integers), json);
    }

    @Test
    public void shouldTransformCollectionOfArraysOfChars() throws IllegalAccessException {
        //given
        List<char[]> characters = Arrays.asList(new char[]{'a', 'b', 'c'}, new char[]{'c', 'd', 'e'});

        //when
        String json = j_son.execJcon(characters);

        //then
        Assert.assertEquals(gson.toJson(characters), json);
    }

    @Test
    public void shouldTransformCollectionOfCharacters() throws IllegalAccessException {
        //given
        List<Character> characters = Arrays.asList('a', 'b', 'c');

        //when
        String json = j_son.execJcon(characters);

        //then
        Assert.assertEquals(gson.toJson(characters), json);
    }

    @Test
    public void shouldTransformMap() throws IllegalAccessException {
        //given
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "abc");
        map.put(2, "abcd");
        map.put(3, "abcde");

        //when
        String json = j_son.execJcon(map);

        //then
        Assert.assertEquals(gson.toJson(map), json);
    }

    @Test
    public void shouldTransformVO() throws IllegalAccessException {

        User user1 = new User();
        user1.setAge(12);
        user1.setName("test name 1");
        user1.setDeleted(false);

        User user2 = new User();
        user2.setAge(13);
        user2.setName("test name 2");
        user2.setDeleted(true);

        Group group = new Group();
        group.setId('c');
        group.setActive(true);
        group.setName("test group");
        group.setSomeNumbers(new Integer[]{1, 2, 3, 4});
        group.setUsers(Arrays.asList(user1, user2));

        Assert.assertEquals(JSONValue.parse(new Gson().toJson(group)), JSONValue.parse(j_son.execJcon(group)));
    }

    public static class User {
        private String name;
        private int age;
        private Boolean deleted;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Boolean getDeleted() {
            return deleted;
        }

        public void setDeleted(Boolean deleted) {
            this.deleted = deleted;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age &&
                    Objects.equals(name, user.name) &&
                    Objects.equals(deleted, user.deleted);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name, age, deleted);
        }
    }

    public static class GroupParent {
        private char id;

        public char getId() {
            return id;
        }

        public void setId(char id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GroupParent that = (GroupParent) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {

            return Objects.hash(id);
        }
    }

    public static class Group extends GroupParent {
        private String name;
        private Integer[] someNumbers;
        private List<User> users;
        private transient boolean active;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer[] getSomeNumbers() {
            return someNumbers;
        }

        public void setSomeNumbers(Integer[] someNumbers) {
            this.someNumbers = someNumbers;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Group group = (Group) o;
            return active == group.active &&
                    Objects.equals(name, group.name) &&
                    Arrays.equals(someNumbers, group.someNumbers) &&
                    Objects.equals(users, group.users);
        }

        @Override
        public int hashCode() {

            int result = Objects.hash(super.hashCode(), name, users, active);
            result = 31 * result + Arrays.hashCode(someNumbers);
            return result;
        }
    }
}
