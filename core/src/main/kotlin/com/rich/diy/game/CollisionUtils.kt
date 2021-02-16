package com.rich.diy.game

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.*
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

object CollisionUtils {
    fun isCollision(rect1: Rectangle, rect2: Rectangle): Boolean {
        val x1 = rect1.x
        val y1 = rect1.y
        val w1 = rect1.width
        val h1 = rect1.height
        val x2 = rect2.x
        val y2 = rect2.y
        val w2 = rect2.width
        val h2 = rect2.height
        //        return x1 < x2 + w2 && x2 < x1 + w1 && y1 < y2 + h2 && y2 < y1 + h1;
        return rect1.overlaps(rect2)
    }

    fun isCollision(object1: MapObject, object2: MapObject): Boolean {
        return isCollision(
            getRectangle(object1),
            getRectangle(object2)
        )
    }

    fun getRectangle(`object`: MapObject): Rectangle {
        return Rectangle(
            `object`.properties.get("x", Float::class.java).toInt().toFloat(),
            `object`.properties.get("y", Float::class.java).toInt().toFloat(),
            `object`.properties.get("width", Float::class.java).toInt().toFloat(),
            `object`.properties.get("height", Float::class.java).toInt().toFloat()
        )
    }

    fun getRectangle(actor: Actor): Rectangle {
        return Rectangle(
            actor.x,
            actor.y,
            actor.width,
            actor.height
        )
    }

    fun getContactDirection(vector2: Array<Vector2>, rectangle: Rectangle): Int {
        val v1 = vector2[0]
        val v2 = vector2[1]
        if (v1.x == v2.x) {
            // 左右碰撞
            return if (Math.abs(v1.x - rectangle.x) < Math.abs(v1.x - (rectangle.x + rectangle.width))) {
                // 左
                Input.Keys.A
            } else {
                // 右
                Input.Keys.D
            }
        } else if (v1.y == v2.y) {
            // 上下碰撞
            return if (Math.abs(v1.y - rectangle.y) < Math.abs(v1.y - (rectangle.y + rectangle.height))) {
                // 上
                Input.Keys.S
            } else {
                // 下
                Input.Keys.W
            }
        }
        return -1
    }
}