package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.mygdx.game.MainGame;
import com.mygdx.game.handler.ResultReporter;


public abstract class God extends Actor {

    public static final int GOD_LAND = 4;
    public static final int GOD_UNLUCKY = 1;
    private int type = -1;

    private String name = "";

    private int activeDays = 7;

    protected Actor1 master = null;
    TextureRegion region;

    public God(TextureRegion region, int type, String name) {
        this.region = region;
        this.type = type;
        this.name = name;
        setWidth(region.getRegionWidth());
        setHeight(region.getRegionHeight());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                region,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation()
        );

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void showMagic() {
        //todo show
        doShowMagic();
    }

    abstract void doShowMagic();

    protected void decreaseActiveDays() {
        activeDays--;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getActiveDays() {
        return activeDays;
    }

    public Actor1 getMaster() {
        return master;
    }

    public void setMaster(Actor1 master) {
        this.master = master;
    }

    public void active(ResultReporter reporter) {
        System.out.println("center:"+MainGame.Instance.getCenterPositionFromCamera());

        Vector2 centerPositionFromCamera = MainGame.Instance.getCenterPositionFromCamera();
        MoveToAction moveToAction = Actions.moveTo(centerPositionFromCamera.x, centerPositionFromCamera.y, 1);
        ScaleByAction scaleByAction = Actions.scaleBy(0.5f, 0.5f, 1);
        ParallelAction parallel = Actions.parallel(moveToAction, scaleByAction);
        RepeatAction repeat = Actions.repeat(3,
                Actions.sequence(
                        Actions.moveBy(0, -50, 0.5f),
                        Actions.moveBy(0, 50, 0.5f)));

        addAction(Actions.sequence(parallel, repeat, Actions.run(new Runnable() {
            @Override
            public void run() {
                God.this.remove();
                God.this.setScale(0.5f);
                master.addActor(God.this);
                master.setGod(God.this);
                God.this.setPosition(0, master.getHeight());
                MainGame.Instance.showTipsWindow("the " + God.this.getName() + " is coming", reporter);
            }
        })));
    }

    public void deActive(ResultReporter reporter) {
        MoveByAction moveByAction = Actions.moveBy(0, Gdx.graphics.getHeight(), 2);
        addAction(Actions.sequence(moveByAction, Actions.run(new Runnable() {
            @Override
            public void run() {
                God.this.remove();
                master = null;
                System.out.println("remove god");
                reporter.report(new Object());
            }
        })));
    }
}
