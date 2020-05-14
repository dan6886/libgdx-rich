package com.mygdx.game.actions;

import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actors.God;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ResultReporter;

public class LossGodHappyAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        Actor1 player = entity.getPlayer();
        God god = player.getGod();
        ResultReporter<Object> reporter = new ResultReporter<>();
        god.deActive(reporter);
        reporter.waitReport();
        MainGame.Instance.showTipsWindow("the damned god finally got away", reporter);
        reporter.waitReport();
        return entity;
    }
}
