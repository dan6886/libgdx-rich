package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;

public class WillNotPayMoneyHappyAction extends HappyAction {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {

        System.out.println("i will not pay for you robber");

        return super.doAction(entity);
    }
}
