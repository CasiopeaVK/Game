package com.mygdx.game.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.items.Item;
import com.mygdx.game.screenUI.GameUI;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SmartStage extends Stage {
    private List<Entity> entityList = new CopyOnWriteArrayList<>();
    @Setter
    private GameUI gameUI;
    @Setter
    @Getter
    private Player player;

    public void update() {
        gameUI.updateTime();
        player.setCurrentCell();
        act();
        for (Entity entity : entityList) {
            entity.update();
        }
        draw();
        gameUI.renderSelectedItem(this);
    }

    public void addEntity(Entity entity) {
        addActor(entity);
        entityList.add(entity);
    }


    public void remove(Entity entity) {
        entityList.remove(entity);
    }

    public boolean addItem(Item item) {
        return player.addItem(item);
    }

    public int getCurrentQuestIndex() {
        return gameUI.getQuestTable().getQuestLine().getCurrentQuestIndex();
    }

    public void incrementCurrentQuestIndex() {
        gameUI.getQuestTable().getQuestLine().incrementQuest();
    }

}
