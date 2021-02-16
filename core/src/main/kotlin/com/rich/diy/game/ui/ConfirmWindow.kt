package com.rich.diy.game.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.utils.Align
import com.rich.diy.game.entity.ConfirmResult
import io.reactivex.subjects.PublishSubject
import ktx.actors.centerPosition
import ktx.actors.onClick
import ktx.scene2d.*
import com.badlogic.gdx.scenes.scene2d.actions.Actions


class ConfirmWindow : Window {
    private lateinit var subject: PublishSubject<ConfirmResult>
    private val type = 0
    private var label: Label? = null
    var text: String? = null
        set(text) {
            field = text
            label!!.setText(text)
        }

    constructor(title: String?, skin: Skin = Scene2DSkin.defaultSkin, subject: PublishSubject<ConfirmResult>) : super(
        title,
        skin
    ) {
        this.skin = skin
        this.subject = subject
        setSize(220f, 180f)
        addActor(root)
    }

    private val root = scene2d.table {
        setFillParent(false)
        background = skin.getDrawable("background")
        width = 220f
        height = 180f
        label = label(text.toString(), style = "decorative") {
            setFontScale(0.5f)
            setAlignment(Align.center)
        }.apply {
            centerPosition(width, height)
        }.cell(row = true)
        textButton("ok", style = "decorative") {
            width = 80F
            onClick {
                subject.onNext(ConfirmResult(type).setOk(false))
                subject.onComplete()
                this@ConfirmWindow.remove()
            }
            align(Align.left)
        }
        textButton(text = "no", style = "decorative") {
            onClick {
                subject.onNext(ConfirmResult(type).setOk(false))
                subject.onComplete()
                this@ConfirmWindow.remove()
            }
        }
    }
//
//    private fun init() {
//        setSize(220f, 180f)
//        // 默认文字是在左边显示，需要手动设置居中
//        titleLabel.setAlignment(Align.center)
//        // 默认window的位置是在左下角，需重新设置
//        x = Gdx.graphics.getWidth() / 2 - width / 2
//        y = Gdx.graphics.getHeight() / 2 - height / 2
//        // 拖动TitleLabel，window会移动
//        isMovable = true
//        label = Label("但是\n222\n333\n444\n\n\n\n", skin)
//        label!!.x = 5f
//        label!!.y = 0f
//        label!!.color = Color.RED
//        val tbOk = TextButton("OK", skin)
//        val tbCancel = TextButton("CANCEL", skin)
//        tbOk.setSize(tbCancel.getPrefWidth(), tbCancel.getPrefHeight())
//        tbCancel.addListener(object : ClickListener() {
//            override fun clicked(event: InputEvent, x: Float, y: Float) {
//                Gdx.app.log("TAG", "dialog cancel button is clicked")
//                subject.onNext(ConfirmResult(type).setOk(false))
//                subject.onComplete()
//                this@ConfirmWindow.remove()
//            }
//        })
//        tbOk.addListener(object : ClickListener() {
//            override fun clicked(event: InputEvent, x: Float, y: Float) {
//                Gdx.app.log("TAG", "dialog ok button is clicked")
//                subject.onNext(ConfirmResult(type).setOk(true))
//                subject.onComplete()
//                this@ConfirmWindow.remove()
//            }
//        })
//        tbOk.setX(width / 2 - tbOk.getWidth() - 10)
//        tbOk.setY(10f)
//        tbCancel.setX(width / 2 + 10)
//        tbCancel.setY(10f)
//        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
//        addActor(label)
//        addActor(tbOk)
//        addActor(tbCancel)
//    }
}


fun confirmWindow(text: String, subject: PublishSubject<ConfirmResult>) = scene2d.window(title = "") {
    val type = 0
//    background("background")
    table {
//        background("background")
        defaults().pad(2f)
        // Adding table children:
        label(text, style = "decorative").cell(row = true)
        // Cell of the nested actor is also available through "it":
        it.spaceBottom(10f).row()

        textButton(text = "ok", style = "decorative") {
            scaleBy(0.1F)
            onClick {
                subject.onNext(ConfirmResult(type).setOk(true))
                subject.onComplete()
                this@window.remove()
            }
        }.cell(row = true)
        textButton(text = "no", style = "decorative") {
            scaleBy(0.1F)
            onClick {
                subject.onNext(ConfirmResult(type).setOk(false))
                subject.onComplete()
                this@window.remove()
            }
        }
    }

    width = 300F
    height = 80F

    x = 320F
    y = 120f
    pack()
}

fun tipWindow(text: String) = scene2d.window("") {
    table {
        defaults().pad(2f)
        // Adding table children:
        label(text, style = "decorative").cell(row = true)
        // Cell of the nested actor is also available through "it":
        it.spaceBottom(10f).row()
    }

    width = 300F
    height = 80F

    x = 320F
    y = 120f
    pack()
}

fun Actor.delayRemove(removed: Runnable) {
    val dismissAction: Action = Actions.run {
        this.remove()
        removed.run()
    }
    val action: Action = Actions.delay(3f, dismissAction)
    addAction(action)
}
