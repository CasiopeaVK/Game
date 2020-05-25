package com.mygdx.game.quest;

public class GenerateQuests {

    public static QuestTable generateQuests(){
        QuestLine questLine = new QuestLine("Sample quest");
        questLine.addQuest(new Quest("Sample quest", "Something big description.\n All is usual", false));
        questLine.addQuest(new Quest("Sample quest2", "Something big description.\n All is usual", true));

        return new QuestTable(questLine);
    }
}
