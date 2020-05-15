package com.mygdx.game.handler;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainGame;
import com.mygdx.game.ui.GameStage;

public class CheckStartWalkHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {

        s.getPlayer().newday();
        Integer count = waitClickStart();
        s.setMoveCount(count);
        GameStage parent = (GameStage) (s.getPlayer().getStage());
        parent.setState(GameStage.STATE_RUN);
        return chain.process(s);
    }

    public Integer waitClickStart() {
        ResultReporter<Integer> reporter = new ResultReporter<>();
        MainGame.Instance.showStartButton(reporter);
        System.out.println(reporter.toString() + "等待线程名称:" + Thread.currentThread().getName());
        return reporter.waitReport();
    }
}
