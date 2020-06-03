package com.mygdx.game.quest;

import lombok.Getter;

import java.util.ArrayList;
enum QuestLineStatus{
    IN_PROCESS, END;
}
public class QuestLine {

    private String name;
    private ArrayList<Quest> quests;
    @Getter
    private int currentQuestIndex = 0;
    private QuestLineStatus questLineStatus = QuestLineStatus.IN_PROCESS;

    public QuestLine(String name){
        this.name = name;
        quests = new ArrayList<Quest>();
    }

    public void addQuest(Quest quest){
        quests.add(quest);
    }

    public Quest getQuest(){
        if(currentQuestIndex < quests.size())
            return quests.get(currentQuestIndex);
        return null;
    }

    public void incrementQuest(){
        currentQuestIndex++;
    }

    public void performQuest(){
        if(questLineStatus == QuestLineStatus.END)
            return;

        Quest quest = quests.get(currentQuestIndex);
        quest.setPerformed(true);
        incrementQuest();

        if(quest.isEnd()){
            questLineStatus = QuestLineStatus.END;
        }
    }

    public QuestLineStatus getQuestLineStatus() {
        return questLineStatus;
    }
}
