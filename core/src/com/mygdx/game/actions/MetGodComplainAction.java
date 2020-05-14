package com.mygdx.game.actions;


import com.mygdx.game.MainGame;
import com.mygdx.game.actors.God;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ResultReporter;

public class MetGodComplainAction extends ComplainAction {
    God god;

    public MetGodComplainAction(God god) {
        this.god = god;
    }

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        god.setMaster(entity.getPlayer());
        god.active(reporter);
        reporter.waitReport();

        ResultReporter<Object> reporter1 = new ResultReporter<>();
        MainGame.Instance.showTipsWindow("i am so unlucky!!!", reporter1);
        reporter1.waitReport();

        return super.doAction(entity);
    }

}
