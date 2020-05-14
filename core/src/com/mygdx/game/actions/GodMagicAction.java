package com.mygdx.game.actions;


import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Actor1;
import com.mygdx.game.handler.BaseHandler;

public class GodMagicAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {

        Actor1 player = entity.getPlayer();
        if (player.getGod() != null) {
            player.getGod().showMagic();

        }
        return entity;
    }
}
