package com.rich.diy.game.handler

import com.rich.diy.game.Actor1

abstract class BaseHandler : IHandler {
    //    public static String START_WALK_HANDLER = "StartWalkHandler";
    var name: String? = null
    fun isNeedHandle(target: String?): Boolean {
        if ("any" == target) {
            return true
        }
        return if (this.javaClass.simpleName == target) {
            true
        } else false
    }

    class HandlerEntity {
        var condition = 0

        /**
         * 可以指定责任链对象类名，这样可以直接跳到对应的点去执行
         */
        var parcelData: ParcelData? = ParcelData.Builder.Companion.DEFAULT

        /**
         * 玩家
         */
        var player: Actor1? = null

        /**
         * 移动的步数
         */
        var moveCount = 10
    }

    companion object {
        var START_WALK_HANDLER = "StartWalkHandler"
        var PASS_HANDLER = "PassHandler"
        var STOP_WAY_HANDLER = "StopWayHandler"
        var BUILD_LAND_HANDLER = "BuildLandHandler"
        var PAY_LAND_HANDLER = "PayLandHandler"
        var BUY_LAND_HANDLER = "BuyLandHandler"
    }
}