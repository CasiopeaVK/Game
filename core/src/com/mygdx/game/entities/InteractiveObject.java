package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.utils.IsoUtils;

public class InteractiveObject extends  Entity{
    private Vector3 lastSpriteWindowPosition = new Vector3(0,0,0);
    public InteractiveObject(World world, Camera camera, String texturePath) {

        super(world, camera, texturePath);
    }

    @Override
    public void update() {
        updateClickListener();
    }

    protected void updateClickListener(){
        Vector3 spriteWindowPosition = camera.project(new Vector3(sprite.getX(),sprite.getY(),0));
        if(!IsoUtils.Vector3Equals(spriteWindowPosition,lastSpriteWindowPosition)){
            this.setBounds(spriteWindowPosition.x,spriteWindowPosition.y,sprite.getWidth(),sprite.getHeight());
            this.setTouchable(Touchable.enabled);
            this.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    System.out.println("lol");
                }
            });
        }
        lastSpriteWindowPosition = spriteWindowPosition;
    }

}
