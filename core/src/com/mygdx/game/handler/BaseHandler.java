package com.mygdx.game.handler;

import com.mygdx.game.Actor1;

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
        /**
         * 可以指定责任链对象类名，这样可以直接跳到对应的点去执行
         */
        private ParcelData target = ParcelData.Builder.DEFAULT;
        /**
         * 玩家
         */
        private Actor1 player;
        /**
         * 移动的步数
         */
        private int moveCount = 10;

        public ParcelData getParcelData() {
            return target;
        }

        public void setParcelData(ParcelData target) {
            this.target = target;
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
