package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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

public class MessageWindow extends Window implements InputProcessor {
    private Skin skin;
    private String message = "";
    private Label label;
    DelayAction delay;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.label.setText(message);
    }


    public void startDismiss(Runnable run) {
        delay = Actions.delay(3, Actions.run(new Runnable() {
            @Override
            public void run() {
                InputMultiplexer multiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
                multiplexer.removeProcessor(MessageWindow.this);
                run.run();
            }
        }));
        addAction(delay);
    }

    public MessageWindow(String title, Skin skin) {
        super(title, skin);
        this.skin = skin;
        InputMultiplexer inputProcessor = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputProcessor.addProcessor(0, this);
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
        label.setColor(Color.GREEN);
        // 这个地方用addActor方法，不能使用add方法，后面将讲解Table的时候会涉及到
        addActor(label);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * 对话框出现的时候点击屏幕就可以取消自己
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        delay.finish();
        System.out.println("点击了事件");
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
