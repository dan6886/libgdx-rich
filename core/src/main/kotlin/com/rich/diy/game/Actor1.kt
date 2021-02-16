package com.rich.diy.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.entity.WayPoint
import com.rich.diy.game.handler.ResultReporter

class Actor1(name: String, texture: Texture?) : Actor() {
    private val region: TextureRegion
    var currentWayPoint: WayPoint? = null
    private var pre: WayPoint? = null
    var state = 1
    private var cash = 10000
    fun start() {}
    fun getCurrent(): WayPoint? {
        return currentWayPoint
    }

    fun setCurrent(current: WayPoint?) {
        this.currentWayPoint = current
    }

    fun getPre(): WayPoint? {
        return pre
    }

    fun setPre(pre: WayPoint?) {
        this.pre = pre
    }

    fun startWalk(runnable: Runnable) {
        val nextWayPoint: WayPoint = TiledMapUtils.getNextWayPoint(currentWayPoint, pre)
        walkTo(nextWayPoint) {
            pre = currentWayPoint
            currentWayPoint = nextWayPoint
            runnable.run()
        }
    }

    private fun walkTo(point: WayPoint?, callback: Runnable) {
        val move: MoveToAction = Actions.moveTo(point!!.x.toFloat(), point!!.y.toFloat(), 0.2f)
        val finish: RunnableAction = Actions.run(callback)
        addAction(Actions.sequence(move, finish))
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch.draw(region, getX(), getY())
    }

    override fun act(delta: Float) {
        super.act(delta)
    }

    fun buy(point: LandPoint) {
        point.setOwner(this)
        System.out.println("购买了土地" + point.toString())
    }

    fun build(point: LandPoint) {
        payMoney(point.buildPrice)
        point.buildUp()
        System.out.println("支付升级费:" + point.toString())
    }

    fun pay(point: LandPoint) {
        val owner: Actor1? = point.getOwner()
        val price: Int = point.price
        payMoney(price)
        owner!!.addMoney(price)
        System.out.println("支付过路费:" + point.toString())
    }

    fun addMoney(money: Int) {
        cash += money
    }

    fun payMoney(money: Int) {
        cash -= money
    }

    override fun toString(): String {
        return "Actor1{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", cash=" + cash +
                '}'
    }

    init {
        region = TextureRegion(texture, 0, 0, 20, 20)
        this.name = name
    }
}