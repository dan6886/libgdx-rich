package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;

public class GameStage extends Stage {
    public static final int STATE_IDLE = 1;
    public static final int STATE_RUN = 2;

    private int state = STATE_IDLE;

    public GameStage(Viewport viewport) {
        super(viewport);
        init();

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private void init() {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor relatedActor = event.getListenerActor();
                System.out.println(relatedActor.toString());
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        prevDragX = screenX;
        prevDragY = screenY;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private boolean touchIn = false;
    private float prevDragX = 0;
    private float prevDragY = 0;
    private boolean moved = false;

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float deltaX = 0;
        float deltaY = 0;
        if (prevDragX != screenX || prevDragY != screenY) {
            moved = true;
        }
        deltaX = screenX - prevDragX;
        deltaY = screenY - prevDragY;
        System.out.println("GameStage dragged " + screenX + "|" + screenY);
        Camera camera = getCamera();
        camera.translate(-deltaX / MainGame.FACTOR, deltaY / MainGame.FACTOR, 0);
        System.out.println(camera.position.x + "|" + camera.position.y);

        prevDragX = screenX;
        prevDragY = screenY;

        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
