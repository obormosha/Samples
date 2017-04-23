package com.javarush.task.task25.task2502;

import java.util.ArrayList;
import java.util.List;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() {
            //init wheels here
            wheels = new ArrayList<>();
            String[] namesWheel = loadWheelNamesFromDB();
            if (namesWheel.length == Wheel.values().length) {
                try {
                    for (String s : namesWheel) {
                        wheels.add(Wheel.valueOf(s));
                    }
                } catch (IllegalArgumentException e) {
                    throw e;
                }
            } else{
                throw new IllegalArgumentException();
            }
        }


        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car.wheels);
    }
}
