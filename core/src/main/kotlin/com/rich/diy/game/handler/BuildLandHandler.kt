package com.rich.diy.game.handler

import com.rich.diy.game.Actor1
import com.rich.diy.game.entity.ConfirmResult
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.events.LandEvent
import com.rich.diy.game.events.TipsEvent


class BuildLandHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        val landPoint: LandPoint = s.player!!.getCurrent()!!.landPoint
        if (landPoint.isCanLevelUp) {
            val confirmResult: ConfirmResult? = buildLandConfirm()
            if (confirmResult!!.isOk) {
                val type: String = landPoint.type
                if ("small" == type) {
                    landPoint.name = "house"

                    // 小地
                    // 可以升级
                    s!!.player!!.build(s!!.player!!.getCurrent()!!.landPoint)
                    val o = buildUIUp(s.player!!)
                    showTipsWindow("welcome to my land")
                } else if ("big" == type) {
                    println("大地")
                }
            } else {
                println("不愿意升级")
            }
        } else {
            println("不能升级")
        }
        return chain.process(s)
    }

    fun buildLandConfirm(): ConfirmResult? {
        val reporter: ResultReporter<ConfirmResult> = ResultReporter<ConfirmResult>()
//        MainGame.Instance.showConfirmWindow("will you want to build up this land?", reporter)
        return LandEvent<ConfirmResult>("build_confirm").postAndWait()
    }

    private fun buildUIUp(actor1: Actor1): Any? {
        val reporter = ResultReporter<Any>()
//        MainGame.Instance.buildUIUp(actor1, reporter)
        return LandEvent<Any>("build_up").postAndWait()
//        return reporter.waitReport()
    }

    private fun showTipsWindow(text: String) {
        val reporter = ResultReporter<Any>()
//        MainGame.Instance.showTipsWindow(text, reporter)
        TipsEvent("TipsEvent").apply {
            tips = text
        }.postAndWait()
//        reporter.waitReport()
    }
}