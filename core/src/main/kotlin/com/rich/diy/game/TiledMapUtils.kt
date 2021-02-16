package com.rich.diy.game

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTile
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileSets
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.entity.WayPoint
import java.util.*

object TiledMapUtils {
    fun getCellPosition(layer: TiledMapTileLayer, x: Int, y: Int): Vector2 {
        val tileHeight: Float = layer.getTileHeight().toFloat()
        val tileWidth: Float = layer.getTileWidth().toFloat()
        val vector2 = Vector2()
        vector2.x = x * tileWidth
        vector2.y = y * tileHeight
        return vector2
    }

    fun convert2LDZPosition(mapHeight: Int, cellHeight: Int, x: Int, y: Int): Vector2 {
        val vector2 = Vector2()
        vector2.x = x.toFloat()
        vector2.y = (mapHeight * cellHeight - y - cellHeight).toFloat()
        return vector2
    }

    /**
     * tiled editor 里面编辑的时候坐标是从左上角开始的
     * libgdx 是从左下角开始的
     *
     * @param map
     * @param layerName
     * @param object
     */
    fun removeTiled(map: TiledMap, layerName: String?, `object`: MapObject) {
        val rectangle: Rectangle = CollisionUtils.getRectangle(`object`)
        val mapLayer: TiledMapTileLayer = map.getLayers().get(layerName) as TiledMapTileLayer
        val width: Int = map.getProperties().get<Int>("width", Int::class.java)
        val height: Int = map.getProperties().get<Int>("height", Int::class.java)
        val tilewidth: Int = map.getProperties().get<Int>("tilewidth", Int::class.java)
        val tileheight: Int = map.getProperties().get<Int>("tileheight", Int::class.java)
        val row = (rectangle.x / tilewidth).toInt()
        val col = (rectangle.y / tileheight).toInt()
        mapLayer.getCell(row, col).setTile(null)
        println("test")
    }

    /**
     * 查找路点的四个方向是否有路点
     *
     * @param wayPoint
     * @param currentWayPoint
     * @param preWayPoint
     * @return
     */
    fun findNextWayPoint(
        wayPoint: Array<Array<WayPoint?>>?,
        currentWayPoint: WayPoint?,
        preWayPoint: WayPoint?
    ): List<WayPoint?> {
        val dest: MutableList<WayPoint?> = ArrayList<WayPoint?>()
        val col: Int = currentWayPoint!!.col
        val row: Int = currentWayPoint!!.row
        if (row - 1 >= 0) {
            // 上方可查找
            val p: WayPoint? = wayPoint!![row - 1][col]
            if (p != null && !p.equals(preWayPoint)) {
                dest.add(p)
            }
        }
        if (row + 1 < Constants.ROW_NUM) {
            // 下方可查找
            val p: WayPoint? = wayPoint!![row + 1][col]
            if (p != null && !p.equals(preWayPoint)) {
                dest.add(p)
            }
        }
        if (col - 1 >= 0) {
            // 左边可查找
            val p: WayPoint? = wayPoint!![row][col - 1]
            if (p != null && !p.equals(preWayPoint)) {
                dest.add(p)
            }
        }
        if (col + 1 < Constants.COL_NUM) {
            // 右边可查找
            val p: WayPoint? = wayPoint!![row][col + 1]
            if (p != null && !p.equals(preWayPoint)) {
                dest.add(p)
            }
        }
        //
        if (dest.isEmpty()) {
            dest.add(preWayPoint)
        }
        return dest
    }

    fun markTileOwner(layer: TiledMapTileLayer?, point: LandPoint, sets: TiledMapTileSets?, id: Int) {
        val tile: TiledMapTile = sets!!.getTile(id)
        layer!!.getCell(point.row, point.col).setTile(tile)
    }

    fun getNextWayPoint(currentWayPoint: WayPoint?, preWayPoint: WayPoint?): WayPoint {
        val nextWayPoint: List<WayPoint?> =
            TiledMapUtils.findNextWayPoint(LandManager.get().wayPointArray, currentWayPoint, preWayPoint)
        return if (nextWayPoint.isEmpty()) {
            println("怎么可能")
            null!!
        } else {
            val size = nextWayPoint.size
            val random = Random()
            val i = random.nextInt(size)
            nextWayPoint[i]!!
        }
    }
}