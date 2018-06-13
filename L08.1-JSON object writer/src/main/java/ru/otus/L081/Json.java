package ru.otus.L081;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Json {

    public String execJcon(Object o) throws IllegalAccessException {
        if (o == null || isPrimitiveOrWrapperOrString(o.getClass())) return JSONValue.toJSONString(o);
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
        Field[] fields = o.getClass().getDeclaredFields();
        JSONObject jsonObject = new JSONObject();

        for (Field field : fields) {
            if (!field.isSynthetic()) {
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
                    if (isPrimitiveOrWrapperOrString(field.getType()))
                        jsonObject.put(name, value);
                    else {
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

            if (item == null || isPrimitiveOrWrapperOrString(item.getClass()))
                jsonArray.add(item);
            else {
                JSONObject j = getMemberObjectValue(item);
                jsonArray.add(j);
            }
        }
        return jsonArray;
    }

    private List arrayToList(Object arr) throws IllegalAccessException {
        if (arr == null) return null;
        if (!arr.getClass().isArray()) throw new IllegalAccessException("Ошибка массива");

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
