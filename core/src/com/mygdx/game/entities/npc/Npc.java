package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Constants;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.entities.InteractiveAnimatedEntity;
import com.mygdx.game.entities.InteractiveEntity;
import com.mygdx.game.map.Map;
import com.sun.tools.javac.util.Pair;

import java.util.*;

public class Npc extends InteractiveAnimatedEntity {
    Path path;
    public Npc(World world, Map map, Camera camera, String texturePath, String markerName, String pathName) {
        super(world, camera, texturePath);
        initialize(map,markerName,pathName);
        xFactor = 1;
    }

    private void initialize(Map map, String markerName, String pathName){
        spriteScale = 0.6f;
        sprite.setScale(spriteScale);
        calculateSpawnPosition(map,markerName);
        path = new Path(map,pathName);
        initCharacterBody(BodyDef.BodyType.KinematicBody);
    }

    @Override
    public void update() {
        update(Constants.PLAYER_LOW_SPEED);
        if(TimeManager.getMinutes()%5==0){
            xFactor=-xFactor;
            //System.out.println(xFactor);
        }
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        Skin uiSkin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
        Dialog dialog = new Dialog("Warning", uiSkin, "default") {
            public void result(Object obj) {
                //System.out.println("result "+obj);
            }
        };
        dialog.text("Test Message");
        dialog.button("Yes", "true"); //sends "true" as the result
        dialog.button("No", "false"); //sends "false" as the result
        dialog.show(stage);
    }


}
