package com.mygdx.game.events;

import java.util.concurrent.*;

public class BaseEvent implements Runnable {
    private String name = "";


    static volatile int count = 40;
    static ExecutorService executors = Executors.newCachedThreadPool();
    private CompletableFuture future = new CompletableFuture();


    public BaseEvent(String name) {
        this.name = name;
        this.future = future;
    }

    public void happen() {
        executors.submit(this);
    }

    public Runnable setFuture(CompletableFuture future) {
        this.future = future;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompletableFuture getFuture() {
        return future;
    }

    public void waitFor(BaseEvent cxEvent) {
        CompletableFuture<Void> future = CompletableFuture
                .runAsync(cxEvent, executors)
                .whenComplete((s, throwable) -> System.out.println(cxEvent.name + "任务结束"));


        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void waitSelf() {
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void continueSelf() {

    }

    @Override
    public void run() {

    }

    public void complete(Object value) {
        future.complete(value);
    }

    public static class ResultReporter<T> {
        Exchanger<T> exchanger = new Exchanger<>();

        private boolean hasReport = false;

        public ResultReporter() {
        }

        public void report(T t) {
            synchronized (this) {
                if (hasReport) {
                    return;
                }

                hasReport = true;
            }
            try {
                exchanger.exchange(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public T waitReport() {
            try {
                return exchanger.exchange(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
