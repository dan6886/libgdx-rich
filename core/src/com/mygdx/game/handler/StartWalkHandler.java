package com.mygdx.game.handler;

public class StartWalkHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {
        if (s.getPlayer().equals("dan")) {
//            s.setTarget("StopWayHandler");
        }

        System.out.println("开始了");
//        String dotask = dotask();
        System.out.println("收到回复");
        return chain.process(s);
    }

    public String dotask() {
        ResultReporter<String> reporter = new ResultReporter<>();
//        Main.task1(reporter);
        return reporter.waitReport();

    }
}
