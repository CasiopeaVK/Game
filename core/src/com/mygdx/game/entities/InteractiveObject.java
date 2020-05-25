package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.utils.IsoUtils;

abstract public class InteractiveObject extends Entity {
    protected Stage stage;

    private Vector3 lastSpriteWindowPosition = new Vector3(0, 0, 0);

    public InteractiveObject(World world, Camera camera, String texturePath) {

        super(world, camera, texturePath);
    }

    @Override
    public void update() {
        updateClickListener();
    }


    protected void updateClickListener() {
        setBounds();
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick(event,x,y);
            }
        });

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    abstract protected void onClick(InputEvent event, float x, float y);

    public void setBounds() {
        Vector3 spriteWindowCorners = camera.project(new Vector3(sprite.getX() + getWidth(), sprite.getY() + getHeight(), 0));
        Vector3 spriteWindowPosition = camera.project(new Vector3(sprite.getX(), sprite.getY(), 0));
        Vector2 spriteWindowSize = new Vector2(spriteWindowCorners.x - spriteWindowPosition.x, spriteWindowCorners.y - spriteWindowPosition.y);
        super.setBounds(spriteWindowPosition.x, spriteWindowPosition.y + spriteWindowSize.y * Constants.UNIT_SCALE, spriteWindowSize.x, spriteWindowSize.y);

    }
}
