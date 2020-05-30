package com.mygdx.game.screenUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;

public class NoticedUI extends Table {
    private static final String PROGRESS_BAR_STYLE = "default-horizontal";
    private static final String LOADING_TEXT_STYLE = "title";

    Label notification;
    ProgressBar leftTimeProgressBar;

    public NoticedUI() {
        super();
        setSize((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 10);
        setPosition((float) Gdx.graphics.getWidth()/2-getWidth()/2, (float) Gdx.graphics.getHeight() - getHeight());

        leftTimeProgressBar = new ProgressBar(0, 1, 0.05f, false, Constants.APP_SKIN, PROGRESS_BAR_STYLE);
        notification = new Label("DETECTED", Constants.APP_SKIN, LOADING_TEXT_STYLE);

        this.add(notification).top().row();
        this.add(leftTimeProgressBar).expand().fill().bottom();
        this.setVisible(false);
    }

    public void setProgressBar(float progress){
        leftTimeProgressBar.setValue(progress);
    }
}
