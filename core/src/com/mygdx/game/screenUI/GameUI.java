package com.mygdx.game.screenUI;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Time.TimeTable;
import com.mygdx.game.inventory.Inventory;
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
}
