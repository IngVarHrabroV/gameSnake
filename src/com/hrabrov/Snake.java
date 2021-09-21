package com.hrabrov;

public class Snake {
    private int bodyParts = 6;
    private char direction = 'R';

    public int getBodyParts() {
        return bodyParts;
    }
    public void setBodyParts(int bodyParts) {
        this.bodyParts = bodyParts;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }



    int applesEaten;
}
