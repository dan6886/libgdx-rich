package com.rich.diy.game.events

import com.rich.diy.game.Actor1
import com.rich.diy.game.handler.BaseHandler
import com.rich.diy.game.handler.ResultReporter
import org.greenrobot.eventbus.EventBus

open class BaseEvent<T>(val type: String) {
    var msg = ""
    lateinit var player: Actor1
    var resultReporter: ResultReporter<T> = ResultReporter()
    fun postAndWait(): T {
        EventBus.getDefault().post(this)
        return resultReporter.waitReport()
    }

    override fun toString(): String {
        return "BaseEvent(type='$type', msg='$msg')"
    }

}