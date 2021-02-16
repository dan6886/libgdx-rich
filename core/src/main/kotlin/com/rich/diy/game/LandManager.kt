package com.rich.diy.game

import com.badlogic.gdx.maps.MapObjects
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileSets
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.rich.diy.game.entity.LandPoint
import com.rich.diy.game.entity.WayPoint

class LandManager private constructor() {
    lateinit var path: String

    /**
     * 从下往上 row 0-->10
     * 从左到右 col 0-->10
     */
    lateinit var wayPointArray: Array<Array<WayPoint?>>
    lateinit var landPointArray: Array<Array<LandPoint?>>
    private val tiledLayers: MutableMap<String, TiledMapTileLayer> = HashMap()
    val loader = TmxMapLoader()
    lateinit var map: TiledMap
    lateinit var tileSets: TiledMapTileSets
    fun init(path: String) {
        this.path = path
        map = loader.load(path)
        ROW_NUM = map.properties.get<Int>("height", Int::class.java)
        COL_NUM = map.properties.get<Int>("width", Int::class.java)
        wayPointArray = Array<Array<WayPoint?>>(Constants.ROW_NUM) { arrayOfNulls<WayPoint>(Constants.COL_NUM) }
        landPointArray = Array<Array<LandPoint?>>(Constants.ROW_NUM) { arrayOfNulls<LandPoint>(Constants.COL_NUM) }
        parseMap()
    }

    fun parseMap() {
        val mapLayer: TiledMapTileLayer = map.layers.get("ground") as TiledMapTileLayer
        tiledLayers["ground"] = mapLayer
        val landbase = map.layers.get("landbase") as TiledMapTileLayer
        tiledLayers["landbase"] = landbase
        val landBuilding = map.layers.get("land") as TiledMapTileLayer
        tiledLayers["land"] = landBuilding
        tileSets = map.tileSets
        val landPoint: MapObjects = map.layers.get("landpoint").objects
        for (`object` in landPoint) {
            if (`object`.properties.containsKey("test")) {
                println("test")
            }
            val x: Float = `object`.properties.get<Float>("x", Float::class.java)
            val y: Float = `object`.properties.get<Float>("y", Float::class.java)
            val type: String = `object`.properties.get<String>("type", String::class.java)
            //相对editor从左往右
            val col = (x / cell_width).toInt()
            //相对editor从下往上
            val row = (y / cell_height).toInt()
            val point = LandPoint(`object`, row, col, x.toInt(), y.toInt())
            point.type = type
            landPointArray[row][col] = point
            println(x.toString() + "|" + y + "|col:" + col + "row:" + row)
        }
        val waypoints: MapObjects = map.layers.get("waypoint").objects
        for (`object` in waypoints) {
            if (`object`.properties.containsKey("test")) {
                println("test")
            }
            val x: Float = `object`.properties.get<Float>("x", Float::class.java)
            val y: Float = `object`.properties.get<Float>("y", Float::class.java)
            val col = (x / cell_width).toInt()
            val row = (y / cell_height).toInt()
            val land_row: Int = `object`.properties.get<Int>("land_row", Int::class.java)
            val land_col: Int = `object`.properties.get<Int>("land_col", Int::class.java)

            var related = landPointArray[land_row][land_col];

            if (null == related) {
                related = LandPoint.NOTHINIG
            }
            wayPointArray[row][col] = WayPoint(`object`, row, col, x.toInt(), y.toInt(), related)
            println("waypoint" + x + "|" + y + "|col:" + col + "row:" + row)
        }
        println("地图解析完")
    }

    fun getLayerByName(name: String): TiledMapTileLayer {
        return tiledLayers[name]!!
    }

    companion object {
        var ROW_NUM = 10
        var COL_NUM = 10
        var cell_width = 32
        var cell_height = 24
        const val LANDBASE = "landbase"
        const val GROUND = "ground"
        const val LAND = "land"
        private val INSTANCE: LandManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy LandManager()
        }

        fun get(): LandManager {
            return INSTANCE
        }
    }
}