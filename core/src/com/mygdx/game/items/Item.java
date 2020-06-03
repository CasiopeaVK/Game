package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.InteractiveEntity;
import com.mygdx.game.view.GameRenderer;
import lombok.Getter;

import java.awt.event.MouseEvent;

public class Item extends InteractiveEntity {

    @Getter
    private boolean digging = false;
    private boolean improvable = false;
    private boolean improves = false;

    @Getter
    public String name;
    private float collidedRaduis = 30f;
    private float scaleFactor = 1.4f;
    GameRenderer renderer;

    public static Item selectedItem;

    public Item(World world, Camera camera, GameRenderer renderer, GameItems item) {
        super(world, camera, item.getPath());
        this.name = item.getName();
        this.renderer = renderer;
        initialize(world);
    }

    @Override
    public void update() {
        setBounds();
        this.setTouchable(Touchable.enabled);
        this.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("ENTER");
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
                //TODO draw info window
            }
        });
    }

    @Override
    protected void onClick(InputEvent event, float x, float y) {

    }

    private void initialize(World world) {
        sprite.setScale(0.1f);
        sprite.setPosition(300, 300);
        initPickUpTrigger();
    }

    private void initPickUpTrigger() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(sprite.getX(), sprite.getY());

        body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(collidedRaduis);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(this);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    public void setZoomToSprite(boolean isZoomed) {
        if (isZoomed) {
            sprite.setScale(sprite.getScaleX() * scaleFactor);
        } else {
            sprite.setScale(sprite.getScaleX() / scaleFactor);
        }
    }

    public void hideItem() {
        renderer.removeEntity(this);
        body.getWorld().destroyBody(body);
    }

    public void createPickUpItem() {
        renderer.addEntity(this);
        Entity player = renderer.getPlayerEntity();
        sprite.setPosition(player.getSprite().getX() + this.sprite.getWidth() * Constants.UNIT_SCALE,
                player.getSprite().getY() + this.sprite.getHeight() * Constants.UNIT_SCALE);
        initPickUpTrigger();
    }

    protected void setDigging(boolean flag){digging = flag;}
    protected void setImprovable(boolean improvable){this.improvable = improvable;}
    protected void setImproves(boolean improves){this.improves = improves;}
}
