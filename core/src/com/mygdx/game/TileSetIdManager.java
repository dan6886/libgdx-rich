package com.mygdx.game;

import com.badlogic.gdx.maps.MapProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TileSetIdManager {

    private Map<String, Map<String, Integer>> player = new HashMap<>();
    private Map<String, Integer> house = new HashMap<>();

    public TileSetIdManager() {
        init();
    }

    public void init() {
        Map<String, Integer> red = player.computeIfAbsent("red", s -> new HashMap<>());
        red.put("big_land", 68);
        red.put("small_land", 63);

        house.put("house_1", 11);
        house.put("house_2", 12);
        house.put("house_3", 13);
        house.put("house_4", 14);
        house.put("house_5", 15);
    }

    public int getHouseTileId(String key) {
        return house.get(key);
    }

    public int getLandBaseId(String playerName, String type) {
        return player.get(playerName).get(type + "_land");
    }
}
