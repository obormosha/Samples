package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager implements Observer {
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public OrderManager() {
        Thread threadDaemon = new Thread() {
            @Override
            public void run() {
                Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                while (true) {
                    try {
                        if (!orderQueue.isEmpty()) {
                            for (final Cook cook : cooks) {
                                if (!cook.isBusy()) {
                                    final Order order = orderQueue.take();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            cook.startCookingOrder(order);
                                        }
                                    }.start();
                                }
                            }
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };

        threadDaemon.setDaemon(true);
        threadDaemon.start();
    }


    @Override
    public void update(Observable o, Object arg) {
        orderQueue.add((Order) arg);
    }
}
