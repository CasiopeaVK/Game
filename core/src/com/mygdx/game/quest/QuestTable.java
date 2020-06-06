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
import com.mygdx.game.GameContext;
import com.mygdx.game.screen.ScreenType;
import lombok.Getter;
import lombok.Setter;

public class QuestTable extends Table {
    private final int TABLE_WIDTH = 250;
    private final int TABLE_HEIGHT = 150;
    private final int TOP_PAD = 17;
    private final int LEFT_PAD = 20;
    private final int RIGHT_PAD = 0;
    private final int BOTTOM_PAD = 0;
    private final String TABLE_BG = "tableBG";
    private final String NAME_STYLE = "LoraLabel";
    private final String DESCRIPTION_STYLE = "LoraText";
    @Getter
    QuestLine questLine;
    Quest currentQuest;


    Label header = new Label("Current quest:", Constants.APP_SKIN, "RobotoTime");
    Label currentName;
    Label currentDescription;
    GameContext context;

    public QuestTable(QuestLine questLine, GameContext context) {
        super(Constants.APP_SKIN);
        this.context = context;
        this.questLine = questLine;
        questLine.setQuestTable(this);
        initialization();
    }

    private void initialization() {
        this.background(TABLE_BG).setSize(TABLE_WIDTH,TABLE_HEIGHT);
        this.setPosition(Gdx.graphics.getWidth()-getWidth(), Gdx.graphics.getHeight()-getHeight());
        this.pad(TOP_PAD,LEFT_PAD,BOTTOM_PAD,RIGHT_PAD);

        this.currentQuest = questLine.getQuest();
        this.currentName = new Label(currentQuest.getName(), Constants.APP_SKIN, NAME_STYLE);
        this.currentDescription = new Label(currentQuest.getDescription(), Constants.APP_SKIN, DESCRIPTION_STYLE);

        this.add(currentName).left();
        this.row();
        this.add(currentDescription).left();


    }

    public void updateQuest() {
        if (!currentQuest.isEnd()) {
            this.currentQuest = questLine.getQuest();
            if (currentQuest != null) {
                currentName.setText(currentQuest.getName());
                currentDescription.setText(currentQuest.getDescription());
            }
        }else{
            context.setScreen(ScreenType.WIN);
        }
    }
}
