package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Constants;
import com.mygdx.game.Time.TimeManager;
import com.mygdx.game.entities.InteractiveAnimatedEntity;
import com.mygdx.game.map.Map;
import com.mygdx.game.utils.IsoUtils;

public class Npc extends InteractiveAnimatedEntity {
    private Path path;

    public Npc(World world, Map map, Camera camera, String texturePath, String pathName) {
        super(world, camera, texturePath);
        initialize(map, pathName);
    }

    private void initialize(Map map, String pathName) {
        spriteScale = 0.6f;
        sprite.setScale(spriteScale);
        path = new Path(map, pathName);
        calculateSpawnPosition();
        calculateDirection();
        initCharacterBody(BodyDef.BodyType.KinematicBody);
    }

    @Override
    public void update() {
        calculateDirection();
        update(Constants.PLAYER_LOW_SPEED);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        Skin uiSkin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));
        Dialog dialog = new Dialog("Warning", uiSkin, "default") {
            public void result(Object obj) {
                //System.out.println("result "+obj);
            }
        };
        dialog.text("Test Message");
        dialog.button("Yes", "true"); //sends "true" as the result
        dialog.button("No", "false"); //sends "false" as the result
        dialog.show(stage);
    }

    protected void calculateSpawnPosition() {
        setPosition(IsoUtils.IsoTo2d(path.getIsoFirstPoint()));
    }

    protected void calculateDirection() {
        float x = path.getIsoCurrent().x - getIsoPosition().x;
        float y = path.getIsoCurrent().y - getIsoPosition().y;
        if (Math.abs(x) < 10 && Math.abs(y) < 10) {
            path.moveNext();
        }
        Vector2 res = IsoUtils.getDirection(new Vector2(x,y));
        xFactor = res.x;
        yFactor = res.y;
    }
}
