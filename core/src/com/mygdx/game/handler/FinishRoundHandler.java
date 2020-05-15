package com.mygdx.game.handler;

import com.mygdx.game.ui.GameStage;

public class FinishRoundHandler extends BaseHandler {
    @Override
    public HandlerEntity doHandle(HandlerEntity s, HandlerChain chain) {

        GameStage parent = (GameStage) (s.getPlayer().getStage());
        parent.setState(GameStage.STATE_IDLE);
        return s;
    }
}
