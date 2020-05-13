package com.mygdx.game.handler;


public class CheckStartWalkHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {
        Integer count = waitClickStart();
        s.setMoveCount(count);
        return chain.process(s);
    }

    public Integer waitClickStart() {
        ResultReporter<Integer> reporter = new ResultReporter<>();
        ReportUtils.deley(1, reporter, 3);
        System.out.println(reporter.toString() + "等待线程名称:" + Thread.currentThread().getName());
        return reporter.waitReport();
    }
}
