package com.mygdx.game.quest;

public class GenerateQuests {

    public static QuestTable generateQuests(){
        QuestLine questLine = new QuestLine("Sample quest");
        questLine.addQuest(new Quest("Decide what to do", "Ability to speak \nis the difference between \nhuman and animal", false));
        questLine.addQuest(new Quest("Quid pro quo", "Fulfill the request of the neighbors", false));
        questLine.addQuest(new Quest("Diplomat", "Time to manipulate", false));

        return new QuestTable(questLine);
    }
}
