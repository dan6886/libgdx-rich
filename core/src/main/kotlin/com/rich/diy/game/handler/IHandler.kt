package com.rich.diy.game.handler

import com.rich.diy.game.handler.BaseHandler.HandlerEntity

interface IHandler {
    fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity?
}