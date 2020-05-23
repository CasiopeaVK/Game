package com.mygdx.game.quest;

public class GenerateQuests {

    public static QuestTable generateQuests(){
        QuestLine questLine = new QuestLine("Sample quest");
        questLine.addQuest(new Quest("Sample quest", "Something big description.\n All is usual, Vlad soset", false));
        questLine.addQuest(new Quest("Sample quest2", "Something big description.\n All is usual, Vlad soset*2", true));

        return new QuestTable(questLine);
    }
}
