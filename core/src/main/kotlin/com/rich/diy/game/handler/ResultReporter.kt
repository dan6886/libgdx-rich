package com.rich.diy.game.handler

import java.util.*
import java.util.concurrent.Exchanger

class ResultReporter<T> {
    var exchanger = Exchanger<T?>()
    fun report(t: T) {
        try {
            exchanger.exchange(t)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun waitReport(): T {
        try {
            return exchanger.exchange(null)!!
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return null!!
    }
}