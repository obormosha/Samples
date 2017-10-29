package com.javarush.task.task35.task3512;

public class Generator<T> {
    Class<T> t = null;

    public Generator(Class<T> t) {
        this.t = t;
    }

    T newInstance() {
        try {
            return t.newInstance();
        } catch (Exception e) {

        }

        return null;
    }
}
