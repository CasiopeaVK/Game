package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.utils.IsoUtils;

import java.awt.event.MouseEvent;

abstract public class InteractiveEntity extends Entity {
    protected Stage stage;

    public InteractiveEntity(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
    }

    public InteractiveEntity(World world, Camera camera, Sprite sprite){
        super(world, camera, sprite);
    }

    public void update(float speed) {
        updateClickListener();

    }

    protected void updateClickListener() {
        setBounds();
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Vector2 distance = new Vector2(Gdx.input.getX()-Gdx.graphics.getWidth()/2,Gdx.input.getY()-Gdx.graphics.getHeight()/2);
                double distanceLength = IsoUtils.CalculateVector2Length(distance);
                if(distanceLength<Constants.INTERACTION_DISTANCE){
                    onClick(event,x,y);
                }

            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    abstract protected void onClick(InputEvent event, float x, float y);

    protected void setBounds() {
        Vector3 spriteWindowCorners = camera.project(new Vector3(sprite.getX() + getWidth(), sprite.getY() + getHeight(), 0));
        Vector3 spriteWindowPosition = camera.project(new Vector3(sprite.getX(), sprite.getY(), 0));
        Vector2 spriteWindowSize = new Vector2(spriteWindowCorners.x - spriteWindowPosition.x, spriteWindowCorners.y - spriteWindowPosition.y);
        super.setBounds(spriteWindowPosition.x, spriteWindowPosition.y + spriteWindowSize.y * Constants.UNIT_SCALE, spriteWindowSize.x, spriteWindowSize.y);
    }
}
