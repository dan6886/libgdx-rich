package com.mygdx.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIStage extends Stage  {
    public UIStage(Viewport viewport) {
        super(viewport);
    }
    private void init() {
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Actor listenerActor = event.getListenerActor();
                System.out.println(listenerActor.getClass().getName());
                super.clicked(event, x, y);
            }
        });
    }

}
