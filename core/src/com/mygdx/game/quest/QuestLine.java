package com.mygdx.game.quest;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Bed;
import com.mygdx.game.entities.SmartBed;
import com.mygdx.game.entities.npc.Npc;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.ObjectsRenderer;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.css.CSSImportRule;

import java.util.ArrayList;

enum QuestLineStatus {
    IN_PROCESS, END;
}

public class QuestLine {

    private String name;
    private ArrayList<Quest> quests;
    @Getter
    private int currentQuestIndex = 0;
    private QuestLineStatus questLineStatus = QuestLineStatus.IN_PROCESS;
    @Setter
    private QuestTable questTable;
    GameContext context;

    public QuestLine(String name, GameContext context) {
        this.name = name;
        this.context = context;
        quests = new ArrayList<Quest>();
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public Quest getQuest() {
        if (currentQuestIndex < quests.size())
            return quests.get(currentQuestIndex);
        return null;
    }

    public void incrementQuest() {
        currentQuestIndex++;
        questTable.updateQuest();
        if (currentQuestIndex == 3) {
            //swap beds
            SmartBed smartBed = ObjectsRenderer.smartBed;
            Bed simpleBed = ObjectsRenderer.simpleBed;
            Vector2 smartBedCoords = new Vector2(smartBed.getSprite().getX(), smartBed.getSprite().getY());
            Vector2 simpleBedCoords = new Vector2(simpleBed.getSprite().getX(), simpleBed.getSprite().getY());
            smartBed.setPosition(simpleBedCoords.x, simpleBedCoords.y);
            smartBed.setSpritesPosition(simpleBedCoords);
            simpleBed.setPosition(smartBedCoords.x, smartBedCoords.y);
            simpleBed.getSprite().setPosition(smartBedCoords.x, smartBedCoords.y);
            //delete evilNPC
            Npc npc = context.getNpcList().stream().filter(npc1 -> npc1.getName().equals("madNpc1")).findFirst().get();
            context.getStage().remove(npc);
            context.getGameRenderer().removeEntity(npc);
            context.getWorld().destroyBody(npc.getBody());
        }
    }

    public void performQuest() {
        if (questLineStatus == QuestLineStatus.END)
            return;

        Quest quest = quests.get(currentQuestIndex);
        quest.setPerformed(true);
        incrementQuest();

        if (quest.isEnd()) {
            questLineStatus = QuestLineStatus.END;
        }
    }

    public QuestLineStatus getQuestLineStatus() {
        return questLineStatus;
    }

}
