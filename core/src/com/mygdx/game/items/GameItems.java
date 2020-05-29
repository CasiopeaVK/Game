package com.mygdx.game.items;


import lombok.Getter;

public enum GameItems {
    SPOON("spoon"), DIRT("dirt");
        
    @Getter
    private String name;

    GameItems(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.name + ".png";
    }
}
