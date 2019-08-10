package com.mygdx.game.events;

import com.mygdx.game.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;

import java.util.concurrent.CompletableFuture;

public class HandleLandEvent extends BaseEvent<HandleLandEvent.HandleLandResult> {
    private String name = "";
    private LandPoint point;
    private Actor1 player;
    public HandleLandEvent(Actor1 player, String name, LandPoint point) {
        super(name);
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
        System.out.println(getName() + "开始" + Thread.currentThread().getName());
        consumeOrinvest(point).waitReport();
        System.out.println(getName() + "结束");
        complete(getName());
    }

    public ResultWaiter<LandPoint> consumeOrinvest(LandPoint point) {
        ResultWaiter<LandPoint> reporter = new ResultWaiter<>();
        // 这里调用出去,这个方法最好是在其他线程里面跑
        MainGame.Instance.consumeOrInvest(player,point, reporter);
        return reporter;
    }

    @Override
    public HandleLandResult getData() {
        return new HandleLandResult();
    }

    public static class HandleLandResult {

    }
}
