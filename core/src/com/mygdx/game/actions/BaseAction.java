package com.mygdx.game.actions;

import com.mygdx.game.handler.BaseHandler;

public abstract class BaseAction<T> {

    public void justAction() {

    }

    public abstract T doAction(BaseHandler.HandlerEntity entity);

    public <S> S waitAction(BaseAction<S> action, BaseHandler.HandlerEntity entity) {
        return action.doAction(entity);
    }
}
