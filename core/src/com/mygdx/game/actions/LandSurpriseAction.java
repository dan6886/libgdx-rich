package com.mygdx.game.actions;


import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actors.God;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ParcelData;
import com.mygdx.game.handler.ReportUtils;

public class LandSurpriseAction extends BaseAction<BaseHandler.HandlerEntity> {

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        Actor1 player = entity.getPlayer();

        WayPoint current = entity.getPlayer().getCurrent();
        if (current.hasGod()) {
            God god = current.getGod();
            God pre = player.getGod();
            if (pre != null) {
                // 当前有god在身
                if (pre.getType() < 3) {
                    waitAction(new LossGodHappyAction(), entity);
                } else {
                    //todo nothing
                }
            }
            // 路点不再持有god
            current.setGod(null);
            if (god.getType() > 3) {
                waitAction(new MetGodHappyAction(god), entity);
            } else {
                waitAction(new MetGodComplainAction(god), entity);
            }

        }
        return entity;
    }

    public String test1() {
        String god = ReportUtils.console("which god do you want", String.class);
        return god;
    }
}
