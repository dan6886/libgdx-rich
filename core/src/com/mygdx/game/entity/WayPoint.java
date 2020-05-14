package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.actors.God;

public class WayPoint extends Point {
    private LandPoint landPoint;
    private God god;

    public WayPoint(MapObject object, int row, int col, int x, int y, LandPoint landPoint) {
        super(object, row, col, x, y);
        this.landPoint = landPoint;
    }


    public void setLandPoint(LandPoint landPoint) {
        this.landPoint = landPoint;
    }

    public LandPoint getLandPoint() {
        return landPoint;
    }

    public void setGod(God god) {
        this.god = god;
    }

    public God getGod() {
        return god;
    }

    public boolean hasGod() {
        return god != null;
    }

}
