package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import javax.swing.*;
import java.util.ArrayList;

public class CloggingIndicator extends Entity {
    ArrayList<Sprite> numbers;
    Vector2 coords;
    Player player;
    Sprite emptySprite;

    public CloggingIndicator(World world, OrthographicCamera camera, Vector2 coords, Player player) {
        super(world, camera, "environmentTextures/one.png");
        this.coords = coords;
        this.player = player;
        numbers = new ArrayList<Sprite>();
        numbers.add(new Sprite(new Texture("environmentTextures/zero.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/one.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/two.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/three.png")));
        numbers.add(new Sprite(new Texture("environmentTextures/four.png")));
        for (Sprite sprite : numbers) {
            sprite.setScale(0.25f);
            sprite.setPosition(coords.x, coords.y);
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
