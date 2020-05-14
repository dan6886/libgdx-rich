package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UnluckyGod extends God {
    public UnluckyGod() {
        super(new TextureRegion(new Texture("actors/god_unlucky.png")), GOD_UNLUCKY, "Land UnLucky");
    }


    @Override
    void doShowMagic() {

    }
}
