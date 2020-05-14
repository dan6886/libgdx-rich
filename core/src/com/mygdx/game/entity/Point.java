package com.mygdx.game.entity;

import com.badlogic.gdx.maps.MapObject;

import java.util.Objects;

public class Point {
    private MapObject object;
    private int row;
    private int col;
    private int x;
    private int y;
    //默认small
    private String type = "small";

    public Point(MapObject object, int row, int col, int x, int y) {
        this.object = object;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
    }

    public MapObject getObject() {
        return object;
    }

    public void setObject(MapObject object) {
        this.object = object;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (null != type && !"".equals(type)) {
            this.type = type;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return row == point.row &&
                col == point.col &&
                x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "object=" + object +
                ", row=" + row +
                ", col=" + col +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
