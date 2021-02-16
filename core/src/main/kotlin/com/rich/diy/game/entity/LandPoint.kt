package com.rich.diy.game.entity

import com.badlogic.gdx.maps.MapObject
import com.rich.diy.game.Actor1

class LandPoint(`object`: MapObject, row: Int, col: Int, x: Int, y: Int) : Point(`object`, row, col, x, y) {
    private var owner: Actor1? = null
    var name = ""
    var level = 0
        private set
    var price = 400
    var buildPrice = price * 1
        get() = price * level
    val ownerName: String
        get() = if (null == owner) {
            ""
        } else owner!!.getName()

    fun getOwner(): Actor1? {
        return owner
    }

    fun setOwner(owner: Actor1?) {
        this.owner = owner
    }

    val isNothing: Boolean
        get() = NOTHINIG == this
    val isBlank: Boolean
        get() = "" == ownerName

    fun buildUp() {
        levelUp()
        price = price * 2
    }

    fun levelUp() {
        if (level < 5) {
            level++
        }
    }

    fun levelDown() {
        if (level > 0) {
            level--
        }
    }

    val isCanLevelUp: Boolean
        get() = level < 5
    val buildingTiledName: String
        get() = name + "_" + level

    companion object {
        var NOTHINIG = LandPoint(MapObject(), -1, -1, -1, -1)
    }
}