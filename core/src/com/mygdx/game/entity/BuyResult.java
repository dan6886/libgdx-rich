package com.mygdx.game.entity;

public class BuyResult extends BaseResult {

    private boolean isOk = false;

    public BuyResult(int type) {
        super(type);
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isNotOK() {
        return !isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
