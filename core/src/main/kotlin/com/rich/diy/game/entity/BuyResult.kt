package com.rich.diy.game.entity

class BuyResult(type: Int) : BaseResult(type) {
    var isOk = false
    val isNotOK: Boolean
        get() = !isOk
}