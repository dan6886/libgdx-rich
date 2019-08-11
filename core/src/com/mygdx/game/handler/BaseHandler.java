package com.mygdx.game.handler;

import com.mygdx.game.Actor1;
import org.omg.CORBA.Any;

public abstract class BaseHandler implements IHandler {
    public static String START_WALK_HANDLER = "StartWalkHandler";
    public static String PASS_HANDLER = "PassHandler";
    public static String STOP_WAY_HANDLER = "StopWayHandler";
    public static String BUILD_LAND_HANDLER = "BuildLandHandler";
    public static String PAY_LAND_HANDLER = "PayLandHandler";
    public static String BUY_LAND_HANDLER = "BuyLandHandler";
    //    public static String START_WALK_HANDLER = "StartWalkHandler";
    private String name;

    public BaseHandler() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedHandle(String target) {
        if ("any".equals(target)) {
            return true;
        }

        if (this.getClass().getSimpleName().equals(target)) {
            return true;
        }
        return false;
    }

    public static class HandlerEntity {
        private int condition = 0;
        private String target = "any";
        private Actor1 player;
        private int moveCount = 10;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public void setAny() {
            this.setTarget("Any");
        }

        public int getCondition() {
            return condition;
        }

        public void setCondition(int condition) {
            this.condition = condition;
        }

        public Actor1 getPlayer() {
            return player;
        }

        public void setPlayer(Actor1 player) {
            this.player = player;
        }

        public int getMoveCount() {
            return moveCount;
        }

        public void setMoveCount(int moveCount) {
            this.moveCount = moveCount;
        }
    }

}
