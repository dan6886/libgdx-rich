package com.rich.diy.game.handler

import com.rich.diy.game.handler.BaseHandler.HandlerEntity
import java.util.*
import java.util.concurrent.Executors

class HandlerChain {
    private val chians: MutableList<BaseHandler> = ArrayList()
    private var index = 0
    private val service = Executors.newCachedThreadPool()
    fun addHandler(IHandler: BaseHandler): HandlerChain {
        chians.add(IHandler)
        return this
    }

    fun start(entity: HandlerEntity, run: Runnable) {
        service.submit {
            process(entity)
            println("任务链执行完毕")
            // 触发回调
            run.run()
        }
    }

    fun process(entity: HandlerEntity): HandlerEntity? {
        return if (index >= chians.size) {
            entity
        } else {
            val IHandler = chians[index]
            index++
            val target = entity.parcelData
            if (IHandler.isNeedHandle(target!!.targetHandlerName)) {
                println("执行" + IHandler.javaClass.simpleName)
                IHandler.doHandle(entity, this)
            } else {
                process(entity)
            }
        }
    }

    fun reset() {
        index = 0
    }
}