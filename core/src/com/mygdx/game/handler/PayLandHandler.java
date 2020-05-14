package com.mygdx.game.handler;

import com.mygdx.game.actors.Actor1;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;

public class PayLandHandler extends BaseHandler {

    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {
        Actor1 player = s.getPlayer();
        showTipMessage("you should pay for 500$!");
        boolean b = checkCard();
        if (b) {
            ConfirmResult confirmResult = showCard("will you want to use card to pay free");
            if (confirmResult.isOk()) {
                showTipMessage("i won't pay!!!");
            } else {
                player.pay(player.getCurrent().getLandPoint());
                // 抱怨
            }
        } else {
            player.pay(player.getCurrent().getLandPoint());
            // 抱怨
        }

        return chain.process(s);
    }

    public Object showTipMessage(String text) {
        ResultReporter<Object> reporter = new ResultReporter<>();
        MainGame.Instance.showTipsWindow(text, reporter);
        return reporter.waitReport();
    }

    public boolean checkCard() {
        // 这里可以直接进行卡片判断，有没有可以抵消的卡片
        return false;
    }

    private ConfirmResult showCard(String text) {
        ResultReporter<ConfirmResult> reporter = new ResultReporter<>();
        MainGame.Instance.showConfirmWindow(text, reporter);
        return reporter.waitReport();
    }
}
