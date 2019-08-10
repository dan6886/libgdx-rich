package com.mygdx.game.entity;

public class PayResult extends BaseResult {

    private boolean isOk = false;

    public PayResult(int type) {
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
