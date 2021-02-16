package com.rich.diy.game.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import ktx.scene2d.Scene2DSkin

class MessageWindow(title: String?) : Window(title, Scene2DSkin.defaultSkin), InputProcessor {
    var message = ""
        set(message) {
            field = message
            label!!.setText(message)
        }
    private var label: Label? = null
    var delay: DelayAction? = null
    fun startDismiss(run: Runnable) {
        delay = Actions.delay(3f, Actions.run {
            val multiplexer: InputMultiplexer = Gdx.input.getInputProcessor() as InputMultiplexer
            multiplexer.removeProcessor(this@MessageWindow)
            run.run()
        })
        addAction(delay)
    }

    private fun init() {
        setSize(220f, 180f)
        // 默认文字是在左边显示，需要手动设置居中
        titleLabel.setAlignment(Align.center)
        // 默认window的位置是在左下角，需重新设置
        x = Gdx.graphics.getWidth() / 2 - width / 2
        y = Gdx.graphics.getHeight() / 2 - height / 2
        // 拖动TitleLabel，window会移动
        isMovable = true
        label = Label("i am rich", skin)
        label!!.x = 5f
        label!!.y = 0f
        label!!.color = Color.GREEN
        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
        addActor(label)
    }

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    /**
     * 对话框出现的时候点击屏幕就可以取消自己
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        delay!!.finish()
        println("点击了事件")
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    fun scrolled(amount: Int): Boolean {
        return false
    }

    init {
        val inputProcessor: InputMultiplexer = Gdx.input.getInputProcessor() as InputMultiplexer
        inputProcessor.addProcessor(0, this)
        init()
    }
}