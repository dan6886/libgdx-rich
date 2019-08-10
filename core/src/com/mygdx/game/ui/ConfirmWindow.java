package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.entity.ConfirmResult;
import io.reactivex.subjects.PublishSubject;

public class ConfirmWindow extends Window {
    private Skin skin;
    private PublishSubject<ConfirmResult> subject;
    private int type;
    private Label label;
    private String text;

    public ConfirmWindow(String title, Skin skin) {
        super(title, skin);
    }

    public ConfirmWindow(String title, Skin skin, PublishSubject<ConfirmResult> subject, int type) {
        super(title, skin);
        this.skin = skin;
        this.subject = subject;
        this.type = type;
        init();
    }

    private void init() {
        setSize(220, 180);
        // 默认文字是在左边显示，需要手动设置居中
        getTitleLabel().setAlignment(Align.center);
        // 默认window的位置是在左下角，需重新设置
        setX(Gdx.graphics.getWidth() / 2 - getWidth() / 2);
        setY(Gdx.graphics.getHeight() / 2 - getHeight() / 2);
        // 拖动TitleLabel，window会移动
        setMovable(true);

        label = new Label("但是\n222\n333\n444\n\n\n\n", skin);
        label.setX(5);
        label.setY(0);
        label.setColor(Color.RED);
        TextButton tbOk = new TextButton("OK", skin);
        TextButton tbCancel = new TextButton("CANCEL", skin);
        tbOk.setSize(tbCancel.getPrefWidth(), tbCancel.getPrefHeight());
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TAG", "dialog cancel button is clicked");
                subject.onNext(new ConfirmResult(type).setOk(false));
                subject.onComplete();
                ConfirmWindow.this.remove();
            }

        });
        tbOk.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TAG", "dialog ok button is clicked");
                subject.onNext(new ConfirmResult(type).setOk(true));
                subject.onComplete();
                ConfirmWindow.this.remove();
            }

        });
        tbOk.setX(getWidth() / 2 - tbOk.getWidth() - 10);
        tbOk.setY(10);
        tbCancel.setX(getWidth() / 2 + 10);
        tbCancel.setY(10);
        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
        addActor(label);
        addActor(tbOk);
        addActor(tbCancel);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        label.setText(text);
    }
}
