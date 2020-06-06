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

public class WinScreen extends AbstractScreen {
    private static String BUTTON_STYLE = "LoraButton";
    private static String LABEL_STYLE = "LoraTitle";

    Stage winStage;
    Table winTable;

    Label winLabel;
    ImageTextButton menuButton;
    public WinScreen(GameContext context) {
        super(context);
    }

    @Override
    public void show() {
        winStage = new Stage();
        winTable = new Table();

        winLabel = new Label("You are win!", Constants.APP_SKIN, LABEL_STYLE);
        menuButton = new ImageTextButton("Menu", Constants.APP_SKIN, BUTTON_STYLE);

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.create();
                context.setScreen(ScreenType.MENU);
            }
        });

        winTable.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        winTable.setPosition(0,0);
        winTable.add(winLabel);
        winTable.row();
        winTable.add(menuButton).pad(5);

        winStage.addActor(winTable);
        Gdx.input.setInputProcessor(winStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        winStage.act();
        winStage.draw();
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
