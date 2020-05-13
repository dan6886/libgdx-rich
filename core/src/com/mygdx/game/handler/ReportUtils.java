package com.mygdx.game.handler;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReportUtils {
    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static <T> void deley(long deley, ResultReporter<T> reporter, T t) {
        service.schedule(new Runnable() {
            @Override
            public void run() {
                reporter.report(t);
            }
        }, deley, TimeUnit.SECONDS);
    }

    public static <T> T console(String tip, Class<T> c) {
        System.out.println(tip);
        Scanner scan = new Scanner(System.in);
        // 判断是否还有输入
        T result = null;

        if (scan.hasNext()) {
            if (c.equals(String.class)) {
                String r = scan.nextLine();
                result = (T) r;
            } else if (c.equals(Integer.class)) {
                Integer i = scan.nextInt();
                result = (T) i;
            } else if (c.equals(Boolean.class)) {
                Boolean i = scan.nextBoolean();
                result = (T) i;
            }
            System.out.println(result);

        }

        return result;
    }
}
