package com.mygdx.game.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;

public class TimeTable extends Table {

    Label time;
    Skin tableSkin = new Skin(Gdx.files.internal(Constants.SKIN_JSON));

    public TimeTable() {
        super(new Skin(Gdx.files.internal(Constants.SKIN_JSON)));
        initialize();
    }

    private void initialize() {
        time = new Label("Time: " + TimeManager.getTime(), tableSkin, "default");

        this.setSize(time.getPrefWidth(), time.getPrefHeight());
        this.background("Semi-gray");
        this.setPosition(5, Gdx.graphics.getHeight() - getHeight() - 5);
        this.pad(2, 2, 2, 2);

        this.add(time).center();
    }

    public void updateTime() {
        time.setText("Time: " + TimeManager.getTime());
    }
}
