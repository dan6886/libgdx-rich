package com.mygdx.game.handler;

public interface IHandler {
    BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity entity, HandlerChain chain);

    <T> T waitAction(BaseAction<T> action, BaseHandler.HandlerEntity entity);

    <T> void justAction(BaseAction<T> action);
}
