package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.quest.Quest;
import com.mygdx.game.quest.QuestLine;
import com.mygdx.game.quest.QuestTable;

public class Game extends ApplicationAdapter{
    SpriteBatch batch;
    Texture img;
    CameraViewProcessor cameraViewProcessor;

    //TODO remove
    Stage stage;
    QuestTable questTable;

    @Override
    public void create() {
        TiledMap tiledMap = new TmxMapLoader().load("Water.tmx");
        cameraViewProcessor = new CameraViewProcessor(tiledMap);

        //TODO remove
        questTest();
    }

    //TODO remove
    private void questTest(){
        stage = new Stage();

        QuestLine questLine = new QuestLine("Sample quest");
        questLine.addQuest(new Quest("Sample quest","Something big description.\n All is usual, Vlad soset",false));
        questLine.addQuest(new Quest("Sample quest2","Something big description.\n All is usual, Vlad soset*2",true));

        questTable = new QuestTable(questLine);
        questTable.setX(stage.getWidth()-170);
        questTable.setY(stage.getHeight()-100);
        questTable.left().top();
        stage.addActor(questTable);
    }

    @Override
    public void render() {
        cameraViewProcessor.process();
        stage.act();
        stage.draw();

        //TODO remove
        questTestListener();
    }

    //TODO remove
    private void questTestListener(){
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            questTable.updateQuest();
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

}
