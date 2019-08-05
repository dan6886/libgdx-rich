package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;

import java.util.Objects;

public class WayPoint {
    private MapObject object;
    private int row;
    private int col;
    private int x;
    private int y;
    private LandPoint landPoint = new LandPoint();

    public WayPoint(MapObject object, int row, int col, int x, int y) {
        this.object = object;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
    }

    public MapObject getObject() {
        return object;
    }

    private void setObject(MapObject object) {
        this.object = object;
    }

    public int getRow() {
        return row;
    }

    private void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    private void setCol(int col) {
        this.col = col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLandPoint(LandPoint landPoint) {
        this.landPoint = landPoint;
    }

    public LandPoint getLandPoint() {
        return landPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayPoint wayPoint = (WayPoint) o;
        return row == wayPoint.row &&
                col == wayPoint.col &&
                Objects.equals(object, wayPoint.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, row, col);
    }

    @Override
    public String toString() {
        return "WayPoint{" +
                "object=" + object +
                ", row=" + row +
                ", col=" + col +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
