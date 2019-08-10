package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.entity.ConfirmResult;
import io.reactivex.subjects.PublishSubject;

public class MessageWindow extends Window {
    private Skin skin;
    private String message = "";
    private Label label;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.label.setText(message);
    }


    public void startDismiss(Runnable run) {
        DelayAction delay = Actions.delay(2, Actions.run(run));
        addAction(delay);
    }

    public MessageWindow(String title, Skin skin) {
        super(title, skin);
        this.skin = skin;
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

        label = new Label("i am rich", skin);
        label.setX(5);
        label.setY(0);
        label.setColor(Color.RED);
        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
        addActor(label);
    }
}
