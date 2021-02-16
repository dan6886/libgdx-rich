package com.rich.diy.game.events

import com.rich.diy.game.handler.ResultReporter
import org.greenrobot.eventbus.EventBus

open class BaseEvent<T>(val type: String) {
    var resultReporter: ResultReporter<T> = ResultReporter()
    fun postAndWait():T {
        EventBus.getDefault().post(this)
       return resultReporter.waitReport()
    }
}