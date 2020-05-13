package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;
import com.mygdx.game.handler.ResultReporter;

public class PayLandAction extends BaseAction<BaseHandler.HandlerEntity> {

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        Integer test = test();
        entity.setParcelData(entity.getParcelData().toBuilder().add("pay", test).build());
        Boolean use = waitAction(new CardInquiryAction(), entity);
        if (!use) {
            entity.getPlayer().payMoney(test);
        }
        return entity;
    }

    public Integer test() {
        ResultReporter<Integer> reporter = new ResultReporter<>();
        ReportUtils.deley(3, reporter, 500);
        Integer integer = reporter.waitReport();
        return integer;
    }
}
