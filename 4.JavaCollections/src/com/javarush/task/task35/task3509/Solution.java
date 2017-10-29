package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        //напишите тут ваш код
        ArrayList<T> arr = new ArrayList<T>();
        if (elements != null && elements.length != 0) {
            for (T t : elements) {
                arr.add(t);
            }
        }

        return arr;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        //напишите тут ваш код
        HashSet<T> tHashSet = new HashSet<T>();
        if (elements != null && elements.length != 0) {
            for (T t : elements) {
                tHashSet.add(t);
            }
        }
        return tHashSet;
    }

    public static <K, V> HashMap<K, V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        //напишите тут ваш код
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException();
        }
        HashMap<K, V> kvHashMap = new HashMap<K, V>();


        if (keys != null && keys.size() != 0 && values != null && values.size() != 0) {
            for(int i = 0; i < keys.size(); i++){
                kvHashMap.put(keys.get(i), values.get(i));
            }
        }

        return kvHashMap;
    }
}
