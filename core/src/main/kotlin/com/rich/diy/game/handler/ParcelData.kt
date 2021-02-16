package com.rich.diy.game.handler

import java.util.*

/**
 * handler 之间 传递的参数
 */
class ParcelData private constructor() {
    // 上一個handler的名称
    var prevHandlerName = ""
        private set
    var targetHandlerName = ""
        private set

    // 传递到此handler的原因，上一个handler为什么指向自己，常量指定
    var jumpReason = -1
        private set

    // 携带参数
    var map: Map<String, Any> = HashMap()
        private set

    class Builder {
        private var prevHandlerName = ""
        private var mTargetHandlerName = TARGET_ANY
        private var mJumpReason = -1
        private val map: MutableMap<String, Any> = HashMap()
        fun setPrevHandlerName(prevHandlerName: String): Builder {
            this.prevHandlerName = prevHandlerName
            return this
        }

        fun setTargetHandlerName(mTargetHandlerName: String): Builder {
            this.mTargetHandlerName = mTargetHandlerName
            return this
        }

        fun setJumpReason(mJumpReason: Int): Builder {
            this.mJumpReason = mJumpReason
            return this
        }

        fun add(key: String, value: Any): Builder {
            map[key] = value
            return this
        }

        fun build(): ParcelData {
            val parcelData = ParcelData()
            parcelData.prevHandlerName = prevHandlerName
            parcelData.targetHandlerName = mTargetHandlerName
            parcelData.jumpReason = mJumpReason
            parcelData.map = map
            return parcelData
        }

        companion object {
            var DEFAULT = Builder().build()
        }
    }

    companion object {
        const val TARGET_ANY = "any"
    }
}