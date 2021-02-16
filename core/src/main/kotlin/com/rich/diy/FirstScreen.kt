package com.rich.diy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rich.diy.game.Actor1
import com.rich.diy.game.LandManager
import com.rich.diy.game.LandManager.Companion.LAND
import com.rich.diy.game.LandManager.Companion.LANDBASE
import com.rich.diy.game.TileSetIdManager
import com.rich.diy.game.TiledMapUtils
import com.rich.diy.game.entity.ConfirmResult
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.events.ActionEvent
import com.rich.diy.game.events.LandEvent
import com.rich.diy.game.events.StartEvent
import com.rich.diy.game.handler.*
import com.rich.diy.game.ui.win
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ktx.app.KtxScreen
import ktx.scene2d.label
import ktx.scene2d.scene2d
import ktx.scene2d.table
import ktx.scene2d.textButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.rich.diy.game.handler.HandlerChain as HandlerChain1

/** First screen of the application. Displayed after the application is created.
 * https://github.com/libktx/ktx/tree/master/scene2d
 *
 * */
class FirstScreen(val stage: Stage) : KtxScreen {
    val backgroundImage = Texture("menu.png")
    val view = scene2d.table {
        setFillParent(true)
        background = TextureRegionDrawable(TextureRegion(backgroundImage, 0, 0, 600, 600))
        touchable = Touchable.enabled

        label(text = "click to move", style = "decorative").cell(padLeft = 320f, row = true, padBottom = 5f)
        label(text = "point to aim", style = "decorative").cell(padLeft = 335f, row = true, padBottom = 10f)
        label(text = "QWER to cast", style = "decorative") {
        }.cell(padLeft = 290f, row = true, padBottom = 240f)
        table {
            label(text = "Click to ", style = "decorative")
            label(text = "play", style = "decorative") {
                color = Color.PURPLE
            }
//            onClick { _, _ -> application.setScreen<Game>() }
//            onClick { _, _ -> stage.addActor(SelectedWindow()) }

        }.cell(padBottom = 50f)

//        addListener(object : ClickListener() {
//            override fun clicked(event: InputEvent?, x: Float, y: Float) {
//                application.setScreen<Game>()
//            }
//        })
    }
    var handlerChain: HandlerChain1 = HandlerChain1()
    private lateinit var actor1: Actor1
    private var startButton = scene2d.textButton("start", style = "decorative") {
    }
    private val camera = OrthographicCamera().apply {
        position.set((640 / 2).toFloat(), (480 / 2).toFloat(), 0f)
        update()
    }

    private var renderer: OrthogonalTiledMapRenderer

    init {
        LandManager.get().init("110.tmx")
        // 开始前检测
        handlerChain.addHandler(CheckStartWalkHandler())
        // 开始行走
        handlerChain.addHandler(StartWalkHandler())
        // 路过
        handlerChain.addHandler(PassHandler())
        // 各路神仙施展魔法,或者触发神灵得到效果
        handlerChain.addHandler(GodMagicHandler())
        // 停下 猜到路点
        handlerChain.addHandler(StopWayHandler())
        //
        handlerChain.addHandler(BuyLandHandler())
        handlerChain.addHandler(BuildLandHandler())
        handlerChain.addHandler(PayLandHandler())
        handlerChain.addHandler(FinishRoundHandler())
        actor1 = Actor1(
            "dan", Texture("badlogic.jpg")
        )
        actor1.currentWayPoint = LandManager.get().wayPointArray[0][0]
        renderer = OrthogonalTiledMapRenderer(LandManager.get().map)
        EventBus.getDefault().register(this)
    }

    private fun prepareWalk() {
        handlerChain.reset()
        val entity = BaseHandler.HandlerEntity()
        entity.player = actor1
        handlerChain.start(entity, Runnable {
            println("完成回调:" + actor1.toString())
            prepareWalk()
        })
        println("执行完毕了吗?")
    }

    override fun show() {
        println("show")
        Gdx.input.inputProcessor = stage
        stage.addActor(actor1)
        stage.addActor(startButton)
        prepareWalk()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        renderer.setView(stage.viewport.camera as OrthographicCamera)
        renderer.render()

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        // Resize your screen here. The parameters represent the new window size.
    }

    override fun pause() {
        // Invoked when your application is paused.
    }

    override fun resume() {
        // Invoked when your application is resumed after pause.
    }

    override fun hide() {
        // This method is called when another screen replaces this one.
    }

    override fun dispose() {
        view.remove()
        EventBus.getDefault().unregister(this)
    }

