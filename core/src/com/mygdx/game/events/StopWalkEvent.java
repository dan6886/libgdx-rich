package com.mygdx.game.events;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.WayPoint;

import java.util.concurrent.CompletableFuture;

public class StopWalkEvent extends BaseEvent {
    private String name = "";
    private WayPoint point;

    public StopWalkEvent(String name, WayPoint point) {
        this(point);
    }

    public StopWalkEvent(WayPoint point) {
        super("StopWalkEvent");
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

        waitFor(new HandleLandEvent("handle land", p.getLandPoint()));

        complete(getName());
    }

    /**
     * 处理在路点踩到的东西
     *
     * @param point
     * @return
     */
    public ResultReporter<WayPoint> stopAtWayPoint(WayPoint point) {
        ResultReporter<WayPoint> reporter = new ResultReporter<>();
        // 这里调用出去,这个方法最好是在其他线程里面跑
        MainGame.Instance.stopAt(point, reporter);
        return reporter;
    }
}
