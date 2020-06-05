package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Constants {
    public static final float UNIT_SCALE = 1 / 4f;
    public static final String SKIN_JSON = "flat-earth2/flat-earth-ui.json";
    public static final Skin APP_SKIN = new Skin(Gdx.files.internal(Constants.SKIN_JSON));
    public static final float PLAYER_SPEED = 10000;
    public static final float PLAYER_LOW_SPEED = 3000;
    public static final int PLAYER_HEIGHT = 400;
    public static final int PLAYER_WIDTH = 150;
    public static final double SUBAREA_COSINUS = Math.cos(Math.PI / 8);
    public static final double SUBAREA_SINUS = Math.sin(Math.PI / 8);
    public static final float COSPI4 = (float) Math.cos(Math.PI / 4);
    public static final float SINPI4 = (float)Math.sin(Math.PI / 4);
    public static final float NOTICE_TIME = 1f;
    public static final float ENVIRONMENT_OBJECTS_SCALE = 0.5f;
    public static final float DOOR_OBJECTS_SCALE = 0.25f;
    public static final Vector2 OBJECT_SETTLING_PADDLE = new Vector2(70,15);
}
