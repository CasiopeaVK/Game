package com.mygdx.game.screenUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Constants;
import com.mygdx.game.Time.TimeTable;
import com.mygdx.game.inventory.Inventory;
import com.mygdx.game.inventory.InventoryTable;
import com.mygdx.game.items.Item;
import com.mygdx.game.quest.GenerateQuests;
import com.mygdx.game.quest.QuestTable;
import lombok.Getter;

public class GameUI extends Table {
    @Getter
    QuestTable questTable;
    TimeTable timeTable;

    @Getter
    boolean inventoryShow = false;

    InventoryTable table;

    public GameUI(QuestTable questTable){
        allUiRender(questTable);
    }
    private void allUiRender(QuestTable questTable){
        addQuestTable(questTable);
        addTimeTable();


    }

    public void showInventory(boolean flag){
        if(flag)
            this.addActor(table);
        else{
            this.removeActor(table);
        }
        inventoryShow = !inventoryShow;

    }

    private void addTimeTable(){
        timeTable = new TimeTable();
        this.addActor(timeTable);
    }

    //Render quests table in UI
    private void addQuestTable(QuestTable questTable) {
        this.questTable = questTable;
        questTable.left().top();
        this.addActor(questTable);
    }

    public void updateTime(){
        timeTable.updateTime();
    }

    public void renderSelectedItem(Stage stage){
        if(Item.selectedItem == null)
            return;

        Batch batch = stage.getBatch();
        batch.begin();
        Constants.APP_SKIN.getDrawable(Item.selectedItem.getName()).draw(batch,Gdx.input.getX()-15,Gdx.graphics.getHeight() - Gdx.input.getY()-15, 30,30);
        batch.end();
    }
}
