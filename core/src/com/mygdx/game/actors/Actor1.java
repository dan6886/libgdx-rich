package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.handler.ResultReporter;

public class Actor1 extends Group {
    private TextureRegion region;
    private WayPoint current;
    private WayPoint pre;
    private String name = "";
    private int state = 1;
    private int cash = 10000;
    private God god;

    public Actor1(String name, Texture texture) {
        region = new TextureRegion(texture, 0, 0, 20, 20);
        this.name = name;
        setWidth(region.getRegionWidth());
        setHeight(region.getRegionHeight());
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public God getGod() {
        return god;
    }

    public void setGod(God god) {
        this.god = god;
    }

    public boolean hasGod(int type) {
        if (null == god) {
            return false;
        }
        return god.getType() == type;
    }

    public void startWalk(ResultReporter<WayPoint> reporter) {
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
        point.setOwner(this);
        payMoney(point.getPrice());
        System.out.println("购买了土地" + point.toString());
    }

    public void buyCurrentLand() {
        LandPoint landPoint = getCurrent().getLandPoint();
        if (null == landPoint) {
            throw new IllegalStateException("try to buy null land");
        }
        build(landPoint);
    }

    public void build(LandPoint point) {
        payMoney(point.getBuildPrice());
        point.buildUp();
        System.out.println("支付升级费:" + point.toString());
    }

    public void pay(LandPoint point) {
        Actor1 owner = point.getOwner();
        int price = point.getPrice();
        payMoney(price);
        owner.addMoney(price);
        System.out.println("支付过路费:" + point.toString());
    }

    public void addMoney(int money) {
        cash += money;
    }

    public void payMoney(int money) {
        cash -= money;
    }

    public void newday() {
        if (god != null) {
            god.decreaseActiveDays();
            if (god.getActiveDays() == 0) {
                ResultReporter<Object> reporter = new ResultReporter<>();
                god.deActive(reporter);
                god = null;
                reporter.waitReport();
            }
        }

    }

    @Override
    public String toString() {
        return "Actor1{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", cash=" + cash +
                '}';
    }
}
