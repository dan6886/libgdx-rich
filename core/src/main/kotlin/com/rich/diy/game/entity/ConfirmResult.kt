package com.rich.diy.game.entity

class ConfirmResult(type: Int) : BaseResult(type) {
    var isOk = false
        private set
    val isNotOK: Boolean
        get() = !isOk

    fun setOk(ok: Boolean): ConfirmResult {
        isOk = ok
        return this
    }
}