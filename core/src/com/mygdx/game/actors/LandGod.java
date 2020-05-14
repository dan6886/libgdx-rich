package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.handler.ResultReporter;

public class LandGod extends God {
    public LandGod() {
        super(new TextureRegion(new Texture("actors/god_land.png")), GOD_LAND, "Land God");
    }

    @Override
    void doShowMagic() {
        if (!master.getCurrent().getLandPoint().isNothing() &&
                !master.getName().equals(master.getCurrent().getLandPoint().getOwnerName())) {
            ResultReporter<Object> reporter = new ResultReporter<>();
            MainGame.Instance.showTipsWindow("god " + getName() + " make the land is yours", reporter);
            LandPoint landPoint = master.getCurrent().getLandPoint();
            landPoint.setOwner(master);
            reporter.waitReport();
            ResultReporter<Object> reporter2 = new ResultReporter<>();
            MainGame.Instance.ownLand(master, reporter2);
            reporter2.waitReport();

            ResultReporter<Object> reporter3 = new ResultReporter<>();
            MainGame.Instance.showTipsWindow("i'm really happy for god " + getName() + " give me the land!!!", reporter3);
            reporter3.waitReport();
        }
    }
}
