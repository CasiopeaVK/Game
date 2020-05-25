package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entities.InteractiveObject;
import com.mygdx.game.map.Map;

public class TestNPC extends InteractiveObject {
    public TestNPC(World world, Map map, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        initialize(map);
    }

    private void initialize(Map map){
        spriteScale = 0.6f;
        sprite.setScale(spriteScale);
        calculateSpawnPosition(map,"testNpc");
        initCharacterBody(BodyDef.BodyType.StaticBody);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        Skin uiSkin = new Skin(Gdx.files.internal("flat-earth/flat-earth-ui.json"));
        Dialog dialog = new Dialog("Warning", uiSkin, "default") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.text("Test Message");
        dialog.button("Yes", "true"); //sends "true" as the result
        dialog.button("No", "false"); //sends "false" as the result
        dialog.show(stage);
    }
}
