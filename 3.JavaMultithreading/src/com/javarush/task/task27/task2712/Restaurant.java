package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        List<Tablet> tablets = new ArrayList<>();

        Cook cook = new Cook("Amigo");
        cook.setQueue(orderQueue);

        Cook cook2 = new Cook("Cook");
        cook2.setQueue(orderQueue);

        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook2.addObserver(waiter);

        //StatisticManager.getInstance().register(cook);
        //StatisticManager.getInstance().register(cook2);

        //OrderManager orderManager = new OrderManager();

        for (int i = 1; i < 6; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread thread = new Thread(cook);
        thread.start();
        Thread thread2 = new Thread(cook2);
        thread2.start();

        RandomOrderGeneratorTask tasks = new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL);
        Thread thread3 = new Thread(tasks);
        thread3.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
        }

        thread3.interrupt();

        while (!orderQueue.isEmpty()) {
            Thread.sleep(1);
        }

        while ((cook.isBusy()) || (cook2.isBusy())) {
            Thread.sleep(1);
        }

        thread.interrupt();
        thread2.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
