package com.mygdx.game.events;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

public class PassEvent extends BaseEvent {
    private WayPoint point;

    public PassEvent(String name, WayPoint point) {
        super(name);
        this.point = point;
    }

    @Override
    public void run() {
        checkPass().waitReport();
    }

    public ResultReporter<Object> checkPass() {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.passWayPoint(point, reporter);
        return reporter;
    }
}
