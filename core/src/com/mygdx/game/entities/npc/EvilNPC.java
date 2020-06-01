package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;
import com.mygdx.game.map.Map;
import com.mygdx.game.screen.ScreenType;
import com.mygdx.game.screenUI.NoticedUI;

import java.util.Iterator;

public class EvilNPC extends Npc {

    private GameContext context;

    private float deadZoneRadius = 200f;
    private float deadZoneAngle = (float) (Math.PI / 2);
    private Vector2[] vertices;
    private FixtureDef deadZoneFixture;
    private Body deadZoneBody;

    private boolean isTriggered = false;
    private float triggerAccumulator = 0;

    private NoticedUI noticedUI;

    public EvilNPC(String name, GameContext context, Map map, String texturePath, String pathName) {
        super(name, context.getWorld(), map, context.getCamera(), texturePath, pathName);
        this.context = context;
        initializeDeadZone();
    }

    public void initializeNoticedUI() {
        Array<Actor> actors = stage.getActors();
        for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if (actor instanceof NoticedUI) {
                noticedUI = (NoticedUI) actor;
            }
        }
    }

    private void initializeDeadZone() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        deadZoneBody = world.createBody(bodyDef);
        PolygonShape sector = new PolygonShape();
        createDeadZoneVertices();
        sector.set(vertices);
        deadZoneFixture = new FixtureDef();
        deadZoneFixture.shape = sector;
        deadZoneFixture.isSensor = true;
        deadZoneBody.createFixture(deadZoneFixture).setUserData(this);
    }


    public void updateDeadZone() {
        deadZoneBody.setTransform(body.getPosition(), (float) rotateAngle);
    }

    public void createDeadZoneVertices() {
        int STEPS = 5;
        vertices = new Vector2[STEPS + 3];
        vertices[0] = new Vector2(0f, 0f);
        float angle = -deadZoneAngle / 2;
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new Vector2((float) Math.cos(angle) * deadZoneRadius, (float) Math.sin(angle) * deadZoneRadius);
            angle += deadZoneAngle / STEPS;
        }
        vertices[STEPS + 2] = new Vector2(0f, 0f);
    }

    @Override
    public void update() {
        super.update();
        updateDeadZone();
        if (isTriggered) {
            triggerAccumulator += Gdx.graphics.getDeltaTime();
            if (triggerAccumulator >= Constants.NOTICE_TIME) {
                context.setScreen(ScreenType.RESTART);
            } else {
                noticedUI.setVisible(true);
                noticedUI.setProgressBar(triggerAccumulator / Constants.NOTICE_TIME);
            }
        }
    }

    public void triggerNpc() {
        System.out.println("Dead zone");
        isTriggered = true;
        initializeNoticedUI();
        //TODO stop enemy
    }

    public void cancelTrigger() {
        isTriggered = false;
        triggerAccumulator = 0;
        noticedUI.setVisible(false);
    }
}
