package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;

public class MetGodComplainAction extends ComplainAction {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        System.out.println("get away!!!");
        return super.doAction(entity);
    }

}
