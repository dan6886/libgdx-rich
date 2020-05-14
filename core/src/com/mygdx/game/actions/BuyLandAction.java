package com.mygdx.game.actions;


import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;
import com.mygdx.game.handler.ResultReporter;

public class BuyLandAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        ConfirmResult confirmResult = confirmBuy();
        if (confirmResult.isOk()) {
            System.out.println("i will buy");
            buyLand(entity);
            entity.getPlayer().payMoney(500);
        }
        return entity;
    }

    public ConfirmResult confirmBuy() {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();
        MainGame.Instance.showConfirmWindow("will you buy land for 500", reporter);
        return reporter.waitReport();
    }

    private void buyLand(BaseHandler.HandlerEntity entity) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.buyLand(entity.getPlayer(), reporter);
        reporter.waitReport();
    }
}
