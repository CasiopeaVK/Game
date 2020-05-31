package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.Executor;

import java.util.ArrayList;

import static com.mygdx.game.Constants.SUBAREA_COSINUS;
import static com.mygdx.game.Constants.SUBAREA_SINUS;

abstract public class AnimatedEntity extends Entity {

    protected float xFactor;
    protected float yFactor;
    protected double rotateAngle = 0;
    private int lastDirection;
    protected boolean firstStep;
    private int idleNumber;
    private int animationStep;
    private float animationAccumulator = 0;
    private ArrayList<ArrayList<Sprite>> sprites;


    public AnimatedEntity(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        firstStep = true;
        lastDirection = 1;
        xFactor = yFactor = 0;
        idleNumber = 0;
        animationStep = 1;

        Texture textureSheet = new Texture(Gdx.files.internal(texturePath));
        TextureRegion[][] tmp = TextureRegion.split(textureSheet, textureSheet.getWidth() / 8, textureSheet.getHeight() / 16);
        sprites = new ArrayList<ArrayList<Sprite>>();
        for (int i = 0; i < 16; i++) {
            ArrayList<Sprite> imageRow = new ArrayList<Sprite>();
            for (int j = 0; j < 8; j++) {
                Sprite tmpSprite = new Sprite(tmp[i][j]);
                tmpSprite.setScale(spriteScale);
                imageRow.add(tmpSprite);
            }
            sprites.add(imageRow);
        }
        sprite.set(sprites.get(0).get(0));
    }

    public void update(Executor clickHandler) {
        //TODO rewrite
        if (animationAccumulator >= 0.1) {
            clickHandler.execute();
            handleMovement();
            firstStep = false;
            animationAccumulator = 0;
        } else
            animationAccumulator += Gdx.graphics.getDeltaTime();
    }

    //this method is setting the appropriate sprite according to the xFactor and yFactor values
    protected void handleMovement() {
        //rightDown
        if (xFactor < SUBAREA_COSINUS && xFactor > SUBAREA_SINUS && yFactor < -SUBAREA_SINUS && yFactor > -SUBAREA_COSINUS) {

            rotateAngle = -Math.PI / 2;

            lastDirection = 4;
            if (firstStep) {
                //showRightDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showRightDownSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //leftDown
        else if (xFactor > -SUBAREA_COSINUS && xFactor < -SUBAREA_SINUS && yFactor < -SUBAREA_SINUS && yFactor > -SUBAREA_COSINUS) {

            rotateAngle = Math.PI;

            lastDirection = 6;
            if (firstStep) {
                //showLeftDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftDownSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //rightUp
        else if (xFactor > SUBAREA_SINUS && xFactor < SUBAREA_COSINUS && yFactor > SUBAREA_SINUS && yFactor < SUBAREA_COSINUS) {

            rotateAngle = 0;

            lastDirection = 2;
            if (firstStep) {
                //showRightUpFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showRightUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //LeftUp
        else if (xFactor < -SUBAREA_SINUS && xFactor > -SUBAREA_COSINUS && yFactor > SUBAREA_SINUS && yFactor < SUBAREA_COSINUS) {

            rotateAngle = Math.PI / 2;

            lastDirection = 8;
            if (firstStep) {
                //showRightDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //Left
        else if (xFactor < -SUBAREA_COSINUS && yFactor < SUBAREA_SINUS && yFactor > -SUBAREA_SINUS) {

            rotateAngle = Math.PI - Math.atan(0.5);

            lastDirection = 7;
            if (firstStep) {
                //showLeftFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //Right
        else if (xFactor > SUBAREA_COSINUS && yFactor < SUBAREA_SINUS && yFactor > -SUBAREA_SINUS) {

            rotateAngle = -Math.atan(0.5);

            lastDirection = 3;
            if (firstStep) {
                //showLRightFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showRightSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //Up
        else if (xFactor > -SUBAREA_SINUS && xFactor < SUBAREA_SINUS && yFactor > SUBAREA_COSINUS) {

            rotateAngle = Math.atan(0.5);

            lastDirection = 1;
            if (firstStep) {
                //showLUpFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        }
        //Down
        else if (xFactor > -SUBAREA_SINUS && xFactor < SUBAREA_SINUS && yFactor < -SUBAREA_COSINUS) {

            rotateAngle = -Math.PI + Math.atan(0.5);

            lastDirection = 5;
            if (firstStep) {
                //showLDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showDownSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (++animationStep == 8)
                    animationStep = 1;
            }
        } else if (xFactor == 0 && yFactor == 0) {
            //show last directioned idle sprites
            sprite.set(sprites.get(lastDirection - 1).get(idleNumber));
            idleNumber = ++idleNumber % 8;
        }

    }
}
