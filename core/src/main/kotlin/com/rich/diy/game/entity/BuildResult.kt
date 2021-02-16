package com.rich.diy.game.entity

class BuildResult(eventType: Int) : BaseResult(eventType) {
    val eventType = 0
    var isOk = false
    val isNotOK: Boolean
        get() = !isOk
}