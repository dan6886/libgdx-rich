package com.rich.diy.game.handler

import io.reactivex.Observable

object Main {
    @JvmStatic
    fun main(arg: Array<String>) {
        val handlerChain = HandlerChain()
        // 开始行走
        handlerChain.addHandler(StartWalkHandler())
        // 路过
        handlerChain.addHandler(PassHandler())
        // 停下 猜到路点
        handlerChain.addHandler(StopWayHandler())
        //        // 结算土地,这里有一个参数传递下去，判断下来的逻辑是谁处理
//        handlerChain.addHandler(new StopLandHandler());
//        // 购买空地（话语）
//        handlerChain.addHandler(new StopLandHandler());
//        // 升级空地（话语）
//        handlerChain.addHandler(new StopLandHandler());
//        // 付过路费 (话语)
//        handlerChain.addHandler(new StopLandHandler());
//        // 结束(一些神明的处理)
//        handlerChain.addHandler(new StopLandHandler());
        val entity = BaseHandler.HandlerEntity()
        entity.moveCount = 5
        println(handlerChain.process(entity))
    }
}