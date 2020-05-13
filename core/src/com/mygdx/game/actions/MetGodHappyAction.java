package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;

/**
 * 遇到神灵
 */
public class MetGodHappyAction extends HappyAction {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        System.out.println("thank you for coming");
        return super.doAction(entity);
    }
}
