package com.mygdx.game.Time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;

public class TimeTable extends Table {
    private static final int PAD = 2;
    private static final int DELTA_POSITION = 5;
    private static final String TIME_STYLE = "default";
    private static final String TABLE_STYLE = "Semi-gray";
    private static final String TIME_TEXT = "Time: ";

    private Label time;
    private Skin tableSkin = new Skin(Gdx.files.internal(Constants.SKIN_JSON));

    public TimeTable() {
        super(new Skin(Gdx.files.internal(Constants.SKIN_JSON)));
        initialize();
    }

    private void initialize() {

        time = new Label(TIME_TEXT + TimeManager.getTime(), tableSkin, TIME_STYLE);

        this.setSize(time.getPrefWidth(), time.getPrefHeight());
        this.background(TABLE_STYLE);
        this.setPosition(DELTA_POSITION, Gdx.graphics.getHeight() - getHeight() - DELTA_POSITION);
        this.pad(PAD);

        this.add(time).center();
    }

    public void updateTime() {
        time.setText(TIME_TEXT + TimeManager.getTime());
    }
}
