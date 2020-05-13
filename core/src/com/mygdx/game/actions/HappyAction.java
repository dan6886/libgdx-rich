package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;

public class HappyAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        return entity;
    }
}
