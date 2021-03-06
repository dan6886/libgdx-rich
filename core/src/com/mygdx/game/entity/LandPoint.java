package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Actor1;

public class LandPoint extends Point {

    public static LandPoint NOTHINIG = new LandPoint(new MapObject(), -1, -1, -1, -1);
    private Actor1 owner;
    private String name = "";
    private int level = 0;
    private int price = 400;
    private int buildPrice = price * 1;

    public String getOwnerName() {
        if (null == owner) {
            return "";
        }
        return owner.getName();
    }

    public Actor1 getOwner() {
        return owner;
    }

    public void setOwner(Actor1 owner) {
        this.owner = owner;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuildPrice() {
        return price * level;
    }

    public void setBuildPrice(int buildPrice) {
        this.buildPrice = buildPrice;
    }

    public void buildUp() {
        levelUp();
        setPrice(getPrice() * 2);
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
