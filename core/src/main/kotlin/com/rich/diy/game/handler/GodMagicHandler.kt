package com.rich.diy.game.handler

class GodMagicHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        return chain.process(s)
    }
}