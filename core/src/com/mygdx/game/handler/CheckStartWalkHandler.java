package com.mygdx.game.handler;


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
        System.out.println(reporter.toString() + "等待线程名称:" + Thread.currentThread().getName());
        return reporter.waitReport();
    }
}
