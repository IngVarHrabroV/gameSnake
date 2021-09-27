package com.hrabrov;

public class Snake {
    private char direction = 'R';

    Snake(int gameUnit) {
        maxSnakeSize = gameUnit;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }



    private int currentSizeOfSnake = 6;

    private int maxSnakeSize;


    public int getCurrentSizeOfSnake() {
        return currentSizeOfSnake;
    }

    public void setCurrentSizeOfSnake(int currentSizeOfSnake) {
        this.currentSizeOfSnake = currentSizeOfSnake;
    }


    public void setMaxSnakeSize(int maxSnakeSize) {
        this.maxSnakeSize = maxSnakeSize;
    }


    int applesEaten;
}
