package com.rich.diy.game.events

class TipsEvent(type: String) : BaseEvent<Any>(type = type) {
    var tips: String = ""
}