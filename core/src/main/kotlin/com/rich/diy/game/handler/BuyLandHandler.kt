package com.rich.diy.game.handler

import com.rich.diy.game.Actor1
import com.rich.diy.game.entity.ConfirmResult
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.events.BaseEvent
import com.rich.diy.game.events.LandEvent
import org.greenrobot.eventbus.EventBus


class BuyLandHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        val confirmResult: ConfirmResult = buyLandConfirm()
        if (confirmResult.isOk) {
            val player: Actor1? = s.player
            val landPoint: LandPoint = s.player!!.getCurrent()!!.landPoint
            player!!.buy(landPoint)
            buyLand(player)
        } else {
        }
        return chain.process(s)
    }

    fun buyLandConfirm(): ConfirmResult {
       return LandEvent<ConfirmResult>("buyLandConfirm").apply {
           msg = "will you want to buy this land?"
       }.postAndWait()
    }

    /**
     * 替换地图
     *
     * @param actor1
     * @return
     */
    fun buyLand(actor1: Actor1?): Any {
        val reporter = ResultReporter<Any>()
        return LandEvent<Any>("buyLand").postAndWait()
    }
}