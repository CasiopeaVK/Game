package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.InteractiveAnimatedEntity;
import com.mygdx.game.entities.npc.dialog.DialogLine;
import com.mygdx.game.map.Map;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;
import lombok.Getter;
import lombok.Setter;


public class Npc extends InteractiveAnimatedEntity {
    @Getter
    private Path path;
    private DialogLine dialogLine;
    @Getter
    private String name;
    @Setter
    protected MovementDelayManager movementDelayManager = new MovementDelayManager() {
        @Override
        public boolean preMovePredicate() {
            return true;
        }

        @Override
        public boolean postMovePredicate() {
            return true;
        }
    };

    public Npc(String name, World world, Map map, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        this.name = name;
        initialize(map);
    }

    public Npc(String name, World world, Map map, Camera camera, String texturePath, MovementDelayManager movementDelayManager) {
        this(name, world, map, camera, texturePath);
        this.movementDelayManager = movementDelayManager;
    }

    private void initialize(Map map) {
        System.out.println(name);
        spriteScale = 0.6f;
        sprite.setScale(spriteScale);
        path = new Path(map, name);
        calculateSpawnPosition();
        calculateDirection();
        initCharacterBody(BodyDef.BodyType.KinematicBody);
        dialogLine = new DialogLine(Gdx.files.internal("dialogs/" + name + ".json"), Constants.APP_SKIN);
    }


    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
        dialogLine.setStage((SmartStage) stage);
    }

    @Override
    public void update() {
        calculateDirection();
        update(Constants.PLAYER_LOW_SPEED);
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {
        dialogLine.runDialog(name);
    }

    protected void calculateSpawnPosition() {
        setPosition(IsoUtils.IsoTo2d(path.getIsoFirstPoint()));
    }

    protected void calculateDirection() {
        float x = path.getIsoCurrent().x - getIsoPosition().x;
        float y = path.getIsoCurrent().y - getIsoPosition().y;
        if (Math.abs(x) < 10 && Math.abs(y) < 10) {
            if (movementDelayManager.preMovePredicate()) {
                path.moveNext();
            } else {
                x = 0;
                y = 0;
            }

        }
        Vector2 res = IsoUtils.getDirection(new Vector2(x, y));
        xFactor = res.x;
        yFactor = res.y;
    }
}
