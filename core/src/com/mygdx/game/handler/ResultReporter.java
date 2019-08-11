package com.mygdx.game.handler;

import java.util.concurrent.Exchanger;

public class ResultReporter<T> {
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