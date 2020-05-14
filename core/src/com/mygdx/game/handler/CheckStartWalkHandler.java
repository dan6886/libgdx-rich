package com.mygdx.game.handler;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.MainGame;

public class CheckStartWalkHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {
        Integer count = waitClickStart();
        s.setMoveCount(count);
        return chain.process(s);
    }

    public Integer waitClickStart() {
        ResultReporter<Integer> reporter = new ResultReporter<>();
        MainGame.Instance.showStartButton(reporter);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("why????");
                Gdx.app.debug("button","why???");
            }
        });
                Gdx.app.debug("button","waitClickStart");
        System.out.println(reporter.toString() + "等待线程名称:" + Thread.currentThread().getName());
        return reporter.waitReport();
    }
}
