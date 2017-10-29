package com.javarush.task.task36.task3602;

import java.lang.reflect.Modifier;
import java.util.Collections;

/*
Найти класс по описанию

java.util.Collections$CheckedList
java.util.Collections$SynchronizedList
java.util.Collections$UnmodifiableList
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        Class[] arr = Collections.class.getDeclaredClasses();
        Class clazz;

        for (Class c : arr) {
            Class[] interfaces = c.getInterfaces();
            for (Class interf : interfaces) {
                if (interf.getSimpleName().equals("RandomAccess")) {
                    int modifiers = c.getModifiers();

                    if (Modifier.isStatic(modifiers)) {
                        if(c.getName().contains("EmptyList")){
                            return c;
                        }
                    }
                }
            }
        }


        return null;
    }
}
