package com.mygdx.game.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class SmartStage extends Stage {
    List<Entity> entityList = new ArrayList<>();
    public void update(){
        act();
        for(Entity entity:entityList){
           entity.update();
        }
        draw();
    }

    public void addEntity(Entity entity){
        addActor(entity);
        entityList.add(entity);
    }
}
