package com.javarush.task.task25.task2506;

/**
 * Created by Пользователь on 06.04.2017.
 */
public class LoggingStateThread extends Thread {
    Thread thread;

    public LoggingStateThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        Thread.State currentState = thread.getState();
        System.out.println(currentState);
        super.run();
        while (!currentState.equals(State.TERMINATED)) {
            Thread.State newState = thread.getState();
            if (!newState.equals(currentState)) {
                System.out.println(newState);
                currentState = newState;
            }
        }
        this.interrupt();
    }
}
