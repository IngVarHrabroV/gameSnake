package com.hrabrov;

public class Board {
    private final int screenWidthSize = 600;
    private final int screenHeightSize = 600;
    private final int unitSize = 25;
    private final int gameUnits = (screenWidthSize * screenHeightSize) / ((int) Math.pow(unitSize, 2));

    public int getScreenWidthSize() {
        return screenWidthSize;
    }

    public int getScreenHeightSize() {
        return screenHeightSize;
    }

    public int getUnitSize() {
        return unitSize;
    }

    public int getGameUnits() {
        return gameUnits;
    }
}
