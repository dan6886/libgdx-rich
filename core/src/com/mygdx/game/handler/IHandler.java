package com.mygdx.game.handler;

public interface IHandler {
    BaseHandler.HandlerEntity doHandle(BaseHandler.HandlerEntity s, HandlerChain chain);
}
