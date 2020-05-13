package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseAction;
import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ParcelData;
import com.mygdx.game.handler.ReportUtils;

public class LandSurpriseAction extends BaseAction<BaseHandler.HandlerEntity> {

    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        String s = test1();

        ParcelData parcelData = entity.getParcelData();
        entity.setParcelData(parcelData.toBuilder().add("god", s).build());
        if ("td".equals(s)) {
            waitAction(new MetGodHappyAction(), entity);
        } else {
            waitAction(new MetGodComplainAction(), entity);
        }

        return entity;
    }

    public String test1() {
        String god = ReportUtils.console("which god do you want", String.class);
        return god;
    }
}
