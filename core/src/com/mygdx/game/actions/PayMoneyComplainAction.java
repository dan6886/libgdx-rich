package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;

public class PayMoneyComplainAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        System.out.println("oh!!! i pay to much money");
        return entity;
    }

}
