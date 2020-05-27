package com.mygdx.game.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Constants;

public class QuestTable extends Table {
    private final int TABLE_WIDTH = 250;
    private final int TABLE_HEIGHT = 150;
    private final int TABLE_PAD = 10;
    private final String TABLE_BG = "Semi-gray";
    private final String NAME_STYLE = "default";
    private final String DESCRIPTION_STYLE = "default";

    QuestLine questLine;
    Quest currentQuest;


    Label header = new Label("Current quest:", Constants.APP_SKIN, "button");
    Label currentName;
    Label currentDescription;
    CheckBox isPerformedCheckBox = new CheckBox("", Constants.APP_SKIN, "default");

    public QuestTable(QuestLine questLine) {
        super(Constants.APP_SKIN);
        this.questLine = questLine;
        initialization();
    }

    private void initialization() {
        this.background(TABLE_BG).setSize(TABLE_WIDTH,TABLE_HEIGHT);
        this.setPosition(Gdx.graphics.getWidth()-getWidth(), Gdx.graphics.getHeight()-getHeight());
        this.pad(TABLE_PAD);

        this.currentQuest = questLine.getQuest();
        this.currentName = new Label(currentQuest.getName(), Constants.APP_SKIN, NAME_STYLE);
        this.currentDescription = new Label(currentQuest.getDescription(), Constants.APP_SKIN, DESCRIPTION_STYLE);
        this.isPerformedCheckBox.setDisabled(true);

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
