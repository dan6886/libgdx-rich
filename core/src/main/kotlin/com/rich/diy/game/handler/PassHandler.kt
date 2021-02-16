package com.rich.diy.game.handler

import com.rich.diy.game.entity.WayPoint
import com.rich.diy.game.events.ActionEvent


class PassHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        var moveCount = s.moveCount
        while (moveCount > 0) {
            var wayPoint = doTask2(s)
            println("移动：$moveCount 到 $wayPoint")
            // 移动完毕的判断，路障或者银行
            moveCount--
        }
        return chain.process(s)
    }

    private fun doTask2(entity: HandlerEntity): WayPoint {
        val reporter: ResultReporter<WayPoint> = ResultReporter<WayPoint>()
//        MainGame.Instance.doWalk(entity.player!!, reporter)
        return ActionEvent<WayPoint>("walk").apply {
            handlerEntity = entity
        }.postAndWait()
    }
}