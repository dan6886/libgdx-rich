package com.rich.diy.game

import java.util.*

class TileSetIdManager private constructor() {
    private val player: MutableMap<String?, MutableMap<String, Int>> = HashMap()
    private val house: MutableMap<String, Int> = HashMap()
    fun init() {
        val red = player.computeIfAbsent("red") { s: String? -> HashMap() }
        red["big_land"] = 68
        red["small_land"] = 63
        //todo 这里注意，在editor里面看到的id值要在这里 +1，比如editor里面的资源id为1，这里就要配置为2才能对应上
        house["house_1"] = 11
        house["house_2"] = 12
        house["house_3"] = 13
        house["house_4"] = 14
        house["house_5"] = 15

        house["tv_1"] = 17
        house["tv_2"] = 18
        house["tv_3"] = 19
        house["tv_4"] = 20
        house["tv_5"] = 21
    }

    fun getHouseTileId(key: String): Int {
        return house[key]!!
    }

    fun getLandBaseId(playerName: String?, type: String): Int {
        return player[playerName]!![type + "_land"]!!
    }

    init {
        init()
    }

    companion object {
        private val INSTANCE: TileSetIdManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            TileSetIdManager()
        }

        fun get(): TileSetIdManager {
            return INSTANCE
        }
    }
}