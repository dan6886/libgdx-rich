package com.rich.diy.game.entity

import com.badlogic.gdx.maps.MapObject

class WayPoint(`object`: MapObject, row: Int, col: Int, x: Int, y: Int, var landPoint: LandPoint) :
    Point(`object`, row, col, x, y)