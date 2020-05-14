package com.mygdx.game.actions;


import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;
import com.mygdx.game.handler.ResultReporter;

public class BuildAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        LandPoint landPoint = entity.getPlayer().getCurrent().getLandPoint();

        ConfirmResult confirmResult = confirmBuild(entity);

        if (confirmResult.isOk()) {
            System.out.println("i will build for");
            entity.getPlayer().payMoney(300);

            String type = landPoint.getType();
            if ("small".equals(type)) {
                landPoint.setName("house");
                // 小地
                // 可以升级
                entity.getPlayer().build(landPoint);
                build(entity);
            } else {
                System.out.println("i will not build for");
            }
        }
        return entity;
    }

    public ConfirmResult confirmBuild(BaseHandler.HandlerEntity entity) {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();
        MainGame.Instance.showConfirmWindow("will build for " + entity.getPlayer().getCurrent().getLandPoint().getBuildPrice(), reporter);
        return reporter.waitReport();
    }

    public void build(BaseHandler.HandlerEntity entity) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.buildUIUp(entity.getPlayer(), reporter);
        reporter.waitReport();
    }
}

