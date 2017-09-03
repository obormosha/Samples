package com.javarush.task.task27.task2712test;

public class OrderManager {

    /*
    //private static final LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public OrderManager() {
        Thread threadDaemon = new Thread() {
            Set<Cook> cooks = StatisticManager.getInstance().getCooks();

            @Override
            public void run() {
                while (true) {
                    try {
                        for (final Cook cook : cooks) {
                            if (!cook.isBusy() && !queue.isEmpty()) {
                                final Order order = queue.poll();
                                cook.startCookingOrder(order);
                            }
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        //break;
                    }
                }
            }
        };

        threadDaemon.setDaemon(true);
        threadDaemon.start();
    }


    @Override
    public void update(Observable o, Object arg) {
        queue.offer((Order) arg);
    }*/
}
