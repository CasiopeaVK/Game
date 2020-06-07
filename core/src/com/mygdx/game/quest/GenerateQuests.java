package com.mygdx.game.quest;

import com.mygdx.game.GameContext;

public class GenerateQuests {

    public static QuestTable generateQuests(GameContext context){
        QuestLine questLine = new QuestLine("Sample quest", context);
        questLine.addQuest(new Quest("Decide what to do", "Ability to speak \nis the difference between \nhuman and animal", false));
        questLine.addQuest(new Quest("Quid pro quo", "Fulfill the request of the \nneighbors", false));
        questLine.addQuest(new Quest("Diplomat", "Time to manipulate", false));
        questLine.addQuest(new Quest("One and AM", "Hollow out the freedom", true));

        return new QuestTable(questLine, context);
    }
}
