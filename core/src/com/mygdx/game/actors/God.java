package com.mygdx.game.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class God extends Actor {

    public static final int GOD_LAND = 1;
    private int type = -1;

    private String name = "";

    private int activeDays = 7;

    private Actor1 master = null;

    public God(int type, String name) {
        this.type = type;
        this.name = name;
    }

    protected void showMagic() {
        if (master == null) {
            throw new IllegalStateException("master is not,can not show");
        }
        //todo show
        doShowMagic();
    }

    abstract void doShowMagic();

    protected void decreaseActiveDays() {
        activeDays--;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getActiveDays() {
        return activeDays;
    }
}
