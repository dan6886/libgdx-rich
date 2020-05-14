package com.mygdx.game.handler;


import com.mygdx.game.actions.BuildAction;
import com.mygdx.game.actions.BuyLandAction;
import com.mygdx.game.actions.GodMagicAction;
import com.mygdx.game.actions.LandSurpriseAction;
import com.mygdx.game.actions.PayLandAction;
import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actors.God;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;

/**
 * 停下来之后
 * 检测路点有没有有意外收获
 * 检测神灵是否需要加成
 * 检测是否付钱，加盖，购买，进入npc路点
 */
public class StopWayHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {
        s = waitAction(new LandSurpriseAction(), s);
        Actor1 player = s.getPlayer();
        LandPoint landPoint = player.getCurrent().getLandPoint();
        System.out.println(s.getPlayer().getCurrent().getLandPoint().toString());
        if (!landPoint.isNothing()) {
            if (landPoint.isBlank()) {
                //询问购买
                if (!player.hasGod(God.GOD_LAND)) {
                    //如果有土地，还买啥
                    waitAction(new BuyLandAction(), s);
                }
            } else if (s.getPlayer().getName().equals(landPoint.getOwnerName())) {
                if (landPoint.isCanLevelUp()) {
                    waitAction(new BuildAction(), s);
                }
            } else if (!s.getPlayer().getName().equals(landPoint.getOwnerName())) {
                waitAction(new PayLandAction(), s);
            }
        }

        waitAction(new GodMagicAction(), s);
        return chain.process(s);
    }

}
