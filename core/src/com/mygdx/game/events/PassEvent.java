package com.mygdx.game.events;

import com.mygdx.game.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

public class PassEvent extends BaseEvent<PassEvent.PassResult> {
    private WayPoint point;
private Actor1 player;
    public PassEvent(Actor1 player,String name, WayPoint point) {
        super(name);
        this.player = player;
        this.point = point;
    }

    @Override
    public void run() {
        checkPass().waitReport();
    }

    public ResultWaiter<Object> checkPass() {
        ResultWaiter<Object> reporter = new ResultWaiter<>();
        MainGame.Instance.passWayPoint(player,point, reporter);
        return reporter;
    }

    @Override
    public PassResult getData() {
        return new PassResult();
    }

    public static class PassResult {

    }
}
