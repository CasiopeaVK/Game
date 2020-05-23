package com.mygdx.game.screenUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.mygdx.game.Constants;

public class LoadingUI extends Table {
    ProgressBar progressBar;
    Label loading;
    Skin uiSkin = new Skin(Gdx.files.internal(Constants.SKIN_JSON));

    public LoadingUI(){
        super();
        setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        setPosition(Gdx.graphics.getWidth()/2-getWidth()/2,0);

        progressBar = new ProgressBar(0,1,0.1f,false,uiSkin,"default-horizontal");
        loading = new Label("Loading", uiSkin, "title");

        this.add(loading).bottom().row();
        this.add(progressBar).expand().fill().bottom();
        bottom();
    }

    public void setProgressBar(float progress){
        progressBar.setValue(progress);
    }
}
