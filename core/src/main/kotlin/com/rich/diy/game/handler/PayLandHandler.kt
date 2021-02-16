package com.rich.diy.game.handler

import com.rich.diy.game.Actor1
import com.rich.diy.game.entity.ConfirmResult
import com.rich.diy.game.events.CardEvent
import com.rich.diy.game.events.TipsEvent
import javax.swing.event.CaretEvent


class PayLandHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        val player: Actor1? = s.player
        showTipMessage("you should pay for 500$!")
        val b = checkCard()
        if (b) {
            val confirmResult: ConfirmResult = showCard("will you want to use card to pay free")
            if (confirmResult!!.isOk) {
                showTipMessage("i won't pay!!!")
            } else {
                player!!.pay(player.getCurrent().landPoint)
                // 抱怨
            }
        } else {
            player!!.pay(player.getCurrent().landPoint)
            // 抱怨
        }
        return chain.process(s)
    }

    fun showTipMessage(text: String): Any? {
        return TipsEvent("TipsEvent").apply {
            msg = text
        }.postAndWait()
    }

    fun checkCard(): Boolean {
        // 这里可以直接进行卡片判断，有没有可以抵消的卡片
        return false
    }

    private fun showCard(text: String): ConfirmResult {
        return CardEvent<ConfirmResult>("").postAndWait()
    }
}