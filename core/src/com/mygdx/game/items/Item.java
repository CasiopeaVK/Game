package com.mygdx.game.items;

import lombok.Getter;

public class Item {
    public static Item selectedItem;
    @Getter
    String name;
    public Item(String name){
        this.name = name;
    }
}
