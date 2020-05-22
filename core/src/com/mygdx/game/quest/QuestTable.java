package com.mygdx.game.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class QuestTable extends Table {
    QuestLine questLine;
    Quest currentQuest;

    Skin tableSkin = new Skin(Gdx.files.internal("flat-earth/flat-earth-ui.json"));

    Label header = new Label("Current quest:", tableSkin, "default");
    Label currentName;
    Label currentDescription;
    CheckBox isPerformedCheckBox = new CheckBox("", tableSkin, "default");

    public QuestTable(QuestLine questLine) {
        super(new Skin(Gdx.files.internal("flat-earth/flat-earth-ui.json")));
        this.questLine = questLine;
        initialization();
    }

    private void initialization() {
        this.background("Semi-gray").setSize(200,100);

        this.currentQuest = questLine.getQuest();
        this.currentName = new Label(currentQuest.getName(), tableSkin, "default");
        this.currentDescription = new Label(currentQuest.getDescription(), tableSkin, "default");

        this.add(header).left();
        this.row();
        this.add(currentName).left();
        this.row();
        this.add(currentDescription).left();
        this.row();
        this.add(isPerformedCheckBox).left();
    }

    public void congratulation() {
        isPerformedCheckBox.setChecked(true);
    }

    public void updateQuest() {
        if (!currentQuest.isEnd()) {
            questLine.performQuest();
            this.currentQuest = questLine.getQuest();
            if (currentQuest != null) {
                currentName.setText(currentQuest.getName());
                currentDescription.setText(currentQuest.getDescription());
                isPerformedCheckBox.setChecked(false);
            }
        }else{
            questLine.performQuest();
            isPerformedCheckBox.setChecked(true);
        }
    }
}