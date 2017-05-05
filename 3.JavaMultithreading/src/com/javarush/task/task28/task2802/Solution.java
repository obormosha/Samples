package com.javarush.task.task28.task2802;


import java.util.HashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Пишем свою ThreadFactory
*/
public class Solution {
    public static class AmigoThreadFactory implements ThreadFactory {
        public static AtomicInteger countFactories = new AtomicInteger(0);
        public AtomicInteger countThreads = new AtomicInteger(0);
        public static HashMap<String, Integer> factories = new HashMap<>();


        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            if (!factories.containsKey(thread.getThreadGroup().getName())) {
                factories.put(thread.getThreadGroup().getName(), countFactories.incrementAndGet());
            }

            countThreads.incrementAndGet();
            thread.setName(thread.getThreadGroup().getName() + "-pool-" + factories.get(thread.getThreadGroup().getName()) + "-thread-" + countThreads);

            return thread;
        }
    }

    public static void main(String[] args) {
        class EmulateThreadFactoryTask implements Runnable {
            @Override
            public void run() {
                emulateThreadFactory();
            }
        }

        ThreadGroup group = new ThreadGroup("firstGroup");
        Thread thread = new Thread(group, new EmulateThreadFactoryTask());

        ThreadGroup group2 = new ThreadGroup("secondGroup");
        Thread thread2 = new Thread(group2, new EmulateThreadFactoryTask());

        thread.start();
        thread2.start();
    }

    private static void emulateThreadFactory() {
        AmigoThreadFactory factory = new AmigoThreadFactory();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        factory.newThread(r).start();
        factory.newThread(r).start();
        factory.newThread(r).start();
    }


}
