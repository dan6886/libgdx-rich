package com.mygdx.game.events;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.*;

public class BaseEvent<T> implements Runnable {
    private String name = "";


    static volatile int count = 40;
    static ExecutorService executors = Executors.newCachedThreadPool();
    private CompletableFuture future = new CompletableFuture();


    public BaseEvent(String name) {
        this.name = name;
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

    public <S> S waitFor(BaseEvent<S> event) {
        CompletableFuture<Void> future = CompletableFuture
                .runAsync(event, executors)
                .whenComplete((s, throwable) -> System.out.println(event.name + "任务结束"));

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Class<S> childClass = getModelName(event);
        return childClass.cast(event.getData());
    }

    public T getData() {
        return null;
    }

    /**
     * 获取实体类型名称
     * 子类可覆盖此方法，返回：泛型T的类名
     *
     * @return
     */
    protected <K> Class<K> getModelName(Object obj) {
        Type t = obj.getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t;
        return (Class<K>) p.getActualTypeArguments()[0];
    }

    @Override
    public void run() {

    }

    public void complete(Object value) {
        future.complete(value);
    }

    public static class ResultReporter<T> {
        Exchanger<T> exchanger = new Exchanger<>();

        public ResultReporter() {
        }

        public void report(T t) {
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
