package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;
import com.mygdx.game.handler.ResultReporter;

public class PayLandAction extends BaseAction<BaseHandler.HandlerEntity> {

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        int price = entity.getPlayer().getCurrent().getLandPoint().getPrice();

        entity.setParcelData(entity.getParcelData().toBuilder().add("pay", price).build());
        Boolean use = waitAction(new CardInquiryAction(), entity);
        if (!use) {
            entity.getPlayer().payMoney(price);
        }
        return entity;
    }
}
