package com.rich.diy.game.handler

import com.rich.diy.game.events.StartEvent


class CheckStartWalkHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        if (s.player!!.state == 0) {
//            showSkip("i am sleep")
            return s
        }
        val count = waitClickStart()
        s.moveCount = count
        return chain.process(s)
    }

    private fun waitClickStart(): Int {
        return StartEvent("START_BUTTON").postAndWait()
    }
}