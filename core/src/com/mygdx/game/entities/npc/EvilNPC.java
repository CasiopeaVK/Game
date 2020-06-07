package com.mygdx.game.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;
import com.mygdx.game.map.Map;
import com.mygdx.game.screen.ScreenType;
import com.mygdx.game.screenUI.NoticedUI;

import java.util.Iterator;

import static com.mygdx.game.Constants.IGNORE;

public class EvilNPC extends Npc {

    private GameContext context;

    private float deadZoneRadius = 200f;
    private float deadZoneAngle = (float) (Math.PI / 2);
    protected FixtureDef deadZoneFixture;
    protected Body deadZoneBody;

    private boolean isTriggered = false;
    private float triggerAccumulator = 0;

    private NoticedUI noticedUI = new NoticedUI();
    private MovementDelayManager normalMovementDelayManager;
    protected MovementDelayManager idleMovementDelayManager = new MovementDelayManager() {
        @Override
        public boolean preMovePredicate() {
            System.out.println("pre");;
            return false;
        }

        @Override
        public boolean postMovePredicate() {
            return false;
        }
    };

    public EvilNPC(String name, GameContext context, Map map, String texturePath) {
        super(name, context.getWorld(), map, context.getCamera(), texturePath);
        this.context = context;
        initializeDeadZone(createDeadZoneVertices());
    }
    protected EvilNPC(String name, String texturePath, GameContext context, Map map) {
        super(name, context.getWorld(), map, context.getCamera(), texturePath);
        this.context = context;
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

    protected void initializeDeadZone(Vector2[] vertices) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        deadZoneBody = world.createBody(bodyDef);
        PolygonShape sector = new PolygonShape();
        sector.set(vertices);
        deadZoneFixture = new FixtureDef();
        deadZoneFixture.shape = sector;
        deadZoneFixture.isSensor = true;
        deadZoneFixture.filter.maskBits = IGNORE;
        deadZoneFixture.filter.categoryBits = IGNORE;
        deadZoneFixture.filter.groupIndex = IGNORE;
        deadZoneBody.createFixture(deadZoneFixture).setUserData(this);
    }


    public void updateDeadZone() {
        deadZoneBody.setTransform(body.getPosition(), (float) rotateAngle);
    }

    protected Vector2[] createDeadZoneVertices() {
        int STEPS = 5;
        Vector2[] vertices = new Vector2[STEPS + 3];
        vertices[0] = new Vector2(0f, 0f);
        float angle = -deadZoneAngle / 2;
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new Vector2((float) Math.cos(angle) * deadZoneRadius, (float) Math.sin(angle) * deadZoneRadius);
            angle += deadZoneAngle / STEPS;
        }
        vertices[STEPS + 2] = new Vector2(0f, 0f);
        return vertices;
    }

    @Override
    public void setMovementDelayManager(MovementDelayManager movementDelayManager) {
        super.setMovementDelayManager(movementDelayManager);
        normalMovementDelayManager = movementDelayManager;
    }

    @Override
    public void update() {
        checkTrigger();
        super.update();
        updateDeadZone();
    }

    protected void checkTrigger() {
        if (isTriggered) {
            movementDelayManager.preMovePredicate();
            triggerAccumulator += Gdx.graphics.getDeltaTime();
            if (triggerAccumulator >= Constants.NOTICE_TIME*2) {
                context.setScreen(ScreenType.RESTART);
            } else {
                noticedUI.setVisible(true);
                noticedUI.setProgressBar(triggerAccumulator / Constants.NOTICE_TIME*2);
            }
        }
    }

    public void triggerNpc() {
        movementDelayManager = idleMovementDelayManager;
        isTriggered = true;
        initializeNoticedUI();
    }

    public void cancelTrigger() {
        movementDelayManager = normalMovementDelayManager;
        isTriggered = false;
        triggerAccumulator = 0;
        noticedUI.setVisible(false);
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
        stage.addActor(noticedUI);
    }
}
