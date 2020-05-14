package com.mygdx.game.events;

import com.mygdx.game.actors.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

import java.util.concurrent.CompletableFuture;

public class StopWalkEvent extends BaseEvent<StopWalkEvent.StopWalkResult> {
    private String name = "";
    private WayPoint point;
    private Actor1 player;

    public StopWalkEvent(Actor1 player, String name, WayPoint point) {
        this(player, point);
        this.player = player;
    }

    public StopWalkEvent(Actor1 player, WayPoint point) {
        super("StopWalkEvent");
        this.player = player;
        this.point = point;
    }

    public void happen() {
        this.run();
    }

    public Runnable setFuture(CompletableFuture future) {
        super.setFuture(future);
        return this;
    }

    @Override
    public void run() {
        System.out.println(getName() + "开始" + Thread.currentThread().getId());

        WayPoint p = stopAtWayPoint(point).waitReport();

        waitFor(new HandleLandEvent(player, "handle land", p.getLandPoint()));

        complete(getName());
    }

    /**
     * 处理在路点踩到的东西
     *
     * @param point
     * @return
     */
    public ResultWaiter<WayPoint> stopAtWayPoint(WayPoint point) {
        ResultWaiter<WayPoint> reporter = new ResultWaiter<>();
        // 这里调用出去,这个方法最好是在其他线程里面跑
        MainGame.Instance.stopAt(player, point, reporter);
        return reporter;
    }

    @Override
    public StopWalkResult getData() {
        return new StopWalkResult();
    }

    public static class StopWalkResult {

    }
}
