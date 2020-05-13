package com.mygdx.game.handler;


import com.mygdx.game.actions.BuildAction;
import com.mygdx.game.actions.BuyLandAction;
import com.mygdx.game.actions.GodMagicAction;
import com.mygdx.game.actions.LandSurpriseAction;
import com.mygdx.game.actions.PayLandAction;

/**
 * 停下来之后
 * 检测路点有没有有意外收获
 * 检测神灵是否需要加成
 * 检测是否付钱，加盖，购买，进入npc路点
 */
public class StopWayHandler extends BaseHandler {

    @Override
    public BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain) {
        s = waitAction(new LandSurpriseAction(), s);

        int test = test();
        if (1 == test) {
            waitAction(new BuyLandAction(), s);
        } else if (2 == test) {
            waitAction(new BuildAction(), s);
        } else if (3 == test) {
            waitAction(new PayLandAction(), s);
        } else if (4 == test) {

        }

        waitAction(new GodMagicAction(), s);
        return chain.process(s);
    }

    public int test() {
        int console = ReportUtils.console("1,empty land\n2,your land\n3,others land \n4,npc land", Integer.class);
        return console;

    }
}
