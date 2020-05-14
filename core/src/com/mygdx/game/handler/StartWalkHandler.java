package com.mygdx.game.handler;

public class StartWalkHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {

        System.out.println("开始了");
//        String dotask = dotask();
        System.out.println("收到回复");
        return chain.process(s);
    }

    public String dotask() {
        ResultReporter<String> reporter = new ResultReporter<>();
        ReportUtils.deley(1, reporter, "开始吧");
        return reporter.waitReport();

    }
}
