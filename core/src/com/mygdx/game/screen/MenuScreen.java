package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;

public class MenuScreen extends AbstractScreen {

    private static String BUTTON_STYLE = "LoraButton";
    Stage menuStage;
    Table menuTable;
    ImageTextButton gameButton;
    ImageTextButton exitButton;

    public MenuScreen(final GameContext context) {
        super(context);
    }

    @Override
    public void show() {
        menuTable = new Table();
        menuTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menuTable.setPosition(0,0);

        menuStage = new Stage();
        gameButton = new ImageTextButton("Start game", Constants.APP_SKIN,BUTTON_STYLE);
        exitButton = new ImageTextButton("Exit", Constants.APP_SKIN,BUTTON_STYLE);
        startGameListener();
        menuTable.add(gameButton).padBottom(5);
        menuTable.row();
        menuTable.add(exitButton).fill();
        menuStage.addActor(menuTable);
        Gdx.input.setInputProcessor(menuStage);
    }

    private void startGameListener(){

        gameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(ScreenType.LOADING);
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuStage.act();
        menuStage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        menuStage.dispose();
    }

    @Override
    public void dispose() {

    }
}
