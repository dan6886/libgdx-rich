package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.entity.ConfirmResult;
import com.mygdx.game.handler.ResultReporter;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class UIStage extends Stage {
    BitmapFont bitmapFont;
    Skin skin;
    Label.LabelStyle labelStyle;

    public UIStage(Viewport viewport) {
        super(viewport);
        init();

    }

    private void init() {
        skin = MainGame.Instance.skin;

        bitmapFont = new BitmapFont(Gdx.files.internal("font/cn.fnt"), Gdx.files.internal("font/cn.png"), false);


        labelStyle = new Label.LabelStyle(bitmapFont, Color.BLUE);

        Label label = new Label("你是否愿意支付500元", MainGame.Instance.skin);
        label.setStyle(labelStyle);
        label.setSize(100, 300);
        label.setPosition(0, 0);

        Button button1 = new Button(MainGame.Instance.skin);
        button1.setSize(100, 300);


        SplitPane splitPane = new SplitPane(label, button1, true, MainGame.Instance.skin);

        splitPane.setSize(100, MainGame.Instance.height);
        splitPane.setPosition(300, 0);
        addActor(splitPane);
    }

    public void showTipsWindow(String tips, ResultReporter<Object> reporter) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MessageWindow messageWindow = new MessageWindow("tips", skin, labelStyle);
                messageWindow.setMessage(tips);
                UIStage.this.addActor(messageWindow);
                messageWindow.setZIndex(2);
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

    public void showConfirmWindow(String text, ResultReporter<ConfirmResult> reporter) {
        PublishSubject<ConfirmResult> subject = PublishSubject.create();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                System.out.println("主循环" + Thread.currentThread().getName());
                ConfirmWindow tips = new ConfirmWindow("tips", skin,labelStyle, subject);
                tips.setText(text);
                UIStage.this.addActor(tips);
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

}
