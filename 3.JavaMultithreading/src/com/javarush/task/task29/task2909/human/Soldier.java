package com.javarush.task.task29.task2909.human;

/**
 * Created by Пользователь on 31.03.2017.
 */
public class Soldier extends Human {

    public void live() {
        fight();
    }

    public void fight() {
    }

    public Soldier(String name, int age) {
        super(name, age);
    }
}
