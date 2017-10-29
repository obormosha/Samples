package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <Key, T extends Convertable<Key>> Map convert(List<T> list) {
        Map<Key, T> result = new HashMap<>();

        for (T t : list) {
            result.put(t.getKey(), t);
        }

        return result;
    }
}
