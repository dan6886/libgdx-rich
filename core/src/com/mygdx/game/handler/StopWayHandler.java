package com.mygdx.game.handler;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.events.BaseEvent;

public class StopWayHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {
        WayPoint current = s.getPlayer().getCurrent();

// 路点的事物

//然后处理接下来要对土地的操作
        LandPoint landPoint = current.getLandPoint();
        if (landPoint.isNothing()) {
            //该路点对应不是土地模块
            s.setTarget(STOP_WAY_HANDLER);
        } else {
            if (landPoint.isBlank()) {
                s.setTarget(BUY_LAND_HANDLER);
            } else {
                if (s.getPlayer().getName().equals(landPoint.getOwnerName())) {
                    s.setTarget(BUILD_LAND_HANDLER);
                } else {
                    s.setTarget(PAY_LAND_HANDLER);
                }
            }
        }
        return chain.process(s);
    }

    /**
     * 处理在路点踩到的东西
     *
     * @param point
     * @return
     */
    public BaseEvent.ResultWaiter<WayPoint> stopAtWayPoint(WayPoint point) {

        return null;
    }
}
