package com.mygdx.game.stage;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class SmartStage extends Stage {
    List<Entity> entityList = new ArrayList<>();
    public void act(Camera camera){
        act();
        for(Entity entity:entityList){
           entity.update(camera);
        }
    }

    public void addEntity(Entity entity){
        addActor(entity);
        entityList.add(entity);
    }
}
