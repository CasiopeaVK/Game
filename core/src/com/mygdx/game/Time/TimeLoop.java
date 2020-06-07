package com.mygdx.game.Time;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Bed;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.TableFood;
import com.mygdx.game.entities.npc.*;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.ObjectsRenderer;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.view.GameRenderer;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeLoop {
    private List<Npc> npcList;
    private List<Bed> beds;
    private GameContext context;
    private static int hours;
    private static boolean day = true;
    private GameRenderer gameRenderer;
    private SmartStage stage;
    private List<String> npcNames;
    private World world;
    private Map map;
    private OrthographicCamera camera;
    private RayHandler rayHandler;

    public TimeLoop(List<Npc> npcList, GameContext context) {
        this.npcNames = new ArrayList<>();
        this.npcList = npcList;
        for (Npc npc : npcList)
            npcNames.add(npc.getName());
        this.context = context;
        this.gameRenderer = context.getGameRenderer();
        this.stage = context.getStage();
        beds = ObjectsRenderer.beds;
        world = context.getWorld();
        map = context.getMap();
        camera = context.getCamera();
        rayHandler = context.getRayHandler();
    }

    public void processTimeChange() {
        hours = TimeManager.getHours();
        if (hours >= 23 || hours < 7) {
            //if changed from day to night
            if (day) {
                startNight();
                day = false;
            }
        } else {
            //if changed from night to day
            if (!day) {
                startDay();
                day = true;
            }
        }
    }

    private void startDay() {
        TimeManager.setTIME_SCALE(0.7f);
        ObjectsRenderer.tables.stream().forEach(tableFood -> tableFood.updateInventory());
        rayHandler.setAmbientLight(0, 0, 0, 0.8f);
        map.removeNightLight();
        context.getPlayer().setSleeping(false);
        //destroy guards
        for (Npc npc : npcList) {
            stage.remove(npc);
            gameRenderer.removeEntity(npc);
            world.destroyBody(npc.getBody());
        }
        //change beds
        for (Bed bed : beds) {
            Sprite oldSprite = bed.getSprite();
            Sprite newSprite = new Sprite(new Texture("environmentTextures/empty_bed.png"));
            newSprite.setScale(ENVIRONMENT_OBJECTS_SCALE);
            newSprite.setPosition(oldSprite.getX(), oldSprite.getY());
            bed.getSprite().set(newSprite);
        }
        //add NPCs
        npcList.clear();
        for (String name : npcNames) {
            if (name.equals("cooker")) {
                npcList.add(new Npc("cooker", world, map, camera, "hero/hero.png", new MovementDelayManager() {
                    @Override
                    public boolean preMovePredicate() {
                        return false;
                    }

                    @Override
                    public boolean postMovePredicate() {
                        return false;
                    }
                }));
            } else
                npcList.add(new Npc(name, world, map, camera, "hero/" + name + ".png"));
        }
        npcList.stream().forEach(this::addEntity);
    }

    private void startNight() {
        //change beds
        for (Bed bed : beds) {
            Sprite oldSprite = bed.getSprite();
            Sprite newSprite = new Sprite(new Texture("environmentTextures/not_empty_bed.png"));
            newSprite.setScale(ENVIRONMENT_OBJECTS_SCALE);
            newSprite.setPosition(oldSprite.getX(), oldSprite.getY());
            bed.getSprite().set(newSprite);
        }
        //remove NPCs
        for (Npc npc : npcList) {
            stage.remove(npc);
            gameRenderer.removeEntity(npc);
            world.destroyBody(npc.getBody());
        }
        npcList = new ArrayList<>();
        //update light
        rayHandler.setAmbientLight(0, 0, 0, 0.2f);
        map.addNightLight();
        //addEvilNPC
        EvilNPC evilNPC = new EvilNPC("testEvilNpc", context, map, "hero/testEvilNpc.png");
        Npc npc = NpcBuilder.setEndStartDelay(evilNPC, 5000, 5000);
        addEntity(npc);
        evilNPC.initializeNoticedUI();
        npcList.add(npc);
    }

    private void addEntity(Entity entity) {
        stage.addEntity(entity);
        gameRenderer.addEntity(entity);
    }
}
