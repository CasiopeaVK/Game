package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class InteractiveObject extends  Entity{


    public InteractiveObject(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
    }

    @Override
    public void update(Camera camera) {
        updateClickListener();
    }

    private void updateClickListener(){
        Vector3 spriteWindowPosition = camera.project(new Vector3(sprite.getX(),sprite.getY(),0));
        this.setBounds(spriteWindowPosition.x,spriteWindowPosition.y,sprite.getWidth(),sprite.getHeight());
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("lol");
            }
        });
    }
}
