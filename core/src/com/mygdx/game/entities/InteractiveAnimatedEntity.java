package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.utils.IsoUtils;

import static com.mygdx.game.Constants.PLAYER_SPEED;

public abstract class InteractiveAnimatedEntity extends AnimatedEntity {
    protected Stage stage;

    public InteractiveAnimatedEntity(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
    }

    public void update(float speed) {
        super.update(() -> {});
        updateClickListener();
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        body.setLinearVelocity(IsoUtils.IsoTo2d(new Vector2(xFactor * speed * Gdx.graphics.getDeltaTime(), yFactor * speed * Gdx.graphics.getDeltaTime())));
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2 - 2, body.getPosition().y - sprite.getWidth() / 2 + 10);
    }
    public void praUpdate(){
        world.step(Gdx.graphics.getDeltaTime(), 6, 6);
        super.update(() -> {});
    }


    protected void updateClickListener() {
        setBounds();
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick(event, x, y);
            }
        });

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    abstract protected void onClick(InputEvent event, float x, float y);

    private void setBounds() {
        Vector3 spriteWindowCorners = camera.project(new Vector3(sprite.getX() + getWidth(), sprite.getY() + getHeight(), 0));
        Vector3 spriteWindowPosition = camera.project(new Vector3(sprite.getX(), sprite.getY(), 0));
        Vector2 spriteWindowSize = new Vector2(spriteWindowCorners.x - spriteWindowPosition.x, spriteWindowCorners.y - spriteWindowPosition.y);
        super.setBounds(spriteWindowPosition.x, spriteWindowPosition.y + spriteWindowSize.y * Constants.UNIT_SCALE, spriteWindowSize.x, spriteWindowSize.y);
    }
}
