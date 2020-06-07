package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.GameContext;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.items.ItemBuilder;
import com.mygdx.game.items.PickUpSensor;
import com.mygdx.game.map.Map;
import com.mygdx.game.screenUI.GameUI;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;

import java.util.ArrayList;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

public class SmartBed extends Bed {
    Vector2 coords;
    Player player;
    ArrayList<Sprite> sprites;
    boolean sleeping = false;
    GameContext context;
    Map map;
    GameUI gameUI;
    PickUpSensor sensor;
    ItemBuilder itemBuilder;
    SmartStage stage;
    GameRenderer gameRenderer;

    public SmartBed(World world, Camera camera, Sprite sprite, Player player, Vector2 isoPosition, GameContext context) {
        super(world, camera, sprite);
        this.coords = new Vector2(isoPosition.x - sprite.getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - sprite.getHeight() * ENVIRONMENT_OBJECTS_SCALE - 20);
        this.player = player;
        this.context = context;
        this.map = context.getMap();
        this.gameUI = context.getGameUI();
        this.sensor = context.getSensor();
        this.itemBuilder = context.getItemBuilder();
        this.stage = context.getStage();
        this.gameRenderer = context.getGameRenderer();
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
        if ((TimeManager.getHours() >= 23 || TimeManager.getHours() < 7) && !sleeping) {
            TimeManager.setTIME_SCALE(30);
            sleeping = true;
            player.setSpritesScale(0);
            player.getSprite().set(player.getSprites().get(0).get(0));
            player.setSleeping(true);
        }

    }

    @Override
    public void update() {
        updateClickListener();
        if (TimeManager.getHours() >= 7 && TimeManager.getHours() < 23 && sleeping) {
            sleeping = false;
            player.setSpritesScale(0.6f);

        }
        if (sleeping)
            sprite.set(sprites.get(1));
        else
            sprite.set(sprites.get(0));
    }
    public void setSpritesPosition(Vector2 position){
        for (Sprite sprite:sprites)
            sprite.setPosition(position.x, position.y);
    }
}
