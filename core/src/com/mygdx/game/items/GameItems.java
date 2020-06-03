package com.mygdx.game.items;


import lombok.Getter;

public enum GameItems {
    SPOON("spoon"), DIRT("dirt"), FORK("fork"), HAMER("hamer"),
    PLATE("plate"), PLATE_FOOD("plateFood"), PLUNGER("plunger"),
    SCREWDRIVER("screwdriver"), SYPRINGE("sypringe");
        
    @Getter
    private String name;

    GameItems(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.name + ".png";
    }
}
