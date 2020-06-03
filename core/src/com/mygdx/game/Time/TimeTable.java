package com.mygdx.game.Time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;

public class TimeTable extends Table {
    private static final int PAD = 3;
    private static final int DELTA_POSITION = 5;
    private static final String TIME_STYLE = "RobotoTime";
    private static final String TABLE_STYLE = "cell";
    private static final String TIME_TEXT = "Time: ";
    private static final int DELTA = 30;
    private Label time;

    public TimeTable() {
        super(Constants.APP_SKIN);
        initialize();
    }

    private void initialize() {

        time = new Label(TIME_TEXT + TimeManager.getTime(), Constants.APP_SKIN, TIME_STYLE);

        this.setSize(time.getPrefWidth()+DELTA, time.getPrefHeight()+DELTA);
        this.background(TABLE_STYLE);
        this.setPosition(DELTA_POSITION, Gdx.graphics.getHeight() - getHeight() - DELTA_POSITION);

        this.add(time).center();
    }

    public void updateTime() {
        time.setText(TIME_TEXT + TimeManager.getTime());
    }


}
