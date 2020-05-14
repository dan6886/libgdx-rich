package com.mygdx.game.handler;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ResultReporter<T> {
    Exchanger<T> exchanger = new Exchanger<>();

    public ResultReporter() {
    }

    public void report(T t) {
        try {
            exchanger.exchange(t);
            System.out.println("good");
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
    public T waitReport2() {
        try {
            return exchanger.exchange(null,5, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}