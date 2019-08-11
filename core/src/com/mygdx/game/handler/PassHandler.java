package com.mygdx.game.handler;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

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

    private WayPoint doTask2(HandlerEntity entity) {
        ResultReporter<WayPoint> reporter = new ResultReporter<>();
        MainGame.Instance.startWalk(entity.getPlayer(), reporter);
        return reporter.waitReport();
    }

}
