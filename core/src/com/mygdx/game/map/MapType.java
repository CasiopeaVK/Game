package com.mygdx.game.map;

public enum MapType {
    MAP_1("water.tmx");

    private final String filePath;

    MapType(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
