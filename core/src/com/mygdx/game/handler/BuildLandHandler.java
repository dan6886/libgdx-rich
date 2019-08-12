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
                String type = landPoint.getType();
                if ("small".equals(type)) {
                    landPoint.setName("house");
                    // 小地
                    // 可以升级
                    s.getPlayer().build(s.getPlayer().getCurrent().getLandPoint());
                    Object o = buildUIUp(s.getPlayer());
                    showTipsWindow("welcome to my land");
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


    public ConfirmResult buildLandConfirm() {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();
        MainGame.Instance.showConfirmWindow("will you want to build up this land?", reporter);
        return reporter.waitReport();
    }

    private Object buildUIUp(Actor1 actor1) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.buildUIUp(actor1, reporter);
        return reporter.waitReport();
    }

    private void showTipsWindow(String text) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.showTipsWindow(text, reporter);
        reporter.waitReport();
    }
}
