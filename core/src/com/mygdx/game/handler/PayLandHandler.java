package com.mygdx.game.handler;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.events.BaseEvent;

public class PayLandHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {
        System.out.println("处理路点相关的事物 StopWayHandler");
        return chain.process(s);
    }

}
