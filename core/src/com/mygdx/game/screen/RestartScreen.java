package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;
import com.mygdx.game.GameContext;

public class RestartScreen extends AbstractScreen {

    private static String BUTTON_STYLE = "LoraButton";
    private static String LABEL_STYLE = "LoraTitle";
    Stage restartStage;
    Table restartTable;

    ImageTextButton restart;
    ImageTextButton menu;
    Label dieText;

    public RestartScreen(GameContext context) {
        super(context);
    }

    @Override
    public void show() {
        restartStage = new Stage();
        restartTable = new Table();

        restart = new ImageTextButton("Restart", Constants.APP_SKIN, BUTTON_STYLE);
        menu = new ImageTextButton("Menu", Constants.APP_SKIN, BUTTON_STYLE);
        dieText = new Label("You were noticed!", Constants.APP_SKIN,LABEL_STYLE);
        addButtonListener();
        restartTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        restartTable.setPosition(0,0);
        restartTable.add(dieText);
        restartTable.row();
        restartTable.add(restart).pad(5);
        restartTable.row();
        restartTable.add(menu);

        restartStage.addActor(restartTable);
        Gdx.input.setInputProcessor(restartStage);
    }

    private void addButtonListener(){
        restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.create();
                context.setScreen(ScreenType.LOADING);
            }
        });

        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(ScreenType.MENU);
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        restartStage.act();
        restartStage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
