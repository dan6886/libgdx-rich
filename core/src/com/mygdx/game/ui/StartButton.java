package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MainGame;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

public class StartButton extends TextButton implements InputProcessor {

    public StartButton(String text, Skin skin) {
        super(text, skin);
        init();
    }

    private void init() {

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

    private boolean touchIn = false;


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        moved = false;
        prevX = screenX;
        prevY = screenY;
        System.out.println(getOriginX());

        float x = getX();
        float y = getY();
        Rectangle rectangle = new Rectangle(x, y, getWidth(), getHeight());
        Vector2 vector2 = new Vector2(screenX / MainGame.FACTOR, (MainGame.Instance.height - screenY / MainGame.FACTOR));
        boolean contains = rectangle.contains(vector2);
        System.out.println(vector2.toString());
        System.out.println(rectangle.toString());
        if (contains) {
            System.out.println("在里面");
            touchIn = true;
            return false;
        }
        touchIn = false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (moved) {
            return true;
        }
        return false;
    }

    private boolean moved = false;
    private float prevX = 0;
    private float prevY = 0;
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("dragged:x" + screenX + "|y:" + screenX + "|p:" + pointer);
        System.out.println("in:"+touchIn);
        if (touchIn) {
            setPosition(screenX / MainGame.FACTOR - getWidth() / 2, MainGame.Instance.height - screenY / MainGame.FACTOR - getHeight() / 2);
            if (prevX != screenX || prevY != screenY) {
                moved = true;
            }
        }
        return true;
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
