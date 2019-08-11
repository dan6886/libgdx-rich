package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;

public class LandPoint extends Point {

    public static LandPoint NOTHINIG = new LandPoint(new MapObject(), -1, -1, -1, -1);
    private String ownerName = "";
    private String name = "";
    private int level = 0;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LandPoint(MapObject object, int row, int col, int x, int y) {
        super(object, row, col, x, y);
    }

    public boolean isNothing() {
        return NOTHINIG.equals(this);
    }

    public boolean isBlank() {
        return "".equals(getOwnerName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void levelUp() {
        if (level < 5) {
            level++;
        }
    }

    public void levelDown() {
        if (level > 0) {
            level--;
        }
    }

    public int getLevel() {
        return level;
    }

    public boolean isCanLevelUp() {
        return getLevel() < 5;
    }

    public String getBuildingTiledName() {
        return name + "_" + level;
    }

}
