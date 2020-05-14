package com.mygdx.game.actions;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actors.God;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ResultReporter;

/**
 * 遇到神灵
 */
public class MetGodHappyAction extends HappyAction {
    private God god;

    public MetGodHappyAction(God god) {
        this.god = god;
    }

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        System.out.println("thank you for coming");
        ResultReporter<Object> reporter = new ResultReporter<>();
        god.setMaster(entity.getPlayer());
        god.active(reporter);
        reporter.waitReport();
        return super.doAction(entity);
    }
}
