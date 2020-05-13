package com.mygdx.game.handler;

public class PassHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {
        int moveCount = s.getMoveCount();

        while (moveCount > 0) {
            doTask2(s);
            System.out.println("移动：" + moveCount);
            // 移动完毕的判断，路障或者银行
            moveCount--;
        }
        return chain.process(s);
    }

    private String doTask2(HandlerEntity entity) {
        ResultReporter<String> reporter = new ResultReporter<>();
        ReportUtils.deley(1, reporter, "walk");
        return reporter.waitReport();
    }

}
