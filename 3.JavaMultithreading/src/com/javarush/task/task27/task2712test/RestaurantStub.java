package com.javarush.task.task27.task2712test;

import com.javarush.task.task27.task2712.DirectorTablet;
import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.kitchen.Dish;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RestaurantStub {
    public static void main(String[] args) {
        List<Dish> dishes1 = new ArrayList<Dish>();
        dishes1.add(Dish.Fish);
        CookedOrderEventDataRow eventDataRow1 = new CookedOrderEventDataRow("1", "Ivanov", 25 * 60, dishes1);
        StatisticManager.getInstance().register(eventDataRow1);

        fire("1", "Ivanov", 15 * 60, 1, Dish.Soup, Dish.Juice, Dish.Water);
        fire("2", "Avanov", 30 * 60, 1, Dish.Fish, Dish.Juice, Dish.Water);
        fire("2", "Zueff", 60*60, 1, Dish.Soup, Dish.Steak, Dish.Juice);


        List<Dish> dishes3 = new ArrayList<Dish>();
        dishes3.add(Dish.Water);
        CookedOrderEventDataRow eventDataRow3 = new CookedOrderEventDataRow("1", "Petrov", 100*60, dishes3);
        StatisticManager.getInstance().register(eventDataRow3);

        //test printAdvertisementProfit()

        VideoSelectedEventDataRow v1 = new VideoSelectedEventDataRow(new ArrayList<Advertisement>(), 210, 10*60);
        StatisticManager.getInstance().register(v1);
        VideoSelectedEventDataRow v2 = new VideoSelectedEventDataRow(new ArrayList<Advertisement>(), 315, 15*60);
        StatisticManager.getInstance().register(v2);

        VideoSelectedEventDataRow v3 = new VideoSelectedEventDataRow(new ArrayList<Advertisement>(), 1040, 30*60);
        StatisticManager.getInstance().register(v3);
        v3.getDate().setTime(new Date().getTime() - 24 * 60 * 60 * 1000);
        VideoSelectedEventDataRow v4 = new VideoSelectedEventDataRow(new ArrayList<Advertisement>(), 20, 2*60);
        StatisticManager.getInstance().register(v4);
        v4.getDate().setTime(new Date().getTime() - 24 * 60 * 60 * 1000);

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();

    }

    private static void fire(String tableName, String cook, int cookingTime, int daysShift, Dish... dishes) {
        CookedOrderEventDataRow eventDataRow = new CookedOrderEventDataRow(tableName, cook, cookingTime, Arrays.asList(dishes));
        eventDataRow.getDate().setTime(new Date().getTime() - 24 * 60 * 60 * 1000 * daysShift);
        StatisticManager.getInstance().register(eventDataRow);
    }
}
