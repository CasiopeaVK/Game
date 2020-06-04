package com.mygdx.game.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameContext;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Toilet;
import com.mygdx.game.stage.SmartStage;
import com.mygdx.game.utils.IsoUtils;
import com.mygdx.game.view.GameRenderer;

import static com.mygdx.game.Constants.ENVIRONMENT_OBJECTS_SCALE;
import static com.mygdx.game.Constants.OBJECT_SETTLING_PADDLE;

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
                addEntityToTheMap(object, objEntity, stage, context.getGameRenderer());
            }
        }
        for (MapObject interactiveObject : map.getLayer("InteractiveEnvironment").getObjects()) {
            if (interactiveObject instanceof TiledMapTileMapObject) {
                if (interactiveObject.getName().equals("2")) {
                    Toilet entity = new Toilet(world, camera, "environmentTextures/" + interactiveObject.getName() + ".png");
                    addEntityToTheMap(interactiveObject, entity, stage, context.getGameRenderer());
                }
            }
        }
    }

    private static void addEntityToTheMap(MapObject object, Entity objEntity, SmartStage stage, GameRenderer
            gameRenderer) {
        Vector2 isoPosition = IsoUtils.IsoTo2d(new Vector2(((TiledMapTileMapObject) object).getX(), ((TiledMapTileMapObject) object).getY()));
        objEntity.getSprite().setPosition(isoPosition.x - OBJECT_SETTLING_PADDLE.x, isoPosition.y - OBJECT_SETTLING_PADDLE.y);
        objEntity.setPosition(isoPosition.x - OBJECT_SETTLING_PADDLE.x, isoPosition.y - OBJECT_SETTLING_PADDLE.y);
        objEntity.getSprite().setScale(ENVIRONMENT_OBJECTS_SCALE);
        stage.addEntity(objEntity);
        gameRenderer.addEntity(objEntity);
    }
}
