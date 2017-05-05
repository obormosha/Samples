package com.javarush.task.task28.task2805;

/**
 * Created by Operator on 04.05.2017.
 */
public class MyThread extends Thread {
    private static int priority = 0;

    public MyThread() {
        if (this.getThreadGroup() != null) {
            if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(priority);
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }

    }

    public MyThread(Runnable target) {
        super(target);
        if (this.getThreadGroup() != null) {
            if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(priority);
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(ThreadGroup group, Runnable target) {
        super(group, target);
        if (this.getThreadGroup() != null) {
            if (priority < this.getThreadGroup().getMaxPriority() && priority < MAX_PRIORITY) {
                this.setPriority(++priority);
            } else if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(this.getThreadGroup().getMaxPriority());
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(String name) {
        super(name);
        if (this.getThreadGroup() != null) {
            if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(priority);
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
        if (this.getThreadGroup() != null) {
            if (priority < this.getThreadGroup().getMaxPriority() && priority < MAX_PRIORITY) {
                this.setPriority(++priority);
            } else if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(this.getThreadGroup().getMaxPriority());
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
        if (this.getThreadGroup() != null) {
            if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(priority);
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        if (this.getThreadGroup() != null) {
            if (priority < this.getThreadGroup().getMaxPriority() && priority < MAX_PRIORITY) {
                this.setPriority(++priority);
            } else if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(this.getThreadGroup().getMaxPriority());
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }

    public MyThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        if (this.getThreadGroup() != null) {
            if (priority < this.getThreadGroup().getMaxPriority() && priority < MAX_PRIORITY) {
                this.setPriority(++priority);
            } else if(priority < MAX_PRIORITY){
                priority = ++priority;
                this.setPriority(this.getThreadGroup().getMaxPriority());
            } else{
                priority = 1;
                this.setPriority(MIN_PRIORITY);
            }
        }
    }


}
