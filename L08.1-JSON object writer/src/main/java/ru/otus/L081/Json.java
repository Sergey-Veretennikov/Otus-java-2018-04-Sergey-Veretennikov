package ru.otus.L081;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Json {

    public String execJcon(Object o) throws IllegalAccessException {
        if (o == null || isPrimitiveOrWrapperOrString(o.getClass())) {
            if (o != null && o.getClass().equals(Character.class))
                return JSONValue.toJSONString(Character.toString((Character) o));
            return JSONValue.toJSONString(o);
        }
        if (o.getClass().isArray() || o instanceof Iterable) {
            Iterable list = o.getClass().isArray()
                    ? arrayToList(o)
                    : (Iterable) o;
            JSONArray jsonArray = buildJsonArray(list);

            return jsonArray.toJSONString();
        }
        if (o instanceof Map) return JSONObject.toJSONString((Map) o);

        return getMemberObjectValue(o).toJSONString();
    }

    private JSONObject getMemberObjectValue(Object o) throws IllegalAccessException {
        List<Field> fieldArray = new ArrayList<>();
        if (o.getClass().getSuperclass() != null) {
            Field[] f = o.getClass().getSuperclass().getDeclaredFields();
            Collections.addAll(fieldArray, f);
        }
        Field[] fields = o.getClass().getDeclaredFields();
        Collections.addAll(fieldArray, fields);
        JSONObject jsonObject = new JSONObject();

        for (Field field : fieldArray) {
            if (!field.isSynthetic() && !Modifier.isTransient(field.getModifiers())) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = null;

                try {
                    value = field.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (value == null) {
                    jsonObject.put(name, null);
                } else {
                    if (isPrimitiveOrWrapperOrString(field.getType())) {
                        if (value.getClass().equals(Character.class))
                            jsonObject.put(name, Character.toString((Character) value));
                        else
                            jsonObject.put(name, value);
                    } else {
                        if (value.getClass().isArray() || value instanceof Iterable) {
                            Iterable list = value.getClass().isArray()
                                    ? arrayToList(value)
                                    : (Iterable) value;
                            JSONArray jsonArray = buildJsonArray(list);
                            jsonObject.put(name, jsonArray);
                        } else if (value instanceof Map) {
                            jsonObject.put(name, value);
                        } else {
                            JSONObject jsonObj = getMemberObjectValue(value);
                            jsonObject.put(name, jsonObj);
                        }
                    }
                }
            }
        }
        return jsonObject;
    }

    private JSONArray buildJsonArray(Iterable collection) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object item = iterator.next();

            if (item == null || isPrimitiveOrWrapperOrString(item.getClass())) {
                if (item != null && item.getClass().equals(Character.class))
                    jsonArray.add(Character.toString((Character) item));
                else
                    jsonArray.add(item);
            } else if (item.getClass().isArray()) {
                JSONArray jsArr = buildJsonArray(arrayToList(item));
                jsonArray.add(jsArr);
            } else {
                JSONObject j = getMemberObjectValue(item);
                jsonArray.add(j);
            }
        }
        return jsonArray;
    }

    private List arrayToList(Object arr) throws IllegalAccessException {
        if (arr == null) return null;
        if (!arr.getClass().isArray()) throw new ExceptionNotArray("Ошибка массива");

        List list = new ArrayList();
        for (int i = 0; i < Array.getLength(arr); i++) {
            list.add(Array.get(arr, i));
        }
        return list;
    }

    private boolean isPrimitiveOrWrapperOrString(Class type) {
        return type.isPrimitive() ||
                type.equals(String.class) ||
                type.equals(Boolean.class) ||
                type.equals(Integer.class) ||
                type.equals(Character.class) ||
                type.equals(Byte.class) ||
                type.equals(Short.class) ||
                type.equals(Double.class) ||
                type.equals(Long.class) ||
                type.equals(Float.class);
    }
}
