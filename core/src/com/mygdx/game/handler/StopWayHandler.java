package com.mygdx.game.handler;

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
            s.setParcelData(new ParcelData.Builder()
                    .setPrevHandlerName(this.getClass().getSimpleName())
                    .build());
        } else {
            if (landPoint.isBlank()) {
                s.setParcelData(new ParcelData.Builder()
                        .setPrevHandlerName(this.getClass().getSimpleName())
                        .setTargetHandlerName(BuyLandHandler.class.getSimpleName())
                        .build());
            } else {
                if (s.getPlayer().getName().equals(landPoint.getOwnerName())) {
                    s.setParcelData(new ParcelData.Builder()
                            .setPrevHandlerName(this.getClass().getSimpleName())
                            .setTargetHandlerName(BuildLandHandler.class.getSimpleName())
                            .build());
                } else {
                    s.setParcelData(new ParcelData.Builder()
                            .setPrevHandlerName(this.getClass().getSimpleName())
                            .setTargetHandlerName(PayLandHandler.class.getSimpleName())
                            .build());
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
