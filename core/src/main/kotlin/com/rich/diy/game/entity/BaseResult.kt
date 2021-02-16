package com.rich.diy.game.entity

open class BaseResult(type: Int) {
    var type = 0

    companion object {
        const val EVENT_BUY_LAND = 1
        const val EVENT_BUILD_LAND = 2
        const val EVENT_PAY_LAND = 3
    }

    init {
        this.type = type
    }
}