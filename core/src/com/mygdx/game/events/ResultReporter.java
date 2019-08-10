package com.mygdx.game.events;

import io.reactivex.subjects.PublishSubject;

public class ResultReporter<T> {
    private PublishSubject<T> subject;


    public ResultReporter() {
        subject = PublishSubject.create();
    }

    public void finish(T t) {
        subject.onNext(t);
        subject.onComplete();
    }

}
