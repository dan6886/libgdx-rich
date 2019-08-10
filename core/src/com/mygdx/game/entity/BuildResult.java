package com.mygdx.game.entity;

public class BuildResult extends BaseResult {
    private int eventType = 0;

    private boolean isOk = false;

    public int getEventType() {
        return eventType;
    }

    public BuildResult(int eventType) {
        super(eventType);
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
