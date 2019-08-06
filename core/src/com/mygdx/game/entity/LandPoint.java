package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;

public class LandPoint extends Point {

    public static LandPoint NOTHINIG = new LandPoint(new MapObject(), -1, -1, -1, -1);

    public LandPoint(MapObject object, int row, int col, int x, int y) {
        super(object, row, col, x, y);
    }

}
