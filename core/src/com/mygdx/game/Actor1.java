package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.events.BaseEvent;

public class Actor1 extends Actor {
    private TextureRegion region;
    private WayPoint current;
    private WayPoint pre;
    private String name = "";

    public Actor1(String name, Texture texture) {
        region = new TextureRegion(texture, 0, 0, 20, 20);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void start() {

    }

    public WayPoint getCurrent() {
        return current;
    }

    public void setCurrent(WayPoint current) {
        this.current = current;
    }

    public WayPoint getPre() {
        return pre;
    }

    public void setPre(WayPoint pre) {
        this.pre = pre;
    }

    public void startWalk(BaseEvent.ResultWaiter<WayPoint> reporter) {
        WayPoint nextWayPoint = MainGame.Instance.getNextWayPoint(current, pre);
        walkTo(nextWayPoint, new Runnable() {
            @Override
            public void run() {
                pre = current;
                current = nextWayPoint;
                reporter.report(current);
            }
        });
    }

    private void walkTo(WayPoint point, Runnable callback) {
        MoveToAction move = Actions.moveTo(point.getX(), point.getY(), 0.2f);
        RunnableAction finish = Actions.run(callback);
        addAction(Actions.sequence(move, finish));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(region, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void buy(LandPoint point) {
        point.setOwnerName(getName());
        System.out.println("购买了土地" + point.toString());
    }

    public void build(LandPoint point) {

    }

    public void pay(LandPoint point) {

    }
}
