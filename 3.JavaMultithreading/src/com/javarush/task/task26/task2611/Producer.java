package com.javarush.task.task26.task2611;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by User on 13.04.2017.
 */
public class Producer implements Runnable {
    private ConcurrentHashMap<String, String> map;

    public Producer(ConcurrentHashMap<String, String> map) {
        this.map = map;
    }

    int count = 1;

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (!currentThread.isInterrupted()) {
            map.put(String.valueOf(count), "Some text for " + count);
            count++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(currentThread.getName() + " thread was terminated");
            }
        }
    }
}
