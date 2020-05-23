package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class IsometricOrderRenderer extends IsometricTiledMapRenderer {

    private Sprite sprite;
    private List<Sprite> sprites;
    private int drawSpritesAfterLayer = 1;
    private SpriteBatch spriteBatch;

    public IsometricOrderRenderer(TiledMap map, float UNIT_SCALE, SpriteBatch spriteBatch) {
        super(map, UNIT_SCALE);
        sprites = new ArrayList<Sprite>();
        this.spriteBatch = spriteBatch;
    }

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                    currentLayer++;
                    if(currentLayer == drawSpritesAfterLayer){
                        for(Sprite sprite : sprites)

                            sprite.draw(this.batch);
                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }
}
