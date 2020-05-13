package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;

public class BuyLandAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        boolean test = test();
        if (test) {
            System.out.println("i will buy");
            entity.getPlayer().payMoney(500);
        }
        return entity;
    }

    public boolean test() {
        Boolean console = ReportUtils.console("will you buy land for 500", Boolean.class);
        return console;
    }
}
