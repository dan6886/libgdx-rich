package com.rich.diy.game.handler

import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.entity.WayPoint
import com.rich.diy.game.events.BaseEvent

class StopWayHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        val current: WayPoint = s.player!!.getCurrent()
// 路点的事物
//然后处理接下来要对土地的操作
        val landPoint: LandPoint = current.landPoint
        println("停留处的关联land：" + landPoint)
        if (landPoint.isNothing) {
            //该路点对应不是土地模块
            s.parcelData = ParcelData.Builder()
                .setPrevHandlerName(this.javaClass.simpleName)
                .setTargetHandlerName(FinishRoundHandler::class.java.simpleName)
                .build()
        } else {
            if (landPoint.isBlank) {
                s.parcelData = ParcelData.Builder()
                    .setPrevHandlerName(this.javaClass.simpleName)
                    .setTargetHandlerName(BuyLandHandler::class.java.simpleName)
                    .build()
            } else {
                if (s.player!!.getName().equals(landPoint.ownerName)) {
                    s.parcelData = ParcelData.Builder()
                        .setPrevHandlerName(this.javaClass.simpleName)
                        .setTargetHandlerName(BuildLandHandler::class.java.simpleName)
                        .build()
                } else {
                    s.parcelData = ParcelData.Builder()
                        .setPrevHandlerName(this.javaClass.simpleName)
                        .setTargetHandlerName(PayLandHandler::class.java.simpleName)
                        .build()
                }
            }
        }
        return chain.process(s)
    }


}