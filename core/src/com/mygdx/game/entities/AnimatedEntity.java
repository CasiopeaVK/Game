package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.Executor;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

abstract public class AnimatedEntity extends Entity {

    protected float xFactor;
    protected float yFactor;
    private int lastDirection;
    protected boolean firstStep;
    private int idleNumber;
    private int animationStep;
    private int framesCounter;
    private ArrayList<ArrayList<Sprite>> sprites;

    public AnimatedEntity(World world, Camera camera, String texturePath) {
        super(world, camera, texturePath);
        firstStep = true;
        lastDirection = 1;
        xFactor = yFactor = 0;
        idleNumber = 0;
        animationStep = 1;
        framesCounter = 0;

        Texture textureSheet = new Texture(Gdx.files.internal(texturePath));
        TextureRegion[][] tmp = TextureRegion.split(textureSheet, textureSheet.getWidth() / 3, textureSheet.getHeight() / 16);
        sprites = new ArrayList<ArrayList<Sprite>>();
        for (int i = 0; i < 16; i++) {
            ArrayList<Sprite> imageRow = new ArrayList<Sprite>();
            for (int j = 0; j < 3; j++) {
                Sprite tmpSprite = new Sprite(tmp[i][j]);
                tmpSprite.setScale(spriteScale);
                imageRow.add(tmpSprite);
            }
            sprites.add(imageRow);
        }
        sprite.set(sprites.get(0).get(0));
    }

    public void update(Executor clickHandler){
        if (framesCounter == 10) {
            clickHandler.execute();
            handleMovement();
            firstStep = false;
            framesCounter = 0;
        } else
            framesCounter++;
    }

    //this method is setting the appropriate sprite according to the xFactor and yFactor values
    void handleMovement() {
        //rightDown
        if (xFactor == 1 && yFactor == 1) {
            lastDirection = 4;
            if (firstStep) {
                //showRightDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
                //showRightDownSprite
            }
        }
        //leftDown
        else if (xFactor == -1 && yFactor == 1) {
            lastDirection = 6;
            if (firstStep) {
                //showLeftDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftDownSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //rightUp
        else if (xFactor == 1 && yFactor == -1) {
            lastDirection = 2;
            if (firstStep) {
                //showRightUpFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showRightUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //LeftUp
        else if (xFactor == -1 && yFactor == -1) {
            lastDirection = 8;
            if (firstStep) {
                //showRightDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //Left
        else if (xFactor == -1 && yFactor == 0) {
            lastDirection = 7;
            if (firstStep) {
                //showLeftFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showLeftSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //Right
        else if (xFactor == 1 && yFactor == 0) {
            lastDirection = 3;
            if (firstStep) {
                //showLRightFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showRightSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //Up
        else if (xFactor == 0 && yFactor == -1) {
            lastDirection = 1;
            if (firstStep) {
                //showLUpFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showUpSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        }
        //Down
        else if (xFactor == 0 && yFactor == 1) {
            lastDirection = 5;
            if (firstStep) {
                //showLDownFirstSprite
                sprite.set(sprites.get(7 + lastDirection).get(0));
            } else {
                //showDownSprite
                sprite.set(sprites.get(7 + lastDirection).get(animationStep));
                if (animationStep == 1)
                    animationStep = 2;
                else animationStep = 1;
            }
        } else if (xFactor == 0 && yFactor == 0) {
            //show last directioned idle sprites
            sprite.set(sprites.get(lastDirection - 1).get(idleNumber));
            idleNumber = ++idleNumber % 3;
        }

    }
}
