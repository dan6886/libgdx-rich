package com.mygdx.game.handler;

import com.mygdx.game.actors.Actor1;
import com.mygdx.game.actions.BaseAction;

public abstract class BaseHandler implements IHandler {


    @Override
    public <T> T waitAction(BaseAction<T> action, HandlerEntity s) {
        return action.doAction(s);
    }

    @Override
    public void justAction(BaseAction action) {
        action.justAction();
    }

    @Override
    public HandlerEntity doHandle(HandlerEntity entity, HandlerChain chain) {
        return null;
    }

    public static class HandlerEntity {
        private int condition = 0;
        /**
         * 可以指定责任链对象类名，这样可以直接跳到对应的点去执行
         */
        private ParcelData parcelData = ParcelData.Builder.DEFAULT;
        /**
         * 玩家
         */
        private Actor1 player;
        /**
         * 移动的步数
         */
        private int moveCount = 10;

        public ParcelData getParcelData() {
            return parcelData;
        }

        public void setParcelData(ParcelData data) {
            this.parcelData = data;
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

        @Override
        public String toString() {
            return "HandlerEntity{" +
                    "condition=" + condition +
                    ", parcelData=" + parcelData +
                    ", player='" + player + '\'' +
                    ", moveCount=" + moveCount +
                    '}';
        }
    }
}
