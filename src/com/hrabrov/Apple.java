package com.hrabrov;

import java.util.Random;

public class Apple {

    Apple(int SCREEN_WIDTH_SIZE, int SCREEN_HEIGHT_SIZE, int UNIT_SIZE) {
        this.SCREEN_WIDTH_SIZE = SCREEN_WIDTH_SIZE;
        this.SCREEN_HEIGHT_SIZE = SCREEN_HEIGHT_SIZE;
        this.UNIT_SIZE = UNIT_SIZE;
    }

    private int applePositionX;
    private int applePositionY;
    private final int SCREEN_WIDTH_SIZE;
    private final int SCREEN_HEIGHT_SIZE;
    private final int UNIT_SIZE;

    public int getApplePositionX() {
        return applePositionX;
    }

    public int getAppleY() {
        return applePositionY;
    }

    Random random = new Random();
    public void getPosition() {
        applePositionX = random.nextInt((int) (SCREEN_WIDTH_SIZE / UNIT_SIZE )) * UNIT_SIZE;
        applePositionY = random.nextInt((int) (SCREEN_HEIGHT_SIZE / UNIT_SIZE )) * UNIT_SIZE;
    }
}

