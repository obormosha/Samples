package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration; // в минутах

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        String allDishes = "";
        for (int i = 0; i < Dish.values().length; i++) {
            allDishes = allDishes.concat(Dish.values()[i].toString());
            if (i != Dish.values().length - 1) {
                allDishes = allDishes.concat(", ");
            }
        }
        return allDishes;
    }
}
