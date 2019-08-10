package com.mygdx.game.entity;

public class ConfirmResult extends BaseResult {
    private boolean isOk = false;

    public ConfirmResult(int type) {
        super(type);
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isNotOK() {
        return !isOk;
    }

    public ConfirmResult setOk(boolean ok) {
        isOk = ok;
        return this;
    }
}
