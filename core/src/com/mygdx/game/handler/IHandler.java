package com.mygdx.game.handler;

import com.mygdx.game.actions.BaseAction;

public interface IHandler {
    BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity entity, HandlerChain chain);

    <T> T waitAction(BaseAction<T> action, BaseHandler.HandlerEntity entity);

    <T> void justAction(BaseAction<T> action);
}
