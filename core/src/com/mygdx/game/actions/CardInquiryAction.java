package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;

/**
 * 选择卡片
 */
public class CardInquiryAction extends BaseAction<Boolean> {
    @Override
    public Boolean doAction(BaseHandler.HandlerEntity entity) {
        boolean test = test();
        if (test) {
            waitAction(new WillNotPayMoneyHappyAction(), entity);
        } else {
            waitAction(new PayMoneyComplainAction(), entity);
        }
        return test;
    }

    public boolean test() {
        Boolean console = ReportUtils.console("will you want to use card to pay free ", Boolean.class);
        return console;
    }
}
