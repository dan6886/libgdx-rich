package com.mygdx.game.entity;

public class BaseResult {
    private int type = 0;
    public static final int EVENT_BUY_LAND = 1;
    public static final int EVENT_BUILD_LAND = 2;
    public static final int EVENT_PAY_LAND = 3;

    public BaseResult(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
