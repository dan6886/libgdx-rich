package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entity.LandPoint;
import com.mygdx.game.entity.WayPoint;
import com.mygdx.game.events.BaseEvent;
import com.mygdx.game.events.StartWalkEvent;

import java.util.List;
import java.util.Random;

public class MainGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    public static MainGame Instance;
    private Stage stage;
    private OrthographicCamera camera;
    private TmxMapLoader loader;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private int width = 640;
    private int height = 480;
    private Viewport viewport;
    private int cell_width = 32;
    private int cell_height = 24;
    private Actor1 actor1;
    /**
     * 从下往上 row 0-->10
     * 从左到右 col 0-->10
     */
    private WayPoint[][] wayPointArray = new WayPoint[Constants.ROW_NUM][Constants.COL_NUM];

    @Override
    public void create() {
        Instance = this;
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");


        camera = new OrthographicCamera();
        camera.position.set(width / 2, height / 2, 0);

        camera.update();
        loader = new TmxMapLoader();
        map = loader.load("110.tmx");


        viewport = new FitViewport(640, 480, camera);
        renderer = new OrthogonalTiledMapRenderer(map);
        actor1 = new Actor1(img);
        Gdx.input.setInputProcessor(stage);
        stage = new Stage(viewport);
        stage.addActor(actor1);
        init();
        actor1.setCurrent(wayPointArray[0][0]);
        actor1.setPre(wayPointArray[0][0]);
        new StartWalkEvent("startwalk", 3000).happen();
    }

    private void init() {
        MapObjects waypoints = map.getLayers().get("waypoint").getObjects();
        for (MapObject object : waypoints) {
            Float x = object.getProperties().get("x", Float.class);
            Float y = object.getProperties().get("y", Float.class);
            int col = (int) (x / cell_width);
            int row = (int) (y / cell_height);
            wayPointArray[row][col] = new WayPoint(object, row, col, x.intValue(), y.intValue());
            System.out.println(x + "|" + y + "|col:" + col + "row:" + row);
        }
        TiledMapUtils.removeTiled(map, "ground", waypoints.get(11));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView((OrthographicCamera) stage.getCamera());
        renderer.render();
//        batch.begin();
////        batch.draw(img, 0, 0);
//        batch.end();

        stage.draw();
        stage.act();

    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    public void subject(BaseEvent.ResultReporter<Object> reporter) {
        System.out.println("subject");
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("subject");
                showWindow(reporter);
            }
        });
    }

    private void showWindow(BaseEvent.ResultReporter<Object> reporter) {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Window window = new Window("WindowTest", skin);
        // 默认文字是在左边显示，需要手动设置居中
        window.getTitleLabel().setAlignment(Align.center);
        // 默认window的位置是在左下角，需重新设置
        window.setX(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2);
        window.setY(Gdx.graphics.getHeight() / 2 - window.getHeight() / 2);
        // 拖动TitleLabel，window会移动
        window.setMovable(true);

        Label label = new Label(getTips(), skin);
        label.setX(0);
        label.setY(30);
        TextButton tbOk = new TextButton("OK", skin);
        TextButton tbCancel = new TextButton("CANCEL", skin);
        tbOk.setSize(tbCancel.getPrefWidth(), tbCancel.getPrefHeight());
        tbCancel.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TAG", "dialog cancel button is clicked");
            }

        });
        tbOk.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TAG", "dialog ok button is clicked");
                reporter.report(new Object());
            }

        });
        tbOk.setX(window.getPrefWidth() / 2 - tbOk.getWidth() / 2 - 10);
        tbOk.setY(10);
        tbCancel.setX(window.getWidth() / 2 + 10);
        tbCancel.setY(10);
        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
        window.addActor(label);
        window.addActor(tbOk);
        window.addActor(tbCancel);
        Gdx.app.log("TAG", "window preWidth=" + window.getPrefWidth() + "window width=" + window.getWidth());
//      window.pack();

        window.addAction(Actions.forever(Actions.rotateBy(360, 5)));
        stage.addActor(window);

    }

    private String getTips() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hit:").append("\n");

        return sb.toString();
    }

    public WayPoint getNextWayPoint(WayPoint currentWayPoint, WayPoint preWayPoint) {
        List<WayPoint> nextWayPoint =
                TiledMapUtils.findNextWayPoint(wayPointArray, currentWayPoint, preWayPoint);

        if (nextWayPoint.isEmpty()) {
            System.out.println("怎么可能");
            return null;
        } else {
            int size = nextWayPoint.size();
            Random random = new Random();
            int i = random.nextInt(size);
            return nextWayPoint.get(i);
        }
    }

    public void startWalk(BaseEvent.ResultReporter<WayPoint> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                actor1.startWalk(reporter);
            }
        });
    }

    public void passWayPoint(WayPoint point, BaseEvent.ResultReporter<Object> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("经过了" + point.toString());
                reporter.report(point);
            }
        });
    }

    public void stopAt(WayPoint point, BaseEvent.ResultReporter<WayPoint> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("停下了：" + point.toString());
                reporter.report(point);
            }
        });
    }

    public void consumeOrinvest(LandPoint point, BaseEvent.ResultReporter<LandPoint> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("在土地上消费或者投资：" + point.toString());
                reporter.report(point);
            }
        });
    }
}
