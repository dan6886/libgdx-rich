package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;

public class ComplainAction extends BaseAction<BaseHandler.HandlerEntity> {
    private int lossMoney = 0;

    public ComplainAction() {
    }

    public ComplainAction(int money) {

    }

    @Override
    public void justAction() {

    }

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        return entity;
    }
}
