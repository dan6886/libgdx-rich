package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entity.*;
import com.mygdx.game.events.BaseEvent;
import com.mygdx.game.handler.*;
import com.mygdx.game.ui.ConfirmWindow;
import com.mygdx.game.ui.MessageWindow;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

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
    private WayPoint[][] wayPointArray = null;
    private LandPoint[][] landPointArray = null;

    private Skin skin;
    private TiledMapTileLayer landbase;
    private TiledMapTileLayer landBuilding;
    private TileSetIdManager tileSetIdManager;
    TiledMapTileSets tileSets;
    HandlerChain handlerChain = new HandlerChain();
    private TextButton button;

    @Override
    public void create() {
        Instance = this;
        tileSetIdManager = new TileSetIdManager();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        camera = new OrthographicCamera();
        camera.position.set(width / 2, height / 2, 0);

        camera.update();
        loader = new TmxMapLoader();
        map = loader.load("110.tmx");

        Constants.ROW_NUM = map.getProperties().get("height", Integer.class);
        Constants.COL_NUM = map.getProperties().get("width", Integer.class);
        wayPointArray = new WayPoint[Constants.ROW_NUM][Constants.COL_NUM];
        landPointArray = new LandPoint[Constants.ROW_NUM][Constants.COL_NUM];
        viewport = new FitViewport(640, 480, camera);
        renderer = new OrthogonalTiledMapRenderer(map);
        actor1 = new Actor1("red", img);
        stage = new Stage(viewport);
        stage.addActor(actor1);
        init();
        actor1.setCurrent(wayPointArray[0][0]);
        actor1.setPre(wayPointArray[0][0]);
        button = new TextButton("start", skin);
        button.setX(400);
        button.setY(100);
        stage.addActor(button);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
        prepareWalk();
    }

    private void prepareWalk() {
        handlerChain.reset();
        BaseHandler.HandlerEntity entity = new BaseHandler.HandlerEntity();
        entity.setPlayer(actor1);
        handlerChain.start(entity, new Runnable() {
            @Override
            public void run() {
                System.out.println("完成回调:" + actor1.toString());
                prepareWalk();
            }
        });
        System.out.println("执行完毕了吗?");
    }

    private void init() {
        // 开始前检测
        handlerChain.addHandler(new CheckStartWalkHandler());
        // 开始行走
        handlerChain.addHandler(new StartWalkHandler());
        // 路过，每一步的处理
        handlerChain.addHandler(new PassHandler());
        // 停下 踩到路点
        handlerChain.addHandler(new StopWayHandler());
        // 这里不需要这几个handler了 因为不是固定的，这几个处理会在 action里面
//        handlerChain.addHandler(new BuyLandHandler());
//        handlerChain.addHandler(new BuildLandHandler());
//        handlerChain.addHandler(new PayLandHandler());

        handlerChain.addHandler(new FinishRoundHandler());

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get("ground");
        landbase = (TiledMapTileLayer) map.getLayers().get("landbase");
        landBuilding = (TiledMapTileLayer) map.getLayers().get("land");

        tileSets = map.getTileSets();
        TiledMapTile tile = tileSets.getTile(61);
//        mapLayer.getCell(0, 0).setTile(tile);
//        landbase.getCell(1, 1).setTile(tile);
        MapObjects landpoint = map.getLayers().get("landpoint").getObjects();
        for (MapObject object : landpoint) {
            Float x = object.getProperties().get("x", Float.class);
            Float y = object.getProperties().get("y", Float.class);
            String type = object.getProperties().get("type", String.class);
            int col = (int) (x / cell_width);
            int row = (int) (y / cell_height);
            LandPoint point = new LandPoint(object, row, col, x.intValue(), y.intValue());
            point.setType(type);
            landPointArray[row][col] = point;
            System.out.println(x + "|" + y + "|col:" + col + "row:" + row);
        }

        MapObjects waypoints = map.getLayers().get("waypoint").getObjects();
        for (MapObject object : waypoints) {
            Float x = object.getProperties().get("x", Float.class);
            Float y = object.getProperties().get("y", Float.class);
            int col = (int) (x / cell_width);
            int row = (int) (y / cell_height);
            Integer land_row = object.getProperties().get("land_row", Integer.class);
            Integer land_col = object.getProperties().get("land_col", Integer.class);

//          LandPoint related = landPointArray[land_row][land_col];
            //测试代码，需删除
            LandPoint related = landPointArray[1][1];
            related.setOwner(new Actor1("dd", img));
            if (null == related) {
                related = LandPoint.NOTHINIG;
            }

            wayPointArray[row][col] = new WayPoint(object, row, col, x.intValue(), y.intValue(), related);
            System.out.println("waypoint" + x + "|" + y + "|col:" + col + "row:" + row);
        }

//        showWindow(null);
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

    public void doWalk(Actor1 player, ResultReporter<WayPoint> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                player.startWalk(reporter);
            }
        });
    }

    @Deprecated
    public void passWayPoint(Actor1 player, WayPoint point, BaseEvent.ResultWaiter<Object> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("经过了" + point.toString());
                reporter.report(point);
            }
        });
    }

    @Deprecated
    public void stopAt(Actor1 player, WayPoint point, BaseEvent.ResultWaiter<WayPoint> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                //检测路点有没有礼物
                System.out.println("停下了：" + point.toString());
                reporter.report(point);
            }
        });
    }

    /**
     * 这个地方应该还是在单独线程里面执行，所以需要提交任务到主循环线程
     *
     * @param player
     * @param point
     * @param reporter
     */
    @Deprecated
    public void consumeOrInvest(Actor1 player, LandPoint point, BaseEvent.ResultWaiter<LandPoint> reporter) {

        Observable.just("开始奇怪的代码")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        System.out.println(s);
                        if (point.isNothing()) {
                            return false;
                        }
                        return true;
                    }
                }).flatMap(new Function<String, ObservableSource<ConfirmResult>>() {
            @Override
            public ObservableSource<ConfirmResult> apply(String s) throws Exception {
                if (point.isBlank()) {
                    return showConfirmWindow(ConfirmResult.EVENT_BUY_LAND, "will want to buy this land?");
                } else if (point.getOwnerName().equals(player.getName())) {
                    return showConfirmWindow(ConfirmResult.EVENT_BUILD_LAND, "will you want to build up this land");
                } else {
                    return showConfirmWindow(ConfirmResult.EVENT_BUILD_LAND, "will you want to build up this land");
                }
            }
        }).flatMap(new Function<ConfirmResult, ObservableSource<BaseResult>>() {
            @Override
            public ObservableSource<BaseResult> apply(ConfirmResult confirmResult) throws Exception {
                System.out.println("计算+修改地图动画" + Thread.currentThread().getName());
                // 计算+修改地图动画
                BaseResult result = null;
                switch (confirmResult.getType()) {
                    case ConfirmResult.EVENT_BUY_LAND:
                        if (confirmResult.isOk()) {
                            player.buy(point);
                            BaseEvent.ResultWaiter<BuyResult> waiter1 = new BaseEvent.ResultWaiter<>();
//                            buyLand(player, waiter1);// 这里提交动画到主线程执行，并且等待结果
                            result = waiter1.waitReport();
                            return buySuccess(result);
                        } else {
                            return buyCancel(confirmResult);
                        }
                    case ConfirmResult.EVENT_BUILD_LAND:
                        BaseEvent.ResultWaiter<BuildResult> waiter2 = new BaseEvent.ResultWaiter<>();
                        buildLand(waiter2);// 这里提交到主线程执行
                        result = waiter2.waitReport();
                        break;
                    case ConfirmResult.EVENT_PAY_LAND:
                        BaseEvent.ResultWaiter<PayResult> waiter3 = new BaseEvent.ResultWaiter<>();
                        payLand(waiter3);// 这里提交到主线程执行
                        result = waiter3.waitReport();
                        break;
                }
                return Observable.just(result);
            }

        }).map(new Function<BaseResult, String>() {
            @Override
            public String apply(BaseResult baseResult) throws Exception {
                // 操作成功，玩家可以发言
                System.out.println("对建筑操作完成" + baseResult);

                return "";
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("完毕了" + Thread.currentThread().getName());
                        reporter.report(point);
                    }
                });
    }

    @Deprecated
    private ObservableSource<BaseResult> buySuccess(BaseResult result) {
        return Observable.just(result).map(new Function<BaseResult, BaseResult>() {
            @Override
            public BaseResult apply(BaseResult baseResult) throws Exception {
                // 操作成功，玩家可以发言
                System.out.println("操作成功，玩家可以发言" + Thread.currentThread().getName());
                switch (baseResult.getType()) {
                    case ConfirmResult.EVENT_BUY_LAND:
                        // 玩家自己买了说话
                        BaseEvent.ResultWaiter<Object> waiter = new BaseEvent.ResultWaiter<>();
                        playerPrideBuy(waiter);
                        waiter.waitReport();
                        break;
                    case ConfirmResult.EVENT_BUILD_LAND:
                        // 玩家自己建了说话
                        BaseEvent.ResultWaiter<Object> waiter2 = new BaseEvent.ResultWaiter<>();
                        playerPrideBuilding(waiter2);
                        waiter2.waitReport();
                        break;
                    case ConfirmResult.EVENT_PAY_LAND:
                        // 玩家自己付钱了
                        BaseEvent.ResultWaiter<Object> waiter3 = new BaseEvent.ResultWaiter<>();
                        playerEarnMoney(waiter3);
                        waiter3.waitReport();
                        if (false) {
                            // 这里如果是巨额，对方还得说话
                        }
                        break;
                }
                return result;
            }

        });
    }

    @Deprecated
    private ObservableSource<BaseResult> buyCancel(BaseResult result) {
        return Observable.just(result);
    }

    @Deprecated
    public ObservableSource<ConfirmResult> showConfirmWindow(int type, String text) {
        PublishSubject<ConfirmResult> subject = PublishSubject.create();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("主循环" + Thread.currentThread().getName());
                ConfirmWindow tips = new ConfirmWindow("tips" + type, skin, subject);
                tips.setText(text);
                stage.addActor(tips);
            }
        });
        //开启线程 showWindow

        return subject.observeOn(Schedulers.newThread());
    }

    @Deprecated
    public ObservableSource<BuildResult> buildLand(BaseEvent.ResultWaiter<BuildResult> waiter) {
        PublishSubject<BuildResult> subject = PublishSubject.create();

        //开启线程 showWindow

        return subject;
    }

    @Deprecated
    public ObservableSource<PayResult> payLand(BaseEvent.ResultWaiter<PayResult> waiter) {
        PublishSubject<PayResult> subject = PublishSubject.create();

        //开启线程 showWindow

        return subject;
    }

    @Deprecated
    public void playerEarnMoney(BaseEvent.ResultWaiter<Object> waiter) {
        //开启线程玩家得意
    }

    @Deprecated
    public void playerPrideBuilding(BaseEvent.ResultWaiter<Object> waiter) {
        //开启线程玩家得意
    }

    @Deprecated
    public void playerPrideBuy(BaseEvent.ResultWaiter<Object> waiter) {
        //开启线程玩家得意

    }

    public void showConfirmWindow(String text, ResultReporter<ConfirmResult> reporter) {
        PublishSubject<ConfirmResult> subject = PublishSubject.create();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("主循环" + Thread.currentThread().getName());
                ConfirmWindow tips = new ConfirmWindow("tips", skin, subject);
                tips.setText(text);
                stage.addActor(tips);
            }
        });
        //开启线程 showWindow

        subject.observeOn(Schedulers.newThread())
                .subscribe(new Consumer<ConfirmResult>() {
                    @Override
                    public void accept(ConfirmResult confirmResult) throws Exception {
                        reporter.report(confirmResult);
                    }
                });

    }

    public void buyLand(Actor1 player, ResultReporter<Object> waiter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                int id = tileSetIdManager.getLandBaseId(player.getName(), player.getCurrent().getLandPoint().getType());
                TiledMapUtils.markTileOwner(landbase, player.getCurrent().getLandPoint(), tileSets, id);
                waiter.report(new Object());
            }
        });
        //开启线程 showWindow
    }

    public void buildUIUp(Actor1 actor, ResultReporter<Object> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                LandPoint landPoint = actor.getCurrent().getLandPoint();
                int houseTileId = tileSetIdManager.getHouseTileId(landPoint.getBuildingTiledName());
                TiledMapUtils.markTileOwner(landBuilding, landPoint, tileSets, houseTileId);
                reporter.report(new Object());
            }
        });
    }


    public void showTipsWindow(String tips, ResultReporter<Object> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MessageWindow messageWindow = new MessageWindow("tips", skin);
                messageWindow.setMessage(tips);
                stage.addActor(messageWindow);
                messageWindow.startDismiss(new Runnable() {
                    @Override
                    public void run() {
                        messageWindow.remove();
                        reporter.report(new Object());
                    }
                });
            }
        });
    }

    public void showStartButton(ResultReporter<Integer> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                button.setVisible(true);
                //这里一定得记得清除上次的监听，否则会引起线程阻塞，上一个reporter 没有释放
                button.clearListeners();
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("消费了点击" + reporter.toString());
                        button.setVisible(false);
                        reporter.report(3);
                    }
                });
            }
        });
    }
}
