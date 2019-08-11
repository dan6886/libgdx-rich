package com.mygdx.game.handler;

import com.mygdx.game.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.entity.LandPoint;

public class BuildLandHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {

        LandPoint landPoint = s.getPlayer().getCurrent().getLandPoint();
        if (landPoint.isCanLevelUp()) {
            ConfirmResult confirmResult = buildLandConfirm();
            if (confirmResult.isOk()) {
                int level = landPoint.getLevel();
                String type = landPoint.getType();
                if ("small".equals(type)) {
                    landPoint.setName("house");
                    // 小地
                    // 可以升级
                    Object o = buildUp(s.getPlayer());
                } else if ("big".equals(type)) {
                    System.out.println("大地");
                }
            } else {
                System.out.println("不愿意升级");
            }
        } else {
            System.out.println("不能升级");
        }
        return chain.process(s);
    }

    ResultReporter<ConfirmResult> reporter = new ResultReporter<>();

    public ConfirmResult buildLandConfirm() {
        MainGame.Instance.showConfirmWindow("will you want to build up this land?", reporter);
        return reporter.waitReport();
    }

    private Object buildUp(Actor1 actor1) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.buildUp(actor1, reporter);
        return reporter.waitReport();
    }
}
