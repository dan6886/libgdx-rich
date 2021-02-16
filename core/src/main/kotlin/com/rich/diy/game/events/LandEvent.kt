package com.rich.diy.game.events

class LandEvent<T>(type: String) : BaseEvent<T>(type = type) {
    var msg = ""
}