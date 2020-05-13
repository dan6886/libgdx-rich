package com.mygdx.game.handler;

public class Player {

    private int money = 10000;
    private String name = "cxdan";

    public void payMoney(int amount) {
        System.out.println("use:" + amount);
        money -= amount;
    }

    @Override
    public String toString() {
        return "Player{" +
                "money=" + money +
                ", name='" + name + '\'' +
                '}';
    }
}
