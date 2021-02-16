package com.rich.diy.game

import java.util.*

class TileSetIdManager private constructor() {
    private val player: MutableMap<String?, MutableMap<String, Int>> = HashMap()
    private val house: MutableMap<String, Int> = HashMap()
    fun init() {
        val red = player.computeIfAbsent("red") { s: String? -> HashMap() }
        red["big_land"] = 68
        red["small_land"] = 63
        house["house_1"] = 11
        house["house_2"] = 12
        house["house_3"] = 13
        house["house_4"] = 14
        house["house_5"] = 15
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