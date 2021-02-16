package com.rich.diy.game.events

import com.rich.diy.game.handler.BaseHandler

class ActionEvent<T>(type: String) : BaseEvent<T>(type = type) {
    var handlerEntity: BaseHandler.HandlerEntity? = null
}