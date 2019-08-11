package com.mygdx.game.handler;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] arg) {
        HandlerChain handlerChain = new HandlerChain();
        // 开始行走
        handlerChain.addHandler(new StartWalkHandler());
        // 路过
        handlerChain.addHandler(new PassHandler());
        // 停下 猜到路点
        handlerChain.addHandler(new StopWayHandler());
//        // 结算土地,这里有一个参数传递下去，判断下来的逻辑是谁处理
//        handlerChain.addHandler(new StopLandHandler());
//        // 购买空地（话语）
//        handlerChain.addHandler(new StopLandHandler());
//        // 升级空地（话语）
//        handlerChain.addHandler(new StopLandHandler());
//        // 付过路费 (话语)
//        handlerChain.addHandler(new StopLandHandler());
//        // 结束(一些神明的处理)
//        handlerChain.addHandler(new StopLandHandler());
        BaseHandler.HandlerEntity entity = new BaseHandler.HandlerEntity();

        entity.setMoveCount(5);
        System.out.println(handlerChain.process(entity));
    }

}
