package com.mygdx.game.quest;

public class Quest {
    private String name;
    private String description;
    private boolean performed = false;
    private boolean isEnd;

    public Quest(String name, String description, boolean isEnd){
        this.name = name;
        this.description = description;
        this.isEnd = isEnd;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPerformed() {
        return performed;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setPerformed(boolean performed){
        this.performed = performed;
    }
}
