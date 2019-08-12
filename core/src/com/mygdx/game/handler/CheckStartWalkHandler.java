package com.mygdx.game.handler;

import com.mygdx.game.MainGame;

public class CheckStartWalkHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {
        if (s.getPlayer().getState() == 0) {
            showSkip("i am sleep");
            return s;
        }
        Integer count = waitClickStart();
        s.setMoveCount(count);
        return chain.process(s);
    }

    public Object showSkip(String text) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.showTipsWindow(text, reporter);
        return reporter.waitReport();
    }

    public Integer waitClickStart() {
        ResultReporter<Integer> reporter = new ResultReporter<>();
        MainGame.Instance.showStartButton(reporter);
        System.out.println(reporter.toString()+"等待线程名称:" + Thread.currentThread().getName());
        return reporter.waitReport();
    }
}
