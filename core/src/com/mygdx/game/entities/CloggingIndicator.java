package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class CloggingIndicator extends Entity {
    ArrayList<Sprite> numbers;
    Vector2 coords;
    Player player;
    Sprite emptySprite;

    public CloggingIndicator(World world, OrthographicCamera camera, Sprite sprite, Player player, Vector2 isoPosition) {
        super(world, camera, sprite);
        this.coords = new Vector2(isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE);
        this.player = player;
        numbers = new ArrayList<Sprite>();
        numbers.add(new Sprite(new Texture("environmentTextures/zero.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/one.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/two.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/three.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/four.png")));
        for (Sprite sprite1 : numbers) {
            sprite1.setScale(ENVIRONMENT_OBJECTS_SCALE);
            sprite1.setPosition(coords.x, coords.y);
        }
        sprite.set(numbers.get(0));
        emptySprite = new Sprite(new Texture("environmentTextures/zero.png"));
        emptySprite.setScale(0);
    }

    @Override
    public void update() {
        if (Math.abs(player.getSprite().getX() - coords.x) + Math.abs(player.getSprite().getY() - coords.y) < 150)
            sprite.set(numbers.get(Toilet.clogging));
        else
            sprite.set(emptySprite);
    }
}
