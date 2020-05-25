package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Constants {
    public static final float UNIT_SCALE = 1/4f;
    public static final String SKIN_JSON = "flat-earth2/flat-earth-ui.json";
    public static final Skin APP_SKIN = new Skin(Gdx.files.internal(Constants.SKIN_JSON));
    public static final float PLAYER_SPEED = 400;
}
