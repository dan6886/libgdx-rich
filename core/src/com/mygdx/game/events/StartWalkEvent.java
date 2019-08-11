package com.mygdx.game.events;

import com.mygdx.game.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

import java.util.concurrent.CompletableFuture;

public class StartWalkEvent extends BaseEvent<StartWalkEvent.StartWalkResult> {
    private String name = "";
    private int steps;
    private Actor1 player;

    public StartWalkEvent(Actor1 player, String name, int steps) {
        super(name);
        this.player = player;
        this.steps = steps;
    }

    public Runnable setFuture(CompletableFuture future) {
        super.setFuture(future);
        return this;
    }

    @Override
    public void run() {
        System.out.println(getName() + "开始");
        WayPoint p = null;
        while (steps > 0) {
            p = doWalk().waitReport();
            waitFor(new PassEvent(player, "Pass", p));
            steps--;
        }
        waitFor(new StopWalkEvent(player,p));
        System.out.println(getName() + "结束");
        complete(getName());
    }

    private ResultWaiter<WayPoint> doWalk() {
        ResultWaiter<WayPoint> reporter = new ResultWaiter<>();
//        MainGame.Instance.startWalk(player, reporter);
        return reporter;
    }

    @Override
    public StartWalkResult getData() {
        return new StartWalkResult();
    }

    public static class StartWalkResult {

    }
}
