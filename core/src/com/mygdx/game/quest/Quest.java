package com.mygdx.game.quest;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Quest {
    private String name;
    private String description;
    @Setter
    private boolean performed = false;
    private boolean isEnd;

    public Quest(String name, String description, boolean isEnd){
        this.name = name;
        this.description = description;
        this.isEnd = isEnd;
    }
}
