package com.mygdx.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.*;
import com.mygdx.game.inventory.InventoryCell;
import com.mygdx.game.items.GameItems;
import com.mygdx.game.items.sample.PlateFood;
import com.mygdx.game.quest.QuestLine;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;
import com.mygdx.game.view.GameRenderer;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.mygdx.game.Constants.*;

public class ObjectsRenderer {
    public static ArrayList<Bed> beds;
    public static Bed simpleBed;
    public static SmartBed smartBed;
    public static ArrayList<TableFood> tables = new ArrayList<>();

    public static void renderEnvironment(Map map, SmartStage stage, Player player, GameContext context, QuestLine questLine) {
        beds = new ArrayList<>();
        World world = context.getWorld();
        OrthographicCamera camera = context.getCamera();
        System.out.println(1);
        for (MapObject object : map.getLayer("Toilets").getObjects()) {
            Vector2 isoPosition = IsoUtils.IsoTo2d(new Vector2(((TiledMapTileMapObject) object).getX(), ((TiledMapTileMapObject) object).getY()));
            Entity entity = new Entity(world, camera, new Sprite(new Texture("environmentTextures/" + object.getName() + ".png"))) {
                @Override
                public void update() {

                }
            };
            addToiletToTheMap(isoPosition, entity, stage, context.getGameRenderer());
        }
        for (MapObject object : map.getLayer("Environment").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                Vector2 isoPosition = IsoUtils.IsoTo2d(new Vector2(((TiledMapTileMapObject) object).getX(), ((TiledMapTileMapObject) object).getY()));
                if (object.getName().equals("empty_bed")) {
                    Bed bed = new Bed(world, camera, new Sprite(new Texture("environmentTextures/empty_bed.png")));
                    beds.add(bed);
                    addEntityToTheMap(isoPosition, bed, stage, context.getGameRenderer(), new Vector2(0, 20));
                } else if (object.getName().equals("always_empty_bed")) {
                    Bed bed = new Bed(world, camera, new Sprite(new Texture("environmentTextures/empty_bed.png")));
                    addEntityToTheMap(isoPosition, bed, stage, context.getGameRenderer(), new Vector2(0, 20));
                } else if (object.getName().equals("always_not_empty_bed")) {
                    Bed bed = new Bed(world, camera, new Sprite(new Texture("environmentTextures/not_empty_bed.png")));
                    addEntityToTheMap(isoPosition, bed, stage, context.getGameRenderer(), new Vector2(0, 20));
                } else {
                    Entity objEntity = new Entity(world, camera, new Sprite(new Texture("environmentTextures/" + object.getName() + ".png"))) {
                        @Override
                        public void update() {
                        }
                    };
                    addEntityToTheMap(isoPosition, objEntity, stage, context.getGameRenderer(), new Vector2(0, 10));
                }

            }
        }
        for (MapObject interactiveObject : map.getLayer("InteractiveEnvironment").getObjects()) {
            Vector2 isoPosition = IsoUtils.IsoTo2d(new Vector2(((TiledMapTileMapObject) interactiveObject).getX(), ((TiledMapTileMapObject) interactiveObject).getY()));
            switch (interactiveObject.getName()) {
                case "2": {
                    Toilet entity = new Toilet(context, "environmentTextures/" + interactiveObject.getName() + ".png");
                    addToiletToTheMap(isoPosition, entity, stage, context.getGameRenderer());
                    break;
                }
                case "cloggingText":
                    CloggingIndicator cloggingIndicator = new CloggingIndicator(world, camera, new Sprite(new Texture("environmentTextures/one.png")), player, new Vector2(isoPosition.x - 10, isoPosition.y - 10));
                    addEntityToTheMapWithCustomPositionAndScale(cloggingIndicator, stage, context.getGameRenderer());
                    cloggingIndicator.setPosition(isoPosition.x - cloggingIndicator.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE - 10, isoPosition.y - cloggingIndicator.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - 10);
                    break;
                case "tunnel":
                    Tunel entity = new Tunel(world, camera, new Sprite(new Texture("environmentTextures/tunnel0.png")), player.getInventory(), context.getItemBuilder(), player, isoPosition, questLine);
                    entity.setPosition(isoPosition.x - entity.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - entity.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE);
                    addEntityToTheMapWithCustomPositionAndScale(entity, stage, context.getGameRenderer());
                    break;
                case "first_bed":
                    SmartBed smartBed = new SmartBed(world, camera, new Sprite(new Texture("environmentTextures/empty_bed.png")), player, isoPosition, context);
                    ObjectsRenderer.smartBed = smartBed;
                    smartBed.setPosition(isoPosition.x - smartBed.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - smartBed.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - 20);
                    addEntityToTheMapWithCustomPositionAndScale(smartBed, stage, context.getGameRenderer());
                    break;
                case "bed":
                    Bed simpleBed = new Bed(world, camera, new Sprite(new Texture("environmentTextures/empty_bed.png")));
                    ObjectsRenderer.simpleBed = simpleBed;
                    addEntityToTheMap(isoPosition, simpleBed, stage, context.getGameRenderer(), new Vector2(0, 20));
                    break;
                case "table1":
                    InteractiveEntity table = new InteractiveEntity(world, camera, new Sprite(new Texture("environmentTextures/table1.png"))) {
                        @Override
                        protected void onClick(InputEvent event, float x, float y) {
                            int count = (int) player.getInventory().getListCells().stream().filter(new Predicate<InventoryCell>() {
                                @Override
                                public boolean test(InventoryCell inventoryCell) {
                                    return inventoryCell.getItem() instanceof PlateFood;
                                }
                            }).count();
                            if (count == 4) {
                                ((SmartStage) stage).incrementCurrentQuestIndex();
                                for (InventoryCell cell : player.getInventory().getListCells()) {
                                    if (cell.getItem() instanceof PlateFood) {
                                        cell.setItem(player.getInventory().getItemBuilder().createItem(GameItems.PLATE));
                                    }
                                }
                            }
                        }

                        @Override
                        public void update() {
                            updateClickListener();
                        }
                    };
                    addEntityToTheMap(isoPosition, table, stage, context.getGameRenderer(), new Vector2(0, 0));
                    break;
                case "canibet3":
                    Chest chest = new Chest(world, camera, "environmentTextures/canibet3.png", isoPosition, stage, context);
                    chest.setPosition(isoPosition.x - chest.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - chest.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - 30);
                    addEntityToTheMapWithCustomPositionAndScale(chest, stage, context.getGameRenderer());
                    break;
                case "hospitalTable2":
                    HospitalTable hospitalTable = new HospitalTable(world, camera, "environmentTextures/hospitalTable2.png", isoPosition, player, context.getItemBuilder());
                    hospitalTable.setPosition(isoPosition.x - hospitalTable.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - hospitalTable.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - 30);
                    addEntityToTheMapWithCustomPositionAndScale(hospitalTable, stage, context.getGameRenderer());
                    break;
                case "tableFood":
                    TableFood tableFood = new TableFood(world,camera,"environmentTextures/tableFood.png", isoPosition, player, context.getItemBuilder(), context.getStage());
                    tableFood.setPosition(isoPosition.x - tableFood.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE, isoPosition.y - tableFood.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - 10);
                    tables.add(tableFood);
                    addEntityToTheMapWithCustomPositionAndScale(tableFood,stage,context.getGameRenderer());
            }
        }
    }

    private static void addEntityToTheMap(Vector2 isoPosition, Entity objEntity, SmartStage stage, GameRenderer
            gameRenderer, Vector2 paddings) {
        objEntity.getSprite().setPosition(isoPosition.x - objEntity.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE - paddings.x, isoPosition.y - objEntity.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - paddings.y);
        objEntity.setPosition(isoPosition.x - objEntity.getSprite().getWidth() * 2 * ENVIRONMENT_OBJECTS_SCALE - paddings.x, isoPosition.y - objEntity.getSprite().getHeight() * ENVIRONMENT_OBJECTS_SCALE - paddings.y);
        objEntity.getSprite().setScale(ENVIRONMENT_OBJECTS_SCALE);
        stage.addEntity(objEntity);
        gameRenderer.addEntity(objEntity);
    }


    private static void addEntityToTheMapWithCustomPositionAndScale(Entity entity, SmartStage stage, GameRenderer gameRenderer) {
        stage.addEntity(entity);
        gameRenderer.addEntity(entity);
    }

    private static void addToiletToTheMap(Vector2 isoPosition, Entity entity, SmartStage stage, GameRenderer gameRenderer) {
        entity.getSprite().setPosition(isoPosition.x - TOILET_SETTLING_PADDLE.x, isoPosition.y - TOILET_SETTLING_PADDLE.y);
        entity.setPosition(isoPosition.x - TOILET_SETTLING_PADDLE.x, isoPosition.y - TOILET_SETTLING_PADDLE.y);
        entity.getSprite().setScale(TOILET_SCALE);
        stage.addEntity(entity);
        gameRenderer.addEntity(entity);
    }
}
