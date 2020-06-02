package com.mygdx.game.screenUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Constants;

public class LoadingUI extends Table {
    private static final String PROGRESS_BAR_STYLE = "hospital";
    private static final String LOADING_TEXT_STYLE = "RobotoTitle";

    ProgressBar progressBar;
    Label loading;

    public LoadingUI(){
        super();
        setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        setPosition(Gdx.graphics.getWidth()/2-getWidth()/2,0);

        progressBar = new ProgressBar(0,1,0.1f,false,Constants.APP_SKIN,PROGRESS_BAR_STYLE);
        loading = new Label("Loading", Constants.APP_SKIN, LOADING_TEXT_STYLE);

        this.add(loading).bottom().row();
        this.add(progressBar).expand().fill().bottom();
        bottom();
    }

    public void setProgressBar(float progress){
        progressBar.setValue(progress);
    }
}
