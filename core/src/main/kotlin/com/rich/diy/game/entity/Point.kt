package com.rich.diy.game.entity

import com.badlogic.gdx.maps.MapObject
import java.util.Objects

open class Point(var `object`: MapObject, var row: Int, var col: Int, var x: Int, var y: Int) {
    var type = ""
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val point = o as Point
        return row == point.row && col == point.col && x == point.x && y == point.y
    }

    override fun hashCode(): Int {
        return Objects.hash(row, col, x, y)
    }

    override fun toString(): String {
        return "Point{" +
                "object=" + `object` +
                ", row=" + row +
                ", col=" + col +
                ", x=" + x +
                ", y=" + y +
                '}'
    }
}