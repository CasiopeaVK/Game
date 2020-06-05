package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Toilet;
import com.mygdx.game.entities.npc.Door;
import com.mygdx.game.entities.npc.SingleDoor;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;
import com.mygdx.game.view.GameRenderer;

import static com.mygdx.game.Constants.*;

public class ObjectsRenderer {
    public static void renderEnvironment(Map map, SmartStage stage, GameContext context) {
        World world = context.getWorld();
        OrthographicCamera camera = context.getCamera();
        for (MapObject object : map.getLayer("Environment").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {

                Entity objEntity = new Entity(world, camera, "environmentTextures/" + object.getName() + ".png") {
                    @Override
                    public void update() {
                    }
                };
                if (object.getName().equals("cube")) {
                    addEntityToTheMap(object, objEntity, stage, context.getGameRenderer(),
                            new Vector2(objEntity.getSprite().getWidth() * 2 * UNIT_SCALE,
                                    objEntity.getSprite().getHeight() * UNIT_SCALE + objEntity.getSprite().getWidth() * UNIT_SCALE / 2));
                    System.out.println("Before scale : " +objEntity.getSprite().getY());
                    objEntity.getSprite().setScale(UNIT_SCALE);
                    System.out.println("After scale : " +objEntity.getSprite().getY());
                } else {
                    addEntityToTheMap(object, objEntity, stage, context.getGameRenderer(), OBJECT_SETTLING_PADDLE);
                }
            }
        }
        for (MapObject interactiveObject : map.getLayer("InteractiveEnvironment").getObjects()) {
            if (interactiveObject instanceof TiledMapTileMapObject) {
                if (interactiveObject.getName().equals("2")) {
                    Toilet entity = new Toilet(world, camera, "environmentTextures/" + interactiveObject.getName() + ".png");
                    addEntityToTheMap(interactiveObject, entity, stage, context.getGameRenderer(), OBJECT_SETTLING_PADDLE);
                }
            }
        }
        renderDoors(map, stage, context, world, camera);
    }

    private static void renderDoors(Map map, SmartStage stage, GameContext context, World world, Camera camera) {
        MapObjects objects = map.getLayer("Doors").getObjects();
        String leftDoorPathTexture = (String) map.getLayer("Doors").getProperties().get("left");
        String rightDoorPathTexture = (String) map.getLayer("Doors").getProperties().get("right");
        String doorPathTexture = (String) map.getLayer("Doors").getProperties().get("door");
        for (int i = 1; i <= objects.getCount() / 6; i++) {
            MapObject leftDoorObj = objects.get("leftDoor" + i);
            MapObject rightDoorObj = objects.get("rightDoor" + i);
            MapObject leftOpenDoorObj = objects.get("leftOpenDoor" + i);
            MapObject rightOpenDoorObj = objects.get("rightOpenDoor" + i);

            MapObject doorObj = objects.get("door" + i);
            MapObject doorTrigger = objects.get("trigger" + i);
            SingleDoor leftDoor = new SingleDoor(world, camera, doorPathTexture, leftDoorPathTexture,
                    IsoUtils.IsoTo2d((TiledMapTileMapObject) leftDoorObj), IsoUtils.IsoTo2d((TiledMapTileMapObject) leftOpenDoorObj));
            SingleDoor rightDoor = new SingleDoor(world, camera, doorPathTexture, rightDoorPathTexture,
                    IsoUtils.IsoTo2d((TiledMapTileMapObject) rightDoorObj), IsoUtils.IsoTo2d((TiledMapTileMapObject) rightOpenDoorObj));
            stage.addEntity(leftDoor);
            stage.addEntity(rightDoor);
            context.getGameRenderer().addEntity(leftDoor);
            context.getGameRenderer().addEntity(rightDoor);
            Door door = new Door(map, leftDoor, rightDoor, doorObj, doorTrigger);
        }
    }

    private static void addEntityToTheMap(MapObject object, Entity objEntity, SmartStage stage, GameRenderer
            gameRenderer, Vector2 translate) {
        Vector2 isoPosition = IsoUtils.IsoTo2d(new Vector2(((TiledMapTileMapObject) object).getX(), ((TiledMapTileMapObject) object).getY()));
        objEntity.getSprite().setPosition(isoPosition.x - translate.x, isoPosition.y - translate.y);
        objEntity.setPosition(isoPosition.x - translate.x, isoPosition.y - translate.y);
        objEntity.getSprite().setScale(ENVIRONMENT_OBJECTS_SCALE);
        stage.addEntity(objEntity);
        gameRenderer.addEntity(objEntity);
    }
}
