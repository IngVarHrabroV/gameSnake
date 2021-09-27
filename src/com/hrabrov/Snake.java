package com.hrabrov;

public class Snake {
    Direction direction = Direction.RIGHT;
    int applesEaten;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private int currentSizeOfSnake = 6;

    public int getCurrentSizeOfSnake() {
        return currentSizeOfSnake;
    }

    public void setCurrentSizeOfSnake(int currentSizeOfSnake) {
        this.currentSizeOfSnake = currentSizeOfSnake;
    }
}
