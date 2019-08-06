package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;

public class WayPoint extends Point {
    private LandPoint landPoint;

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


}
