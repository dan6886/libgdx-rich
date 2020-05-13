package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;

public class BuildAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        boolean test = test();
        if (test) {
            System.out.println("i will build for");
            entity.getPlayer().payMoney(300);
        } else {
            System.out.println("i will not build for");
        }
        return entity;
    }

    public boolean test() {
        Boolean console = ReportUtils.console("will build for 300", Boolean.class);
        return console;
    }
}
