package com.mygdx.game.events;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

import java.util.concurrent.CompletableFuture;

public class StartWalkEvent extends BaseEvent <StartWalkEvent.StartWalkResult>{
    private String name = "";
    private int steps;

    public StartWalkEvent(String name, int steps) {
        super(name);
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
            waitFor(new PassEvent("Pass", p));
            steps--;
        }
        waitFor(new StopWalkEvent(p));
        System.out.println(getName() + "结束");
        complete(getName());
    }

    private ResultReporter<WayPoint> doWalk() {
        ResultReporter<WayPoint> reporter = new ResultReporter<>();
        MainGame.Instance.startWalk(reporter);
        return reporter;
    }

    @Override
    public StartWalkResult getData() {
        return new StartWalkResult();
    }

    public static class StartWalkResult {

    }
}