    fun showStartButton(reporter: ResultReporter<Int?>) {
        Gdx.app.postRunnable(Runnable {
            startButton.setVisible(true)
            //这里一定得记得清除上次的监听，否则会引起线程阻塞，上一个reporter 没有释放
            startButton.clearListeners()
            startButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    System.out.println("消费了点击" + reporter.toString())
                    startButton.setVisible(false)
                    reporter.report(1)
                }
            })
        })
    }


    fun showConfirmWindow(text: String?, reporter: ResultReporter<ConfirmResult?>) {
//        val subject: PublishSubject<ConfirmResult> = PublishSubject.create()
//        Gdx.app.postRunnable(Runnable {
//            println("主循环" + Thread.currentThread().name)
//            val tips = ConfirmWindow("tips", skin, subject)
//            tips.setText(text)
//            stage!!.addActor(tips)
//        })
//        //开启线程 showWindow
//        subject.observeOn(Schedulers.newThread())
//            .subscribe(object : Consumer<ConfirmResult?>() {
//                @Throws(Exception::class)
//                override fun accept(confirmResult: ConfirmResult?) {
//                    reporter.report(confirmResult)
//                }
//            })
    }

    fun buyLand(player: Actor1, waiter: ResultReporter<Any?>) {
        Gdx.app.postRunnable(Runnable {
            val id = TileSetIdManager.get().getLandBaseId(player.name, player.currentWayPoint!!.landPoint.type)
            TiledMapUtils.markTileOwner(
                LandManager.get().getLayerByName(LANDBASE),
                player.currentWayPoint!!.landPoint,
                LandManager.get().tileSets,
                id
            )
            waiter.report(Any())
        })
        //开启线程 showWindow
    }

    fun buildUIUp(actor: Actor1, reporter: ResultReporter<Any?>) {
        Gdx.app.postRunnable(Runnable {
            val landPoint: LandPoint = actor.currentWayPoint!!.landPoint
            val houseTileId = TileSetIdManager.get().getHouseTileId(landPoint.buildingTiledName)
            TiledMapUtils.markTileOwner(
                LandManager.get().getLayerByName(LAND),
                landPoint,
                LandManager.get().tileSets,
                houseTileId
            )
            reporter.report(Any())
        })
    }

    fun showTipsWindow(tips: String?, reporter: ResultReporter<Any?>) {
//        Gdx.app.postRunnable(Runnable {
//            val messageWindow = MessageWindow("tips", skin)
////            messageWindow.setMessage(tips)
//            stage!!.addActor(messageWindow)
//            messageWindow.startDismiss(Runnable {
//                messageWindow.remove()
//                reporter.report(Any())
//            })
//        })
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun handleLandEvent(baseEvent: LandEvent<Any>) {
        Gdx.app.postRunnable {
            when (baseEvent.type) {
                "buyLandConfirm" -> {
                    val subject: PublishSubject<ConfirmResult> = PublishSubject.create()
                    val tips = win(baseEvent.msg, subject)
                    stage.addActor(tips)
                    subject.observeOn(Schedulers.newThread())
                        .subscribe(object : Consumer<ConfirmResult> {
                            @Throws(Exception::class)
                            override fun accept(confirmResult: ConfirmResult?) {
                                baseEvent.resultReporter.report(confirmResult as Any)
                            }
                        })
                }
                "buyLand" -> {
                    val id: Int =
                        TileSetIdManager.get().getLandBaseId(actor1.getName(), actor1.currentWayPoint!!.landPoint.type)
                    TiledMapUtils.markTileOwner(
                        LandManager.get().getLayerByName(LANDBASE),
                        actor1.currentWayPoint!!.landPoint,
                        LandManager.get().tileSets,
                        id
                    )
                    baseEvent.resultReporter.report(Any())
                }
            }
        }

        println(baseEvent)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun handleStartEvent(startEvent: StartEvent) {
        Gdx.app.postRunnable(Runnable {
            startButton.setVisible(true)
            //这里一定得记得清除上次的监听，否则会引起线程阻塞，上一个reporter 没有释放
            startButton.clearListeners()
            startButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    System.out.println("消费了点击" + startEvent.resultReporter.toString())
                    startButton.setVisible(false)
                    startEvent.resultReporter.report(1)
                }
            })
        })
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun handleActionEvent(actionEvent: ActionEvent<Any>) {
        Gdx.app.postRunnable {
            actionEvent.handlerEntity!!.player!!.startWalk(runnable = {
                actionEvent.resultReporter.report(actor1.currentWayPoint as Any)
            })
        }
    }
}