package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.utils.IsoUtils;

import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class Chest extends InteractiveEntity {

    Vector2 coords;
    ArrayList<Sprite> sprites;

    public Chest(World world, Camera camera, String texturePath, Vector2 isoPosition) {
        super(world, camera, texturePath);
        this.coords = isoPosition;
        sprites = new ArrayList<Sprite>();
        sprites.add(new Sprite(new Texture("environmentTextures/cabinet1.png")));
        for (Sprite s : sprites) {
            s.setScale(ENVIRONMENT_OBJECTS_SCALE);
            s.setPosition(coords.x, coords.y);
        }

        System.out.println(isoPosition.x + " "+isoPosition.y);
        System.out.println(this.getX() + " " + this.getY());
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    @Override
    public void update() {
        sprite.set(sprites.get(0));
    }
}
