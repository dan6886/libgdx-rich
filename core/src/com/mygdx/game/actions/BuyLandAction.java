package com.mygdx.game.actions;


import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actors.God;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ResultReporter;

public class BuyLandAction extends BaseAction<BaseHandler.HandlerEntity> {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        Actor1 player = entity.getPlayer();
        ConfirmResult confirmResult = confirmBuy();
        if (confirmResult.isOk()) {
            System.out.println("i will buy");
            if (player.hasGod(God.GOD_UNLUCKY)) {
                buyLandFailByGod();
            } else {
                buyLand(entity);
                entity.getPlayer().buy(entity.getPlayer().getCurrent().getLandPoint());
            }
        }
        return entity;
    }

    public ConfirmResult confirmBuy() {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();

        MainGame.Instance.showConfirmWindow("你是否愿意支付来购买此地", reporter);
        return reporter.waitReport();
    }

    private void buyLand(BaseHandler.HandlerEntity entity) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.ownLand(entity.getPlayer(), reporter);
        reporter.waitReport();
    }

    private void buyLandFailByGod() {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.showTipsWindow("Buy land failed because unlucky god", reporter);
        reporter.waitReport();
    }
}
