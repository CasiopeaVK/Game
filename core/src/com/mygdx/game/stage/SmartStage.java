package com.mygdx.game.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.items.Item;
import com.mygdx.game.screenUI.GameUI;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SmartStage extends Stage {
    private List<Entity> entityList = new ArrayList<>();
    @Setter
    private GameUI gameUI;
    public void update(){
        gameUI.updateTime();
        gameUI.setCurrentCell();
        act();
        for(Entity entity:entityList){
           entity.update();
        }
        draw();
        gameUI.renderSelectedItem(this);
    }
    public void addEntity(Entity entity){
        addActor(entity);
        entityList.add(entity);
    }
    public boolean addItem(Item item){
        return gameUI.addItem(item);
    }
    public int getCurrentQuestIndex(){
        return gameUI.getQuestTable().getQuestLine().getCurrentQuestIndex();
    }
}
