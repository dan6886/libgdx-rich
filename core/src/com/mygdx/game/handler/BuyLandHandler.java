package com.mygdx.game.handler;

import com.mygdx.game.actors.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.entity.LandPoint;

public class BuyLandHandler extends BaseHandler {
    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {

        ConfirmResult confirmResult = buyLandConfirm();
        if (confirmResult.isOk()) {
            Actor1 player = s.getPlayer();
            LandPoint landPoint = s.getPlayer().getCurrent().getLandPoint();
            player.buy(landPoint);
            buyLand(player);
        } else {

        }

//        s.setAny();
        return chain.process(s);
    }

    public ConfirmResult buyLandConfirm() {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();
        MainGame.Instance.showConfirmWindow("will you want to buy this land ?", reporter);
        return reporter.waitReport();
    }

    /**
     * 替换地图
     *
     * @param actor1
     * @return
     */
    public Object buyLand(Actor1 actor1) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.buyLand(actor1, reporter);
        return reporter.waitReport();
    }

}
