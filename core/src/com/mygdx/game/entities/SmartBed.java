package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class SmartBed extends Bed {
    Vector2 coords;
    Player player;
    ArrayList<Sprite> sprites;
    boolean sleeping = false;

    public SmartBed(World world, Camera camera, Sprite sprite, Player player, Vector2 isoPosition) {
        super(world, camera, sprite);
        this.coords = new Vector2(isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE-20);
        this.player = player;
        sprites = new ArrayList<>();
        sprites.add(new Sprite(new Texture("environmentTextures/empty_bed.png")));
        sprites.add(new Sprite(new Texture("environmentTextures/bed_with_man.png")));
        for (Sprite s : sprites) {
            s.setScale(ENVIRONMENT_OBJECTS_SCALE);
            s.setPosition(coords.x, coords.y);
        }
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        System.out.println(111);
    }

    @Override
    public void update() {
        updateClickListener();
        if (sleeping)
            sprite.set(sprites.get(1));
        else
            sprite.set(sprites.get(0));
    }
}
