package com.mygdx.game.screenUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;
import com.mygdx.game.Time.TimeTable;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryBuilder;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.Item;
import com.mygdx.game.quest.GenerateQuests;
import com.mygdx.game.quest.QuestTable;

public class GameUI extends Table {
    QuestTable questTable;
    TimeTable timeTable;
    Inventory inventory;

    public GameUI(){
        allUiRender();
    }
    private void allUiRender(){
        addQuestTable();
        addTimeTable();
        addInventory();

        //TODO remove after test
        InventoryBuilder.InventoryTable table = InventoryBuilder.getInventoryTable(1,2);
        table.setPosition(50,50);
        this.addActor(table);
    }

    private void addInventory(){
        inventory = new Inventory();
        this.row();
        this.addActor(inventory);
    }
    private void addTimeTable(){
        timeTable = new TimeTable();
        this.addActor(timeTable);
    }

    //Render quests table in UI
    private void addQuestTable() {
        questTable = GenerateQuests.generateQuests();
        questTable.left().top();
        this.addActor(questTable);
    }

    public void updateTime(){
        timeTable.updateTime();
    }
    public void setCurrentCell(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            inventory.setCurrentCell(0);
        }if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            inventory.setCurrentCell(1);
        }if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)){
            inventory.setCurrentCell(2);
        }if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            inventory.setCurrentCell(3);
        }if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
            inventory.setCurrentCell(4);
        }if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)){
            inventory.setCurrentCell(5);
        }
    }
    public void addItem(Item item){inventory.addItem(item);}
    public void renderSelectedItem(Stage stage){
        if(Item.selectedItem == null)
            return;

        Batch batch = stage.getBatch();
        batch.begin();
        System.out.println(Gdx.input.getX() + " "+Gdx.input.getY());
        Constants.APP_SKIN.getDrawable(Item.selectedItem.getName()).draw(batch,Gdx.input.getX()-15,Gdx.graphics.getHeight() - Gdx.input.getY()-15, 30,30);
        batch.end();
    }
}
