package com.rich.diy.game.handler

class StartWalkHandler : BaseHandler() {
    override fun doHandle(s: HandlerEntity, chain: HandlerChain): HandlerEntity? {
        if (s.player!!.equals("dan")) {
//            s.setParcelData("StopWayHandler");
        }
        println("开始了")
        //        String dotask = dotask();
        println("收到回复")
        return chain.process(s)
    }

    fun dotask(): String? {
        val reporter = ResultReporter<String>()
        //        Main.task1(reporter);
        return reporter.waitReport()
    }
}