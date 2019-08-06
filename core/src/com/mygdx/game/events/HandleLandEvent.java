package com.mygdx.game.events;

import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class HandleLandEvent extends BaseEvent<HandleLandEvent.HandleLandResult> {
    private String name = "";
    private LandPoint point;

    public HandleLandEvent(String name, LandPoint point) {
        super(name);
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
        consumeOrinvest(point).waitReport();
        System.out.println(getName() + "结束");
        complete(getName());
    }

    public ResultReporter<LandPoint> consumeOrinvest(LandPoint point) {
        ResultReporter<LandPoint> reporter = new ResultReporter<>();
        // 这里调用出去,这个方法最好是在其他线程里面跑
        MainGame.Instance.consumeOrinvest(point, reporter);
        return reporter;
    }

    @Override
    public HandleLandResult getData() {
        return new HandleLandResult();
    }

    public static class HandleLandResult {

    }
}
