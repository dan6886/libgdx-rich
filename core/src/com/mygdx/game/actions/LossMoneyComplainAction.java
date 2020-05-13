package com.mygdx.game.actions;


import com.mygdx.game.handler.BaseHandler;
import com.mygdx.game.handler.ReportUtils;
import com.mygdx.game.handler.ResultReporter;

public class LossMoneyComplainAction extends ComplainAction {
    @Override
    public BaseHandler.HandlerEntity doAction(BaseHandler.HandlerEntity entity) {
        ResultReporter<String> reporter = new ResultReporter<>();
        System.out.println("say to much");
        ReportUtils.deley(3, reporter, "too much money");
        String s = reporter.waitReport();
        System.out.println(s);
        return super.doAction(entity);
    }
}
