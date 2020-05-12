package com.mygdx.game.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerChain {
    private List<BaseHandler> chians = new ArrayList<>();
    private int index = 0;
    private ExecutorService service = Executors.newCachedThreadPool();

    public HandlerChain addHandler(BaseHandler IHandler) {
        chians.add(IHandler);
        return this;
    }

    public void start(BaseHandler.HandlerEntity entity, Runnable run) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                process(entity);
                System.out.println("任务链执行完毕");
                // 触发回调
                run.run();
            }
        });
    }

    public BaseHandler.HandlerEntity process(BaseHandler.HandlerEntity entity) {
        if (index >= chians.size()) {
            return entity;
        } else {
            BaseHandler IHandler = chians.get(index);
            index++;
            ParcelData target = entity.getParcelData();
            if (IHandler.isNeedHandle(target.getTargetHandlerName())) {
                System.out.println("执行" + IHandler.getClass().getSimpleName());
                BaseHandler.HandlerEntity result = IHandler.doHandle(entity, this);
                return result;
            } else {
                return process(entity);
            }
        }
    }


    public void reset() {
        index = 0;
    }

}
